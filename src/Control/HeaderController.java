package Control;

import Model.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the Parent node. All scenes will be displayed
 * underneath the Header in the {@code #mainPane}.
 * The hierarchy is as follows:
 * <q>Header -> Main/SelectPractices -> Page(x)</q>.
 * That is, Main.fxml gets displayed in {@link HeaderController#mainPane}
 * and Page(x).fxml gets displayed in {@link MainController#mainPane}.
 *
 * @author Eric Pedrido
 */
public class HeaderController implements Initializable {
	@FXML public Button micTestButton;
	@FXML public Button homeButton;
	@FXML public AnchorPane mainPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPane();
	}

	@FXML
	public void home(ActionEvent actionEvent) {
		Mediator.getInstance().setPage("Root");
		loadPane();
	}

	/**
	 * Loads the page set in {@link Mediator} to
	 * the {@code #mainPane}. This ensures that
	 * the Header for the GUI stays the same throughout
	 * the program.
	 */
	public void loadPane() {
		VBox vBox = null;
		try {
			URL url = new File("src/GUI/" + Mediator.getInstance().getPage() + ".fxml").toURL();
			vBox = FXMLLoader.load(url);
			mainPane.getChildren().add(vBox);
		} catch (IOException e) {
			e.printStackTrace(); //todo GUI popup
		}
	}


}
