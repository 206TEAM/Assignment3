package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 *
 * @author Lucy Chen
 * @author Eric Pedrido
 */
public class PracticeController {

	@FXML public Button playButton_3;
	@FXML public Button practiceButton_3;
	@FXML public ProgressBar progressBar;

	/**
	 * Plays the original .wav file when selecting a name, and pressing the play button
	 */
	public void playOriginal(ActionEvent event) {
		System.out.println("played");
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
}
