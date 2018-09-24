package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompareController implements SubSceneController {


	@FXML public Button doneButton_5;
	@FXML public Button playOriginalButton_5;
	@FXML public Button playPracticeButton_5;
	@FXML public ProgressBar originalProgressBar;
	@FXML public ProgressBar practiceProgressBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		playOriginalButton_5.setDisable(true);
		Mediator.getInstance().addController(this);
	}

	@FXML
	public void done(ActionEvent actionEvent) throws IOException {
		String currentName = Practices.getInstance().getCurrentName();
		Mediator mediator = Mediator.getInstance();
		Practices.getInstance().removePracticeName(currentName); //removes current name from list.

		mediator.reloadMain();

		List<String> currentNames = Practices.getInstance().getPracticeNames();
		if (currentNames.size() == 0) { //done
			mediator.setPage("Root");
			mediator.loadHeaderPane();
		} else {
			mediator.enableLists(true, true);
			mediator.setPage("Page3");
			mediator.loadMainPane();
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

	@Override
	public void itemSelected() {
		// Implementation not needed since the item will have already been selected.
	}

	@Override
	public void originalSelected() {
		// Even though it may have been selected earlier, there is no guarantee thus we must confirm selection.
		playOriginalButton_5.setDisable(false);
	}
}
