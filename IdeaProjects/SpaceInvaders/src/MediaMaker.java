import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaMaker {
    /**
    * Classe statica che genera i media del gioco. Crea il background e lo muove, la musica, i suoni.
     */
    static MediaPlayer mediaPlayer;
    static private final String AUDIO_EXPLOSION_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\AUDIO\\explosion\\esm_8bit_explosion_heavy_with_voice_bomb_boom_blast_cannon_retro_old_school_classic_cartoon.mp3";
    static private final String AUDIO_SHOOT_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\AUDIO\\explosion\\esm_8bit_splat_explosion_bomb_boom_blast_cannon_retro_old_school_classic_cartoon.mp3";
    static private final String SONG_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\AUDIO\\PowerLoad2.mp3";
    static private final String BG_PATH = "\\IdeaProjects\\SpaceInvaders\\src\\resources\\bg\\16 Bit Space Background Astronaut scott kelly is coming home - cnn.com.jpg";

    static private GridPane pane1 = new GridPane();
    static private GridPane pane2 = new GridPane();
    static private GridPane pane3 = new GridPane();

    static AudioClip shootSound = new AudioClip(new File(createFilePath(AUDIO_SHOOT_PATH)).toURI().toString());
    static AudioClip explosionSound = new AudioClip(new File(createFilePath(AUDIO_EXPLOSION_PATH)).toURI().toString());
    /**
    * Metodo che genera il path assoluto di ogni file dando in input il path relativo alla content root
     * @param path
     * @return String
     */
    static String createFilePath(String path){
        String finalPath = new File("").getAbsolutePath();
        return finalPath + path;
    }
    /**
    * Metodo per riprodurre il suono dell'esplosione
     * @return void
     */
    static void playExplosion(){
        explosionSound.setVolume(0.1);explosionSound.play();
    }
    /**
     * Metodo per riprodurre il suono dello sparo
     * @return void
     */
    static void playShoot(){

        shootSound.setVolume(0.1);shootSound.play();
    }
    /**
     * Metodo per generare l'immagina dato in input il suo content path
     * @param path
     * @return Image
     */
    static Image createImage(String path){
        File file = new File(createFilePath(path));
        return new Image(file.getAbsoluteFile().toURI().toString());
    }
    /**
     * Metodo per riprodurre il media player con la musica
     * @return MediaPlayer
     */
    static MediaPlayer playSong(){
        return mediaPlayer = new MediaPlayer(new Media(new File(createFilePath(SONG_PATH)).toURI().toString()));
    }
    /**
     * Metodo per creare il background
     * @param GAME_HEIGTH,Pane
     * @return void
     */
    static void createBackground(Pane pane, int GAME_HEIGTH) {
        pane1.getChildren().add(new ImageView(createImage(BG_PATH)));
        pane2.getChildren().add(new ImageView(createImage(BG_PATH)));
        pane3.getChildren().add(new ImageView(createImage(BG_PATH)));
        pane.getChildren().addAll(pane1,pane2,pane3);
        pane3.setLayoutY(GAME_HEIGTH-5);
        pane2.setLayoutY(GAME_HEIGTH-5);
    }
    /**
     * Metodo per muovere il background
     * @param GAME_HEIGTH
     * @return void
     */
    static void moveBackground(int GAME_HEIGTH){
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
