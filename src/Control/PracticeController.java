package Control;

import Model.Mediator;
import Model.Practice;
import Model.Practices;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 * @author: Lucy Chen
 */
public class PracticeController {

    @FXML
    public Button playButton_3;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public VBox rootVBox;

    /**
     * Plays the original .wav file when selecting a name, and pressing the play button
     */
    public void playOriginal(ActionEvent event){
        System.out.println("played");
        String name = Practices.getInstance().getCurrentName();
        MediaPlayer mediaPlayer = new MediaPlayer(createMedia(name));
        mediaPlayer.play();
    }

    /**
     * creates a video from the .mp3 fileName
     * ISSUE: doesnt work... something wrong with .wav files???
     */
    public Media createMedia(String name) {
        File file = new File("Names/Ahn/Original/se206_18-5-2018_12-13-0_Ahn.wav"); //todo: add more methods in Originals
        String URL = file.toURI().toString();
        Media media = new Media(URL);
        return media;
    }


    /**
     * when user clicks on practice button
     * @param event
     */
    public void practiceName(ActionEvent event){
        Mediator.getInstance().setPage("Page4");
        switchTo("Main");

    }

    private void switchTo(String scene) {
        try {
            URL url = new File("src/GUI/" + scene + ".fxml").toURL();
            VBox root = FXMLLoader.load(url);
            rootVBox.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
