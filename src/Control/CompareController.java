package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.util.List;

public class CompareController {

    @FXML
    public Button doneButton_5;
    @FXML
    public Button playOriginalButton_5;
    @FXML
    public Button playPracticeButton_5;
    @FXML
    public ProgressBar originalProgressBar;
    @FXML
    public ProgressBar practiceProgressBar;

    @FXML
    public void done(ActionEvent actionEvent) {
	    List<String> currentNames = Practices.getInstance().getPracticeNames();
	    if (currentNames.size() == 0) { //done
		    Mediator.getInstance().setPage("Page6");
		    Mediator.getInstance().loadHeaderPane();
	    } else {
	    	//todo remove the currently selected name from the list.
		    Mediator.getInstance().setPage("Page4");
		    Mediator.getInstance().loadMainPane();
	    }
    }

    @FXML
    public void playOriginal(ActionEvent actionEvent) {
        String name = Practices.getInstance().getCurrentName();
        String fileName = Mediator.getInstance().getSelectedFileName();
        Thread thread = new Thread(new Runnable() {
	        @Override
	        public void run() {
		        Original original;
		        Media media;

		        if (Originals.getInstance().getFileName(name).size() > 1) {
			        original = Originals.getInstance().getOriginalWithVersions(fileName, name);
		        } else {
			        original = Originals.getInstance().getOriginal(fileName);
		        }
		        media = new Media(original);
		        media.play();
	        }
        });
	    thread.setDaemon(true);
	    thread.start();

        Mediator.getInstance().showProgress(originalProgressBar, "Original", fileName);
    }

    @FXML
    public void playPractice(ActionEvent actionEvent) {
        String name = Practices.getInstance().getCurrentName();
        String fileName = Practices.getInstance().getFileName();
        Thread thread = new Thread(new Runnable() {
	        @Override
	        public void run() {
		        Media media = new Media(Practices.getInstance().getPractice(name, fileName));
		        media.play();
	        }
        });
	    thread.setDaemon(true);
        thread.start();

        Mediator.getInstance().showProgress(practiceProgressBar, "Practices", fileName + ".wav");
    }
}
