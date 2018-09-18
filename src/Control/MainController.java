package Control;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {

	public Pane mainTitlePane;
	public Button mainMicTestButton;
	public Button mainHomeButton;
	public ListView mainListView;
	public AnchorPane mainPane;



	public void loadPane(String pane) {
		try {
			mainPane = FXMLLoader.load(getClass().getResource(pane + ".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
