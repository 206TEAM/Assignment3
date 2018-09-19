package Control;

import Model.Mediator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Controls interactions on the <q>Root</q> scene which is
 * more easily identified as the home page.
 *
 * @author Eric Pedrido
 */
public class RootController {

	@FXML public VBox rootVBox;
	@FXML public Label practiceLabel_1;
	@FXML public Label listenLabel_1;

	@FXML
	public void practice1(MouseEvent mouseEvent) {
		switchTo("SelectPractices");
	}

	@FXML
	public void listen1(MouseEvent mouseEvent) {
		Mediator.getInstance().setPage("Page6");
		switchTo("Main");
	}

	/**
	 * Switches to a different scene depending on the input.
	 * Since all scenes get displayed in {@link HeaderController#mainPane},
	 * this switches what scene gets displayed.
	 *
	 * <p> The only two options are <q>Main</q> and <q>SelectPractices</q>
	 * because all other scenes, except for <q>Root</q>, are displayed
	 * within {@link MainController#mainPane}. </p>
	 *
	 * @param scene the name of the scene to switch to
	 *
	 * @see HeaderController
	 */
	private void switchTo(String scene) {
		try {
			URL url = new File("src/GUI/" + scene + ".fxml").toURL();
			VBox root = FXMLLoader.load(url);
			rootVBox.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
