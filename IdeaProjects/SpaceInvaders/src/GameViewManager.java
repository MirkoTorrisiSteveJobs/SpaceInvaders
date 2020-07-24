import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class GameViewManager extends Application {
    private boolean isShooting;
    private AnchorPane pane;
    SpaceInvaders game = new SpaceInvaders();

    private ArrayList<Rectangle> shieldSquares = new ArrayList<>();

    private final int multiplier = 25;
    private final int GAME_HEIGTH = game.getGrid().length * multiplier;
    private final int GAME_WIDTH = game.getGrid()[0].length * multiplier;

    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGTH);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    private GridPane pane1 = new GridPane();
    private GridPane pane2 = new GridPane();
    private GridPane pane3 = new GridPane();

    private final String BG_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\bg\\16 Bit Space Background Astronaut scott kelly is coming home - cnn.com.jpg";
    private final String PLAYER_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\player\\kisspng-sprite-space-shuttle-story-spacecraft-2d-computer-space-craft-5ab949f7a10741-removebg-preview.png";
    private final String ENEMY_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy1.png";
    private final String ENEMY_SHIP2_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy2.png";
    private final String ENEMY_SHIP3_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy3.png";
    private final String SHOOT_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\shoot\\red-laser-png - removebg-preview.png";
    private final String EXPLOSION_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\explosion\\explosion.png";
    private final String AUDIO_SHOOT_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\AUDIO\\explosion\\esm_8bit_explosion_heavy_with_voice_bomb_boom_blast_cannon_retro_old_school_classic_cartoon.mp3";
    private final String AUDIO_EXPLOSION_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\AUDIO\\shoot\\laser1.mp3";

    Image playerShip = new Image(new File(createFilePath(PLAYER_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 20, 20, false, false);
    Image shootImage = new Image(new File(createFilePath(SHOOT_PATH)).getAbsoluteFile().toURI().toString(), 2, 10, false, false);
    Image explosionImage = new Image(new File(createFilePath(EXPLOSION_PATH)).getAbsoluteFile().toURI().toString(), 10, 20, false, false);
    Image enemyImage1 = new Image(new File(createFilePath(ENEMY_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    Image enemyImage2 = new Image(new File(createFilePath(ENEMY_SHIP2_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    Image enemyImage3 = new Image(new File(createFilePath(ENEMY_SHIP3_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);

    public static void Main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SpaceInvaders");
        createContent(stage);

    }
    private void animatePlayer(){
        gc.drawImage(playerShip, game.getPlayer().getPosition()[0] * multiplier, game.getPlayer().getPosition()[1] * multiplier - 5);
    }
    private void animateEnemies(){
        for (int i = 0; i < game.getEnemies().size();i++) {
            if(!game.getEnemies().get(i).isHit()) {
                switch (game.getEnemies().get(i).getType()){
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
    private void animateShoots(){
        for (Shoot shoot : game.getShoots()) {
            if(!shoot.isHitSomething()) {
                gc.drawImage(shootImage, shoot.getCoords()[0] * multiplier, shoot.getCoords()[1] * multiplier);
            }
            else{
                gc.drawImage(explosionImage, shoot.getCoords()[0] * multiplier, shoot.getCoords()[1] * multiplier);
            }

        }
    }
    private void createAudioClips(){
        AudioClip shoot = new AudioClip(new File(createFilePath(AUDIO_SHOOT_PATH)).toURI().toString());
        AudioClip explosion = new AudioClip(new File(createFilePath(AUDIO_EXPLOSION_PATH)).toURI().toString());

    }
    private Scene createContent(Stage stage) {
        Group root = new Group();
        pane = new AnchorPane();
        createBackground();
        pane.getChildren().add(root);
        root.getChildren().add(canvas);
        Scene scene = new Scene(pane, GAME_WIDTH, GAME_HEIGTH);

        for (Shield shield : game.getShieldCoords()) {
            Rectangle rect = new Rectangle(20, 20);
            rect.setX(shield.getCoords()[0]*25);
            rect.setY(shield.getCoords()[1]*25);
            rect.setFill(Color.BLUEVIOLET);
            shieldSquares.add(rect);
            root.getChildren().addAll(rect);
        }
        Text score = new Text();
        score.setX(100);
        score.setY(10);
        score.setStyle("-fx-font-size:15px;");
        root.getChildren().add(score);

        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle); // call to the EventHandler
        root.getChildren().add(keyboardNode);

        stage.setScene(scene);
        stage.show();

        AnimationTimer animator = new AnimationTimer() {

            @Override
            public void handle(long arg0) {
                gc.clearRect(0, 0, GAME_WIDTH,GAME_HEIGTH);
                moveBackground();
                score.setText("SCORE: "+String.valueOf(game.getScore()));
                if(game.getPlayer().isInGame() && !game.isGameOver()){
                    animatePlayer();
                    animateEnemies();
                    animateShoots();
                    for (int i = 0; i < game.getShieldCoords().size(); i++) {
                        if(game.getShieldCoords().get(i).isHit()){
                            root.getChildren().remove(shieldSquares.get(i));
                        }
                    }


                    game.makeOneFrame();

                }
            }

        };
        animator.start();
        return scene;
    }
    private void handle(KeyEvent arg0) {
        if (arg0.getCode() == KeyCode.SPACE )
        {
            game.playerShot();
            isShooting = true;
        }

        else if (arg0.getCode() == KeyCode.LEFT )
        {
            game.getPlayer().move(1);
        }
        else if (arg0.getCode() == KeyCode.RIGHT )
        {
            game.getPlayer().move(-1);
        }
    }
    private Text scoreGen(){
        return new Text(String.valueOf(game.getScore()));
    }
    private String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }
    private void createBackground() {
        File file = new File(createFilePath(BG_PATH));
        javafx.scene.image.Image img = new Image(file.getAbsoluteFile().toURI().toString());

        pane1.getChildren().add(new ImageView(img));
        pane2.getChildren().add(new ImageView(img));
        pane3.getChildren().add(new ImageView(img));
        pane.getChildren().addAll(pane1,pane2,pane3);
        pane3.setLayoutY(GAME_HEIGTH-5);
        pane2.setLayoutY(GAME_HEIGTH-5);
    }
    private void moveBackground(){
        pane1.setLayoutY(pane1.getLayoutY()-4.5);
        pane2.setLayoutY(pane2.getLayoutY()-4.5);
        pane3.setLayoutY(pane3.getLayoutY()-4.5);
        if(pane1.getLayoutY() <= -GAME_HEIGTH+5){
            pane1.setLayoutY(GAME_HEIGTH-5);
        }
        if(pane2.getLayoutY() <= -GAME_HEIGTH+5){
            pane2.setLayoutY(GAME_HEIGTH-5);
        }

    }
}
