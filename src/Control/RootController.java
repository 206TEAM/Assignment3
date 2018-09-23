package Control;

import Model.Mediator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Controls interactions on the <q>Root</q> scene which is
 * more easily identified as the home page.
 */
public class RootController {

	@FXML public VBox rootVBox;
	@FXML public Label practiceLabel_1;
	@FXML public Label listenLabel_1;

	/**
	 * when user clicks on practice, it takes them to to practice page
	 * @param mouseEvent
	 */
	@FXML
	public void practice1(MouseEvent mouseEvent) {
		Mediator.getInstance().setPage("SelectPractices");
		Mediator.getInstance().loadHeaderPane();
		Mediator.getInstance().setState(false);
	}

	/**
	 * when user clicks on listen, it takes them to the listen page
	 * @param mouseEvent
	 */
	@FXML
	public void listen1(MouseEvent mouseEvent) {
		Mediator.getInstance().setPage("Page6");
		Mediator.getInstance().loadHeaderPane();
		Mediator.getInstance().setState(true);
	}
}
