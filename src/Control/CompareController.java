package Control;

import Model.Mediator;
import Model.Originals;
import Model.Practices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class CompareController {

	@FXML public Button doneButton_5;
	@FXML public Button playOriginalButton_5;
	@FXML public Slider originalSlider_5;
	@FXML public Slider practiceSlider_5;
	@FXML public Button playPracticeButton_5;

	@FXML
	public void done(ActionEvent actionEvent) {
		Mediator.getInstance().setPage("Page6");
		Mediator.getInstance().loadPane();
	}
	@FXML
	public void playOriginal(ActionEvent actionEvent) {
		String name = Practices.getInstance().getCurrentName();
		Originals.getInstance().playOriginal(name);
	}
	@FXML
	public void playPractice(ActionEvent actionEvent) {
		//todo Implement a method in Practices that can fetch a Practice object given its name.
	}
}