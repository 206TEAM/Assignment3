package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 *
 */
public class PracticeController implements SubSceneController {

	@FXML public Button playButton_3;
	@FXML public Button practiceButton_3;
	@FXML public ProgressBar progressBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playButton_3.setDisable(true);
		practiceButton_3.setDisable(true);
		Mediator.getInstance().addController(this);
	}

	/**
	 * Plays the original .wav file when selecting a name, and pressing the play button
	 */
	public void playOriginal(ActionEvent event) {
		String name = Practices.getInstance().getCurrentName();
		String fileName = Mediator.getInstance().getSelectedFileName();

		Thread thread = new Thread(() -> {
			Original original;

			if (Originals.getInstance().getFileName(name).size() > 1) {
				original = Originals.getInstance().getOriginalWithVersions(fileName, name);
			} else {
				original = Originals.getInstance().getOriginal(fileName);
			}
			Media media = new Media(original);
			media.play();
		});
		thread.setDaemon(true);
		thread.start();

		Mediator.getInstance().showProgress(progressBar, "Original", fileName);
	}

	/**
	 * when user clicks on practice button
	 *
	 * @param event
	 */
	public void practiceName(ActionEvent event) {
		Mediator.getInstance().setPage("Page4");
		Mediator.getInstance().loadMainPane();
	}

	public void addName(ActionEvent event){
		Mediator.getInstance().setPage("SelectPractices");
		Mediator.getInstance().loadHeaderPane();
	}

	@Override
	public void itemSelected() {
		practiceButton_3.setDisable(false);
	}

	@Override
	public void originalSelected() {
		playButton_3.setDisable(false);
	}
}
