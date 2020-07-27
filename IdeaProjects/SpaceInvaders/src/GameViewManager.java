import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class GameViewManager extends Application {

    SpaceInvaders game;
    String playerName;
    private AnchorPane pane;


    private final int multiplier = 25;
    private final int GAME_HEIGTH = 20 * multiplier;
    private final int GAME_WIDTH = 20 * multiplier;

    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGTH);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    private final String PLAYER_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\player\\kisspng-sprite-space-shuttle-story-spacecraft-2d-computer-space-craft-5ab949f7a10741-removebg-preview.png";
    private final String ENEMY_SHIP_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy1.png";
    private final String ENEMY_SHIP2_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy2.png";
    private final String ENEMY_SHIP3_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\enemy\\enemy_scontornati\\enemy3.png";
    private final String SHOOT_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\shoot\\red-laser-png - removebg-preview.png";
    private final String EXPLOSION_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\explosion\\explosion.png";
    private final String SHIELD_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\shield\\scudo1.png";

    private final String SCORES_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\scores.txt";

    private Image playerShip = new Image(new File(MediaMaker.createFilePath(PLAYER_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 20, 20, false, false);
    private Image shootImage = new Image(new File(MediaMaker.createFilePath(SHOOT_PATH)).getAbsoluteFile().toURI().toString(), 2, 10, false, false);
    private Image explosionImage = new Image(new File(MediaMaker.createFilePath(EXPLOSION_PATH)).getAbsoluteFile().toURI().toString(), 10, 20, false, false);
    private Image enemyImage1 = new Image(new File(MediaMaker.createFilePath(ENEMY_SHIP_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    private Image enemyImage2 = new Image(new File(MediaMaker.createFilePath(ENEMY_SHIP2_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    private Image enemyImage3 = new Image(new File(MediaMaker.createFilePath(ENEMY_SHIP3_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);
    private Image shieldImage = new Image(new File(MediaMaker.createFilePath(SHIELD_PATH)).getAbsoluteFile().toURI().toString(), 10, 10, false, false);

    public static void Main(String[] args){
        launch(args);
    }

    public GameViewManager(String playerName){
        game = new SpaceInvaders(playerName);
        this.playerName = playerName;
        MediaMaker.playSong();

    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SpaceInvaders");
        createContent(stage);

    }
    /**
     * Metodo per animare lo sprite del giocatore
     * @return void
     */
    private void animatePlayer(){
        gc.drawImage(playerShip, game.getPlayer().getPosition()[0] * multiplier, game.getPlayer().getPosition()[1] * multiplier - 5);
    }
    /**
     * Metodo per animare lo sprite dei nemici
     * @return void
     */
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
    /**
     * Metodo per animare lo sprite dello sparo
     * @return void
     */
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
    /**
     * Metodo per animare lo sprite degli scudi
     * @return void
     */
    private void animateShields(){
        for (Shield shield : game.getShieldCoords()) {
            if (!shield.isHit()) {
                gc.drawImage(shieldImage, shield.getCoords()[0] * multiplier, shield.getCoords()[1] * multiplier);
            }
        }
    }
    /**
     * Metodo per creare il contenuto del gioco. Inizializza il mediaplayer con la musica, il pannello dove vengono inseriti tutti gli oggetti
     * contiene l'animator che aggiorna il frame, richiamando la logica del gioco della classe di modello con il suo metodo "MakeOneFrame",
     * gestisce l'input dei tasti, restituisce gameOver se la partita finisce
     * @param stage
     * @return Scene
     */
    private Scene createContent(Stage stage) {
        MediaPlayer player = MediaMaker.playSong();
        player.play();
        Group root = new Group();
        pane = new AnchorPane();
        MediaMaker.createBackground(pane, GAME_HEIGTH);
        pane.getChildren().add(root);

        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this::handle); // call to the EventHandler
        root.getChildren().add(keyboardNode);
        final boolean[] justOnce = {true};
        AnimationTimer animator = new AnimationTimer() {

            @Override
            public void handle(long arg0) {

                gc.clearRect(0, 0, GAME_WIDTH,GAME_HEIGTH);
                MediaMaker.moveBackground(GAME_HEIGTH);

                gc.setFont(Font.font(20));
                gc.fillText("SCORE: " + game.getScore(), 300, 20);
                gc.strokeText("SCORE: " + game.getScore(), 300, 20);
                gc.setFill(Color.WHITE);
                gc.fillText("LEVEL " + game.getLevel(), 10, 20);
                gc.fillText(" LIVES: " + game.getPlayer().getLives()+" ", 10, 50);
                if(game.getPlayer().isDead()){
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }
                if(game.getPlayer().isInGame() && !game.isGameOver()){
                    animatePlayer();
                    animateEnemies();
                    animateShoots();
                    animateShields();
                    game.makeOneFrame();
                }
                else{
                    gc.setFont(Font.font(50));
                    gc.setFill(Color.RED);
                    gc.fillText("GAME OVER", 200, 200);
                    gc.setFont(Font.font(20));
                    gc.setFill(Color.WHITE);
                    if(justOnce[0]) {
                        CSV.writeAppend(MediaMaker.createFilePath(SCORES_PATH), "\n" + playerName + "," + game.getScore());
                        justOnce[0] = false;
                    }
                    gc.fillText("press ENTER to play again", 200, 250);
                }
            }
        };
        root.getChildren().add(canvas);
        animator.start();
        Pane paneCont = new Pane(pane);
        Scene scene = new Scene(paneCont, GAME_WIDTH, GAME_HEIGTH);
        stage.setScene(scene);
        stage.show();
        return scene;
    }
    /**
     * Metodo per gestire l'input da tastiera, cambia comportamento a seconda dello status della partita(GameOver)
     * @param arg0
     * @return void
     */
    private void handle(KeyEvent arg0) {
        if(!game.isGameOver()) {
            if (arg0.getCode() == KeyCode.SPACE) {
                game.playerShot();
                MediaMaker.playShoot();
            } else if (arg0.getCode() == KeyCode.LEFT) {
                game.getPlayer().move(1);
            } else if (arg0.getCode() == KeyCode.RIGHT) {
                game.getPlayer().move(-1);
            }
        }
        else{
            if(arg0.getCode() == KeyCode.ENTER){
                game = new SpaceInvaders(this.playerName);
            }
        }
    }


}
