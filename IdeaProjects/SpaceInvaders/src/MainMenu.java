import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;


public class MainMenu extends Application {
    GameViewManager game;


    private AnchorPane pane;
    private final int GAME_HEIGTH = 400;
    private final int GAME_WIDTH = 400;
    private final String SCORES_PATH = "\\src\\resources\\scores.txt";
    Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGTH);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    /**
     * Metodo per creare il pannello di gioco. Viene generato una nuova partita con tutti i contenuti al suo interno.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SpaceInvaders");
        createContent(primaryStage);
    }
    /**
     * Metodo per creare il contenuto del menu. Il button new Game inizializza una nuova partita, il metodo get best scores restituisce i punteggi migliori, salvati su file csv.
     * @param stage
     */
    private void createContent(Stage stage){
        pane = new AnchorPane();
        MediaMaker.createBackground(pane, GAME_HEIGTH);
        gc.setFont(Font.font(40));
        gc.setFill(Color.WHITE);

        gc.fillText("SPACE INVADERS", 40,80);
        gc.setFont(Font.font(20));
        gc.fillText("di Mirko e Alberto Torrisi\n\n\n\tesame Java 2020", 90,100);

        Button startBtn = new Button("Start Game");
        startBtn.setLayoutX(90);
        startBtn.setLayoutY(300);

        Button scoreBtn = new Button("Best Scores");
        scoreBtn.setLayoutX(220);
        scoreBtn.setLayoutY(300);
        scoreBtn.setOnAction(e->{
            try {
                gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
                ArrayList<String[]> results = CSV.read(MediaMaker.createFilePath(SCORES_PATH));
                Map<Integer,String> scoresMap = new HashMap<Integer,String>();
                for(String[] res : results){
                    scoresMap.put(Integer.valueOf(res[1]),res[0]);
                }
                TreeMap<Integer, String> treeMap = new TreeMap<>(scoresMap);
                Set<Map.Entry<Integer, String>> entries = treeMap.entrySet();
                int i = 10;
                while(entries.size() > 10){
                    entries.remove(entries.iterator().next());
                }
                for(Map.Entry<Integer, String> entry : entries){
                    gc.fillText( entry.getKey() + "-" + entry.getValue(), 20, i * 20 );
                    i--;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        Label label1 = new Label("Name:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        hb.setLayoutX(100);
        hb.setLayoutY(200);
        startBtn.setOnAction(e->{
            try {
                /** se il playername non viene inserito nel field, la partita non inizia*/
                if(textField.getText()!= null && !textField.getText().isEmpty()) {
                    game = new GameViewManager(textField.getText());
                    game.start(stage);
                }
                else{
                    gc.fillText("INSERT PLAYER NAME!!!!",150,150);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        pane.getChildren().addAll(canvas, scoreBtn, startBtn, hb);

        stage.setScene(new Scene(pane, GAME_WIDTH, GAME_HEIGTH));

        stage.show();
    }
}
