package Control;

import Model.Mediator;
import Model.Original;
import Model.Originals;
import Model.Practices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 * @author Lucy Chen
 */
public class PracticeController {

    @FXML public Button playButton_3;
    @FXML public Button practiceButton_3;
    @FXML public Slider originalSlider_3;

    /**
     * Plays the original .wav file when selecting a name, and pressing the play button
     */
    public void playOriginal(ActionEvent event){
        System.out.println("played");
        String name = Practices.getInstance().getCurrentName();
	    Original original = Originals.getInstance().getOriginal(name);
//        String name = Practices.getInstance().getCurrentName();
//        MediaPlayer mediaPlayer = new MediaPlayer(createMedia(name));
//        mediaPlayer.play();
	    Model.Media media = new Model.Media(original);
	    media.play();
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
        Mediator.getInstance().loadPane();
    }
}
