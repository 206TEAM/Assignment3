package Control;

import Model.Media;
import Model.Mediator;
import Model.Practice;
import Model.Practices;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RecordController implements Initializable{

    @FXML public Button recordButton_4;
    @FXML public ProgressIndicator recordProgress;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordButton_4.setDisable(false);
    }

    /**
     * User prompted to record audio (generates creation which is added to the model)
     * user is then asked to review the audio through another pop up.
     */
    private void recordAudio() {
        Mediator.getInstance().showProgress(recordProgress, 5);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    //generates file which adds to creation list
                    String name = Practices.getInstance().getCurrentName();
                    String fileName = Practices.getInstance().addNewPractice(name);
                    Practices.getInstance().setFileName(fileName);
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

    /**
     * When user presses the record button.
     */
    public void record(ActionEvent event){
    	recordButton_4.setDisable(true);
        recordAudio();
    }

    /**
     * Alert that pops up when user clicks on record button. user can re record,
     * cancel or listen and save their recording.
     */
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

        if (result.get() == done) {
            done();

            alert.close();
        } else if (result.get() == listen) {
            Thread thread = new Thread(() -> {
                String name = Practices.getInstance().getCurrentName();
                String fileName = Practices.getInstance().getFileName();
                Media media = new Media(Practices.getInstance().getPractice(name, fileName));
                media.play();
            });
            thread.setDaemon(true);
            thread.start();
            reviewAudioPopUp();
        } else if (result.get() == record) {
            removeAudio();
            recordAudio();
            alert.close();

        } else {
        	Mediator mediator = Mediator.getInstance();
        	mediator.enableLists(true, true);
        	mediator.setPage("Page3");
        	mediator.loadMainPane();
            alert.close();
        }

        return alert;
    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void removeAudio() {
        String name = Practices.getInstance().getCurrentName();
        String fileName = Practices.getInstance().getFileName();
        Practices.getInstance().deletePractice(name, fileName);
    }

    /**
     * when user presses done, it changes the page
     */
    public void done() {
    	Mediator.getInstance().enableLists(true, false);
        Mediator.getInstance().setPage("Page5");
        Mediator.getInstance().loadMainPane();
    }
}
