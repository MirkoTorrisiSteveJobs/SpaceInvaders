import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainMenu extends Application {
    GameViewManager game = new GameViewManager();
    private AnchorPane pane;
    private final int GAME_HEIGTH = 400;
    private final int GAME_WIDTH = 400;
    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGTH);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SpaceInvaders");
        createContent(primaryStage);

    }
    private void createContent(Stage stage){
        pane = new AnchorPane();
        MediaMaker.playSong();
        MediaMaker.createBackground(pane, GAME_HEIGTH);
        gc.setFont(Font.font(40));
        gc.setFill(Color.WHITE);

        gc.fillText("SPACE INVADERS", 40,80);
        gc.setFont(Font.font(20));
        gc.fillText("di Mirko e Alberto Torrisi\n\n\n\tesame Java 2020", 90,100);

        Button startBtn = new Button("Start Game");
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(300);
        startBtn.setOnAction(e->{
            try {
                game.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Button scoreBtn = new Button("Best Scores");
        scoreBtn.setLayoutX(220);
        scoreBtn.setLayoutY(300);
        scoreBtn.setOnAction(e->{
            try {
                game.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        pane.getChildren().addAll(canvas, scoreBtn, startBtn);

        stage.setScene(new Scene(pane, GAME_WIDTH, GAME_HEIGTH));

        stage.show();
    }
}
