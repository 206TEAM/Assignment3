package Control;

import Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;


/**
 * This class contains methods that populates the Listview with the list thing
 */
public class ListController implements Initializable {

    private Practices _model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> practiceNames = FXCollections.observableArrayList(Originals.getInstance().listNames());
        listView_2.setItems(practiceNames);
        listView_2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * initialises the creations model
     */
    public ListController() {
        _model = new Practices();
    }

    @FXML
    private ListView<String> listView_2;


}