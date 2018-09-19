package Control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RootController {

	@FXML public VBox rootVBox;
	@FXML public Button micTestButton_1;
	@FXML public Button homeButton_1;
	@FXML public Label practiceLabel_1;
	@FXML public Label listenLabel_1;

	@FXML
	public void practice1(MouseEvent mouseEvent) {
		try {
			URL url = new File("src/GUI/Main.fxml").toURL();
			VBox root = FXMLLoader.load(url);
			rootVBox.getChildren().setAll(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
