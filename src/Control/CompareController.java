package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class CompareController {

	@FXML public Button doneButton_5;
	@FXML public Button playOriginalButton_5;
	@FXML public Button playPracticeButton_5;
	@FXML public ProgressBar originalProgressBar;
	@FXML public ProgressBar practiceProgressBar;

	@FXML
	public void done(ActionEvent actionEvent) {
		Mediator.getInstance().setPage("Page6");
		Mediator.getInstance().loadMainPane();
	}
	@FXML
	public void playOriginal(ActionEvent actionEvent) {
		String name = Practices.getInstance().getCurrentName();
		Originals.getInstance().playOriginal(name);
		Mediator.getInstance().showProgress(originalProgressBar,"Original");
	}
	@FXML
	public void playPractice(ActionEvent actionEvent) {
		String name = Practices.getInstance().getCurrentName();
		String fileName = Practices.getInstance().getSelectFile();
		Media media = new Media(Practices.getInstance().getPractice(name, fileName));
		media.play();
		Mediator.getInstance().showProgress(practiceProgressBar, "Practice");
	}

	public void done() {

	}
}
