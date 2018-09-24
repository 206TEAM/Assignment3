package Control;

import Model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * This class gets all of the original names and allows the user
 * to select which names they want to practice.
 * It has a go button which takes them to the next page.
 * The shuffle button shuffles the currently selected practices. 
 */
public class ListController implements Initializable {

    /**
     * Components of SelectPractices.fxml
     */
    @FXML public ListView selectListView;
    @FXML public VBox rootVBox;
	@FXML public CheckBox shuffleCheckbox_2;
	@FXML public Button goButton_2;
	@FXML public Label emptyRecordingsLabel;

    private boolean _shuffle;

	/**
     * sets the original names in the selectListView.
     * allows you to select multiple names at once.
     * @param location
     * @param resources
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	goButton_2.setDisable(true);
    	shuffleCheckbox_2.setDisable(true);
	    _shuffle = false;

        List<String> names = Originals.getInstance().listNames();

        if (names.size() == 0) {
        	emptyRecordingsLabel.setVisible(true);
        } else {
        	emptyRecordingsLabel.setVisible(false);
	        java.util.Collections.sort(names);
	        ObservableList<String> practiceNames = FXCollections.observableArrayList(names);
	        selectListView.setItems(practiceNames);
	        selectListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

	/**
     * when the user clicks on the GO button, it takes them to Main.fxml to
     * start practicing their names.
     * adds the names they selected to the list of practice names in Practice class (package: Model)
     *
     */
    public void goAction(ActionEvent event) {
        Mediator.getInstance().setState(false);
        List<String> list = selectListView.getSelectionModel().getSelectedItems();
        List<String> array = new ArrayList<String>();
        array.addAll(list);
        if (_shuffle){
            Collections.shuffle(array);
        }

        Practices.getInstance().addNames(array);
        Mediator.getInstance().setPage("Page3");
        try {

            //URL url = new File("src/GUI/Main.fxml").toURL();
            VBox root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
            rootVBox.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shuffles {@link MainController#mainListView} if
     * the checkbox is selected.
     */
    public void shufflePracticeList(ActionEvent event){
	    _shuffle = shuffleCheckbox_2.isSelected();
    }

	@FXML
	public void enableButtons(MouseEvent mouseEvent) {
    	if (selectListView.getItems().size() > 0) {
		    goButton_2.setDisable(false);
		    shuffleCheckbox_2.setDisable(false);
	    }
	}
}