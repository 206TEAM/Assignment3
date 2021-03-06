package Control;

import Model.Mediator;
import Model.Original;
import Model.Originals;
import Model.Practices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls interactions on the <q>Main</q> scene.
 * It contains an {@code AnchorPane} with which scenes named <q>Page(x).fxml</q>
 * are displayed. <q>Main</q> itself is displayed within {@link HeaderController#mainPane}.
 *
 * <p> It also contains a side bar with a constantly updating sub-list {@code Practices}
 * selected by the user.</p>
 */
public class MainController implements Initializable {

    @FXML public AnchorPane mainPane; // Left blank. To be subject to change.
    @FXML public VBox rootVBox;
    @FXML public Label nameLabel_3;
	@FXML public ListView<String> mainListView;
	@FXML public Button addName;
	@FXML public Label ratingLabel;
	@FXML public ListView<String> originalListView;

	/**
     * Loads the scene that is currently set in {@link Mediator} into
     * the {@code #mainPane}.
     *
     * <p> Also populates {@code mainListView} with the sub-list of {@code Practices}
     * selected by the user.</p>
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	Mediator.getInstance().setMain(this);
    	loadPane();
	    List<String> practiceNamesList = Practices.getInstance().getPracticeNames();
	    ObservableList<String> practiceNames = FXCollections.observableArrayList(practiceNamesList);
        mainListView.setItems(practiceNames);
        mainListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	/**
	 * Reloads the items displayed on {@code #mainListView}
	 */
	@FXML
	public void reload() {
		List<String> practiceNamesList = Practices.getInstance().getPracticeNames();

		ObservableList<String> practiceNames = FXCollections.observableArrayList(practiceNamesList);
		mainListView.setItems(practiceNames);
		originalListView.getItems().clear();
	}

	/**
     * Changes what scene is being displayed in the {@code #mainPane}.
     */
    public void loadPane() {
        VBox vBox = null;
        try {
            vBox = FXMLLoader.load(getClass().getResource("/"+ Mediator.getInstance().getPage() + ".fxml"));
            mainPane.getChildren().add(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRatings(Original original) {
	    int rating = Originals.getInstance().getRating(original);
	    if (rating > 0) {
	    	ratingLabel.setText("Rating: " + rating);
	    }
    }

    /**
     * when the user selects on a name they want to practice, it updates the name label
     * and sets {@link Model.Practices#_currentName} to the selected name.
     */
    public void selectName(MouseEvent event) {
        String name = mainListView.getSelectionModel().getSelectedItem();
        if (name != null) {
	        Mediator.getInstance().fireItemSelected();
        }
        nameLabel_3.setText(name);
        ratingLabel.setText("Rating: --");

        //gets filenames of names selected
        ObservableList<String> originals = FXCollections.observableArrayList(Originals.getInstance().getFileName(name));
        originalListView.setItems(originals);
        originalListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Practices.getInstance().setCurrentName(name);
    }

	/**
	 * Sets the label to the selected {@code Original} and
	 * notifies {@link Mediator} to notify all
	 * {@link SubSceneController} classes that an item was
	 * selected. This lifts user restrictions.
	 */
	public void selectOriginal(MouseEvent event) {
	    String fileName = originalListView.getSelectionModel().getSelectedItem();
	    String name = mainListView.getSelectionModel().getSelectedItem();

	    if (name != null) {
		    Mediator.getInstance().fireOriginalSelected();
	    }

	    Mediator.getInstance().setSelectedFileName(fileName);

	    if (fileName != null) {
		    String labelName;
		    Original original;
		    // Check if multiple versions of this Original exists
		    if (Originals.getInstance().getFileName(name).size() > 1) {
			    labelName = name + Originals.getInstance().extractVersion(fileName);
			    original = Originals.getInstance().getOriginalWithVersions(fileName, name);
		    } else {
			    labelName = name + Originals.getInstance().getOriginal(fileName).getVersion();
			    original = Originals.getInstance().getOriginal(fileName);
		    }
		    nameLabel_3.setText(labelName);
		    loadRatings(original);
	    }
    }

    public void disableLists(boolean original, boolean main) {
    	originalListView.setDisable(original);
    	mainListView.setDisable(main);
    	addName.setDisable(main);
    }

    public void enableLists(boolean original, boolean main) {
    	originalListView.setDisable(!original);
    	mainListView.setDisable(!main);
    	addName.setDisable(!main);

    }

	public void addName(ActionEvent actionEvent) {
    	Mediator.getInstance().setPage("SelectPractices");
    	Mediator.getInstance().setPracticeList(Practices.getInstance().getPracticeNames());
    	Mediator.getInstance().loadHeaderPane();
	}
}