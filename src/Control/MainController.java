package Control;

import Model.Mediator;
import Model.Original;
import Model.Originals;
import Model.Practices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controls interactions on the <q>Main</q> scene.
 * It contains an {@code AnchorPane} with which scenes named <q>Page(x).fxml</q>
 * are displayed. <q>Main</q> itself is displayed within {@link HeaderController#mainPane}.
 *
 * <p> It also contains a side bar with a constantly updating sub-list {@code Practices}
 * selected by the user.</p>
 *
 * @author Lucy Chen
 * @author Eric Pedrido
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
     *
     * @param location
     * @param resources
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
     * Changes what scene is being displayed in the {@code #mainPane}.
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

    private void loadRatings(Original original) {
	    int rating = Originals.getInstance().getRating(original);
	    if (rating > 0) {
	    	ratingLabel.setText("Rating: " + rating);
	    }
    }

    /**
     * when the user selects on a name they want to practice, it updates the name label
     * and sets the currently practicing name in the Practice class (package: Model)
     * to the selected name.
     *
     * @param event
     */
    public void selectName(MouseEvent event){
        String name = mainListView.getSelectionModel().getSelectedItem();
        nameLabel_3.setText(name);
        ratingLabel.setText("Rating: --");

        //gets filenames of names selected
        ObservableList<String> originals = FXCollections.observableArrayList(Originals.getInstance().getFileName(name));
        originalListView.setItems(originals); //todo
        originalListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Practices.getInstance().setCurrentName(name);

	    String fileName = originalListView.getSelectionModel().getSelectedItem();

        if (fileName != null) {
	        String labelName;
	        Original original;
        	if (originals.size() > 1) {
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


	public void addName(ActionEvent actionEvent) { //todo
    	Mediator.getInstance().setPage("SelectPractices");
    	Mediator.getInstance().setPracticeList(Practices.getInstance().getPracticeNames());
    	Mediator.getInstance().loadHeaderPane();
	}
}