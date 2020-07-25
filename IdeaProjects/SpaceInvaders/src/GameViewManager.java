import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class GameViewManager extends Application {
    private AnchorPane pane;
    SpaceInvaders game = new SpaceInvaders();

    private ArrayList<Rectangle> shieldSquares = new ArrayList<>();

    private final int multiplier = 25;
    private final int GAME_HEIGTH = game.getGrid().length * multiplier;
    private final int GAME_WIDTH = game.getGrid()[0].length * multiplier;

    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGTH);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    private final String PLAYER_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\player\\kisspng-sprite-space-shuttle-story-spacecraft-2d-computer-space-craft-5ab949f7a10741-removebg-preview.png";
    private final String ENEMY_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy1.png";
    private final String ENEMY_SHIP2_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy2.png";
    private final String ENEMY_SHIP3_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy3.png";
    private final String SHOOT_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\shoot\\red-laser-png - removebg-preview.png";
    private final String EXPLOSION_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\explosion\\explosion.png";

    private Image playerShip = new Image(new File(createFilePath(PLAYER_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 20, 20, false, false);
    private Image shootImage = new Image(new File(createFilePath(SHOOT_PATH)).getAbsoluteFile().toURI().toString(), 2, 10, false, false);
    private Image explosionImage = new Image(new File(createFilePath(EXPLOSION_PATH)).getAbsoluteFile().toURI().toString(), 10, 20, false, false);
    private Image enemyImage1 = new Image(new File(createFilePath(ENEMY_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    private Image enemyImage2 = new Image(new File(createFilePath(ENEMY_SHIP2_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    private Image enemyImage3 = new Image(new File(createFilePath(ENEMY_SHIP3_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);

    public static void Main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SpaceInvaders");
        createContent(stage);

    }

    private void animatePlayer() {
        gc.drawImage(playerShip, game.getPlayer().getPosition()[0] * multiplier, game.getPlayer().getPosition()[1] * multiplier - 5);
    }

    private void animateEnemies() {
        for (int i = 0; i < game.getEnemies().size(); i++) {
            if (!game.getEnemies().get(i).isHit()) {
                switch (game.getEnemies().get(i).getType()) {
                    case 1:
                        gc.drawImage(enemyImage1, game.getEnemies().get(i).getPosition()[0] * multiplier, game.getEnemies().get(i).getPosition()[1] * multiplier);
                        break;
                    case 2:
                        gc.drawImage(enemyImage2, game.getEnemies().get(i).getPosition()[0] * multiplier, game.getEnemies().get(i).getPosition()[1] * multiplier);
                        break;
                    case 3:
                        gc.drawImage(enemyImage3, game.getEnemies().get(i).getPosition()[0] * multiplier, game.getEnemies().get(i).getPosition()[1] * multiplier);
                        break;
                }
            }
        }
    }

    private void animateShoots() {
        for (Shoot shoot : game.getShoots()) {
            if (!shoot.isHitSomething()) {
                gc.drawImage(shootImage, shoot.getCoords()[0] * multiplier, shoot.getCoords()[1] * multiplier);
            } else {
                gc.drawImage(explosionImage, shoot.getCoords()[0] * multiplier, shoot.getCoords()[1] * multiplier);
            }
        }
    }

    private Scene createContent(Stage stage) {
        Group root = new Group();
        pane = new AnchorPane();
        MediaMaker.createBackground(pane, GAME_HEIGTH);
        pane.getChildren().add(root);
        MediaPlayer player = MediaMaker.playSong();
        player.play();
        for (Shield shield : game.getShieldCoords()) {
            Rectangle rect = new Rectangle(20, 20);
            rect.setX(shield.getCoords()[0] * 25);
            rect.setY(shield.getCoords()[1] * 25);
            rect.setFill(Color.BLUEVIOLET);
            shieldSquares.add(rect);
            root.getChildren().addAll(rect);
        }

        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle); // call to the EventHandler
        root.getChildren().add(keyboardNode);

        AnimationTimer animator = new AnimationTimer() {

            @Override
            public void handle(long arg0) {

                gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
                MediaMaker.moveBackground(GAME_HEIGTH);
                if (game.isLevelWin()) {
                    gc.setFont(Font.font(50));
                    gc.fillText("LEVEL " + game.getLevel(), 200, 200);
                    gc.strokeText("LEVEL: " + game.getLevel(), 200, 200);
                    gc.setFill(Color.RED);
                    try {
                        Thread.sleep(100);
                        gc.setFill(Color.WHITE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gc.setFont(Font.font(20));
                gc.fillText("SCORE: " + game.getScore(), 300, 20);
                gc.strokeText("SCORE: " + game.getScore(), 300, 20);
                gc.setFill(Color.WHITE);
                if (game.getPlayer().isInGame() && !game.isGameOver()) {
                    animatePlayer();
                    animateEnemies();
                    animateShoots();
                    for (int i = 0; i < game.getShieldCoords().size(); i++) {
                        if (game.getShieldCoords().get(i).isHit()) {
                            root.getChildren().remove(shieldSquares.get(i));
                        }
                    }
                    game.makeOneFrame();
                } else {
                    gc.setFont(Font.font(50));
                    gc.setFill(Color.RED);
                    gc.fillText("GAME OVER", 200, 200);
                }
            }

        };
        canvas.setMouseTransparent(true);
        root.getChildren().add(canvas);
        animator.start();
        Pane scrollPane = new Pane(pane);
        BorderPane borderPane = new BorderPane();
        borderPane.setMinHeight(GAME_HEIGTH + 20);
        borderPane.setTop(menu(stage));
        borderPane.setCenter(scrollPane);
        Scene scene = new Scene(borderPane, GAME_WIDTH, GAME_HEIGTH);
        stage.setScene(scene);
        stage.show();
        return scene;
    }

    private void handle(KeyEvent arg0) {
        if (!game.isLevelWin()) {
            if (arg0.getCode() == KeyCode.SPACE) {
                game.playerShot();
                MediaMaker.playShoot();
            } else if (arg0.getCode() == KeyCode.LEFT) {
                game.getPlayer().move(1);
            } else if (arg0.getCode() == KeyCode.RIGHT) {
                game.getPlayer().move(-1);
            }
        } else {
            if (arg0.getCode() == KeyCode.SPACE || arg0.getCode() == KeyCode.RIGHT || arg0.getCode() == KeyCode.LEFT) {
                game.setLevelWin(false);
            }
        }
    }

    private Text scoreGen() {
        return new Text(String.valueOf(game.getScore()));
    }

    private String createFilePath(String path) {
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }

    private VBox menu(Stage stage) {
        final Menu menu1 = new Menu("Options");
        MenuItem menuItem1 = new MenuItem("Restart");
        MenuItem menuItem2 = new MenuItem("Go to main menu");
        menuItem1.setOnAction(e -> {
            game = new SpaceInvaders();
        });
        menuItem2.setOnAction(e -> {
            MainMenu menu = new MainMenu();
            try {
                menu.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        menu1.getItems().add(menuItem2);
        menu1.getItems().add(menuItem1);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1);

        return new VBox(menuBar);
    }

}