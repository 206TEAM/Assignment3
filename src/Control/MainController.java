package Control;

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

	private RootController _root;

	/**
	 * Components of Main.fxml
	 */
	public Pane mainTitlePane;
	public Button mainMicTestButton;
	public Button mainHomeButton;
	public ListView mainListView;
	public AnchorPane mainPane; // Left blank. To be subject to change.

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadPane("Page2");
	}

	/**
	 * Changes what is being displayed in the {@code #mainPane}.
	 *
	 * @param page the name of the <q>.fxml</q> file that is displayed
	 *             in {@code #mainPane}.
	 */
	public void loadPane(String page) {
		VBox vBox = null;
				
		try {
			URL url = new File("src/GUI/" + page + ".fxml").toURL();
			vBox = FXMLLoader.load(url);
			mainPane.getChildren().add(vBox);
		} catch (IOException e) {
			e.printStackTrace(); //todo GUI popup
		}
	}


}
