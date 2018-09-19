package Control;

import Model.Mediator;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class controls the main section of the class which controls Main.fxml
 * it contains a side bar with a constantly updating list of practices the user is about to do.
 * @author: Lucy Chen
 * @author: Eric Pedrido
 */
public class MainController implements Initializable {

    /**
     * Components of Main.fxml
     */
    @FXML public Pane mainTitlePane;
    @FXML public Button mainMicTestButton;
    @FXML public Button mainHomeButton;
    @FXML public AnchorPane mainPane; // Left blank. To be subject to change.
    @FXML public VBox rootVBox;
    @FXML public Label nameLabel_3;
    @FXML public Label ratingLabel_3;
    @FXML private ListView<String> mainListView;

    /**
     * fields
     */
    public List<String> _practiceNamesList = Practices.getInstance().getPracticeNames();

    /**
     * populates the mainListView with the practices the user has previously selected.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPane();
        ObservableList<String> practiceNames = FXCollections.observableArrayList(_practiceNamesList);
        mainListView.setItems(practiceNames);
        mainListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Changes what is being displayed in the {@code #mainPane}
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

    /**
     * when the user selects on a name they want to practice, it updates the name label
     * and sets the currently practicing name in the Practice class (package: Model)
     * to the selected name.
     * @param event
     */
    public void selectName(MouseEvent event){
        String name = mainListView.getSelectionModel().getSelectedItem();
        nameLabel_3.setText(name);
        Practices.getInstance().setCurrentName(name);
    }










}