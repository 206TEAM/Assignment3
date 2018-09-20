package Control;

import Model.Practice;
import Model.Practices;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Optional;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 * @author: Lucy Chen
 */
public class PracticeController {

    @FXML
    public Button playButton_3;

    /**
     * fields
     */
    String _fileName;

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
     * User prompted to record audio (generates creation which is added to the model)
     * user is then asked to review the audio through another pop up.
     */
    public void recordAudioPopUp() {
        Label l = new Label("Do you wish to record?");
        l.setWrapText(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recording");
        alert.setHeaderText("Recording");
        alert.getDialogPane().setContent(l);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert a = recordingDialog();

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //generates file which adds to creation list
                    String name = Practices.getInstance().getCurrentName();
                    System.out.println(name);
                    String fileName = Practices.getInstance().addNewPractice(name); //todo change addnewpractice return value
                    System.out.println("don1e");
                    Practices.getInstance().setFileName(fileName);
                    System.out.println("done");
                    return null;
                }
            };

            //after creating the creation, user reviews the audio
            task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> {
                reviewAudioPopUp();
                a.hide();
            });

            a.show();
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } else {
            alert.close();
        }
    }

    public Alert recordingDialog() {
        // show recording dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Recording");
        alert.setHeaderText("Recording dialog");
        ButtonType dismissButton = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(dismissButton);

        return alert;
    }

    public Alert reviewAudioPopUp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //type of alert is a CONFIRMATION
        alert.setTitle("Review"); //sets the title of alert
        alert.setHeaderText("Review"); //sets the header
        alert.setContentText("what would you like to do?");

        ButtonType record = new ButtonType("Record", ButtonBar.ButtonData.OTHER);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType listen = new ButtonType("Listen", ButtonBar.ButtonData.OTHER);
        ButtonType done = new ButtonType("Done", ButtonBar.ButtonData.FINISH);

        alert.getButtonTypes().setAll(done, listen, record, cancel);

        Optional<ButtonType> result = alert.showAndWait(); //takes the selected button

        if (result.get() == done) { //todo
            //list_creations.getItems().add(name);
            alert.close();
        } else if (result.get() == listen) { //todo
           // listenCreation(name);
            alert.close();
        } else if (result.get() == record) {
            removeAudio();
           recordAudioPopUp();
            alert.close();

        } else {
            alert.close();
        }

        return alert;

    }

    /**
     * when user clicks on practice button
     * @param event
     */
    public void practiceName(ActionEvent event){
        recordAudioPopUp();
    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void removeAudio() {
        Practices.getInstance().deletePractice(Practices.getInstance().getCurrentName(), Practices.getInstance().getFileName());
    }

}
