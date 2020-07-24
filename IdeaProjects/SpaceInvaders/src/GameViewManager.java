import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
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
    private ArrayList<Circle> enemyCircles = new ArrayList<>();
    private ArrayList<Rectangle> shieldSquares = new ArrayList<>();
    private ArrayList<Rectangle> shootRectContainer = new ArrayList<>();
    private final int GAME_HEIGTH = game.getGrid().length * 25;
    private final int GAME_WIDTH = game.getGrid()[0].length * 25;
    private final String BG_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\bg.jfif";
    private final String HEAD_PATH = "/src/SpaceInvaders/view/resources/zequila.JPG";
    private final String WAKA_PATH = "/src/SpaceInvaders/view/resources/waka.mp3";

    public static void Main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SpaceInvaders");
        createContent(stage);

    }
    private Scene createContent(Stage stage) {
        Group root = new Group();
        pane = new AnchorPane();
        createBackground();
        pane.getChildren().add(root);
        Scene scene = new Scene(pane, GAME_WIDTH, GAME_HEIGTH);

        Circle playerShip = new Circle(20);
        root.getChildren().addAll(playerShip);

        for (EnemyShip ship : game.getEnemies()) {
            Circle circle = new Circle(10);
            root.getChildren().addAll(circle);
            enemyCircles.add(circle);
        }

        for (Shield shield : game.getShieldCoords()) {
            Rectangle rect = new Rectangle(20, 20);
            rect.setX(shield.getCoords()[0]*25);
            rect.setY(shield.getCoords()[1]*25);
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
                game.makeOneFrame();
                score.setText("SCORE: "+String.valueOf(game.getScore()));
                if(game.getPlayer().isInGame() && !game.isGameOver()){
                    playerShip.setCenterX(game.getPlayer().getPosition()[0]*25+25);
                    playerShip.setCenterY(game.getPlayer().getPosition()[1]*25+25);

                    if(isShooting && game.getShoots().size() > 0){
                        Rectangle rect = new Rectangle(5,20);
                        shootRectContainer.add(rect);
                        root.getChildren().add(rect);
                        isShooting = false;
                    }

                    for(int i = 0 ; i < shootRectContainer.size(); i++){
                        if(game.getShoots().size()>i) {
                            shootRectContainer.get(i).setX(game.getShoots().get(i).getCoords()[0] * 25);
                            shootRectContainer.get(i).setY(game.getShoots().get(i).getCoords()[1] * 25);
                            if(game.getShoots().get(i).isHitSomething()){
                                root.getChildren().remove(shootRectContainer.get(i));
                            }
                        }
                    }
                    for (int i = 0; i < game.getShieldCoords().size(); i++) {
                        if(game.getShieldCoords().get(i).isHit()){
                            root.getChildren().remove(shieldSquares.get(i));
                        }
                    }

                    for (int i = 0; i < enemyCircles.size(); i++) {
                        if(game.getEnemies().get(i).isHit()) {
                            root.getChildren().remove(enemyCircles.get(i));
                        }
                        else{
                            enemyCircles.get(i).setCenterX(game.getEnemies().get(i).getPosition()[0] * 25 + 25);
                            enemyCircles.get(i).setCenterY(game.getEnemies().get(i).getPosition()[1] * 25 + 25);
                        }
                    }
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
        System.out.println(createFilePath(BG_PATH));
        javafx.scene.image.Image img = new Image(file.getAbsoluteFile().toURI().toString());
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bgImg));
    }

}
