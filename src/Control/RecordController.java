package Control;

import Model.Media;
import Model.Mediator;
import Model.Practices;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class RecordController {

    @FXML
    public Button recordButton_4;
    public ProgressIndicator recordProgress;

    /**
     * User prompted to record audio (generates creation which is added to the model)
     * user is then asked to review the audio through another pop up.
     */
    public void recordAudioPopUp() {
        Mediator.getInstance().showProgress(recordProgress, 5);
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
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
            
    }

    public void record(ActionEvent event){
        recordAudioPopUp();
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
            done();

            alert.close();
        } else if (result.get() == listen) {
            Thread thread = new Thread(() -> {
                String name = Practices.getInstance().getCurrentName();
                String fileName = Practices.getInstance().getSelectFile();

                Media media = new Media(Practices.getInstance().getPractice(name, fileName));
                media.play();
            });
            thread.setDaemon(true);
            thread.start();
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
        String name = Practices.getInstance().getCurrentName();
        String fileName = Practices.getInstance().getFileName();
        System.out.println(name);
        System.out.println("file to delete"+ fileName);
        Practices.getInstance().deletePractice(name, fileName);
    }

    public void done() {
        Mediator.getInstance().setPage("Page6");
        Mediator.getInstance().loadHeaderPane();
    }
}
