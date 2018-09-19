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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * This class gets the original names and allows the user to select which names they want to practice
 * @author Lucy Chen
 */
public class ListController implements Initializable {


    /**
     * Components of SelectPractices.fxml
     */
    @FXML public Pane mainTitlePane;
    @FXML public Button mainMicTestButton;
    @FXML public Button mainHomeButton;
    @FXML public ListView selectListView;
    @FXML public VBox rootVBox;

    /**
     * sets the original names in the selectListView.
     * allows you to select multiple names at once.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> practiceNames = FXCollections.observableArrayList(Originals.getInstance().listNames());
        selectListView.setItems(practiceNames);
        selectListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * goes to home (Root.fxml)
     * @param actionEvent
     */
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

    /**
     * when the user clicks on the GO button, it takes them to Main.fxml to
     * start practicing their names.
     * adds the names they selected to the list of practice names in Practice class (package: Model)
     *
     */
    public void goAction(ActionEvent event) {

        Practices.getInstance().addNames(selectListView.getSelectionModel().getSelectedItems());
        Mediator.getInstance().setPage("Page3");
        try {
            URL url = new File("src/GUI/Main.fxml").toURL();
            VBox root = FXMLLoader.load(url);
            rootVBox.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * when the user clicks shuffle
     */
    public void shufflePracticeList(ActionEvent event){ //todo
        //Practices.getInstance().addNames(Collections.shuffle(Practices.getInstance().getPracticeNames()));
    }


}