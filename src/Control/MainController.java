package Control;

import Model.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	/**
	 * Components of Main.fxml
	 */
	@FXML public Pane mainTitlePane;
	@FXML public Button mainMicTestButton;
	@FXML public Button mainHomeButton;
	@FXML public ListView mainListView;
	@FXML public AnchorPane mainPane; // Left blank. To be subject to change.
	@FXML public VBox rootVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPane();
	}

	/**
	 * Changes what is being displayed in the {@code #mainPane}.
	 *
	 */
	@FXML
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

	@FXML
	public void home(ActionEvent actionEvent) {
		try {
			URL url = new File("src/GUI/Root.fxml").toURL();
			VBox root = FXMLLoader.load(url);
			rootVBox.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
