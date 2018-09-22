package Control;

import Model.Media;
import Model.Mediator;
import Model.Practices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the Parent node. All scenes will be displayed
 * underneath the Header in the {@code #mainPane}.
 * The hierarchy is as follows:
 * <q>Header -> Main/SelectPractices -> Page(x)</q>.
 * That is, Main.fxml gets displayed in {@link HeaderController#mainPane}
 * and Page(x).fxml gets displayed in {@link MainController#mainPane}.
 *
 */
public class HeaderController implements Initializable {

	@FXML public Button micTestButton;
	@FXML public Button homeButton;
	@FXML public AnchorPane mainPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Mediator.getInstance().setHeader(this);
		loadPane();
	}

	@FXML
	public void home(ActionEvent actionEvent) {
		Boolean safe = Mediator.getInstance().getState();
		if (!safe) {
			boolean confirmAction = false;
			confirmAction = confirmAction();

			// If the user confirms, delete it
			if (confirmAction) {
				goToRoot();
			}
		} else {
			goToRoot();
		}
		Mediator.getInstance().setState(true);

	}

	public void goToRoot(){
		Practices.getInstance().clearCurrentNames(); //clears the names
		Mediator.getInstance().setPage("Root");
		loadPane();
	}

	/**
	 * A confirmation popup that asks user if they want to delete their creation
	 */
	public boolean confirmAction() {
		Label l = new Label("Are you sure you want to abandon your practice?");
		l.setWrapText(true);

		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Abandon");
		alert.getDialogPane().setContent(l);

		//Creates the buttons
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
		ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
		alert.getButtonTypes().setAll(yesButton, noButton);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButton) {
			return true;
		} else {
			return false;
		}
	}

	@FXML
	public void testMic(MouseEvent mouseEvent) {
		micTestButton.disableProperty().setValue(true);
		micTestButton.setText("Testing...");
		micTestButton.setLayoutX(507.0);

		Thread thread = new Thread(() -> {
			Media.recordMicTest();
			Platform.runLater(() -> {
				micTestButton.disableProperty().setValue(false);
				micTestButton.setText("Test Mic");
				micTestButton.setLayoutX(514.0);
			});
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Loads the page set in {@link Mediator} to
	 * the {@code #mainPane}. This ensures that
	 * the Header for the GUI stays the same throughout
	 * the program.
	 */
	public void loadPane() {
		if (Mediator.getInstance().getPage().equals("Root")) {
			homeButton.setDisable(true);
		} else {
			homeButton.setDisable(false);
		}

		VBox vBox = null;
		try {
			URL url = new File("src/GUI/" + Mediator.getInstance().getPage() + ".fxml").toURL();
			vBox = FXMLLoader.load(url);
			mainPane.getChildren().addAll(vBox);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
