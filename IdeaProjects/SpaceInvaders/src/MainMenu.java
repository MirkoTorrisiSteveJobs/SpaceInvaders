import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainMenu extends Application {
    GameViewManager game = new GameViewManager();
    private AnchorPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SpaceInvaders");
        createContent(primaryStage);

    }
    private void createContent(Stage stage){
        pane = new AnchorPane();
        Button butn = new Button("cliccami");
        butn.setOnAction(e->{
            try {
                game.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        stage.setScene(new Scene(pane));
        stage.show();
    }
}
