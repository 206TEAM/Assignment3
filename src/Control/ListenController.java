package Control;

import Model.Originals;
import Model.Practices;
import Model.Media;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ListenController {
    @FXML
    public Button playButton_3;

    @FXML
    public ListView<String> practiceListView;

    @FXML
    public ListView<String> originalListView;


    /**
     * fields
     */
    String _selected;
    String _type;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        String name = Practices.getInstance().getCurrentName();
        ObservableList<String> originals = FXCollections.observableArrayList(Originals.getInstance().getFileName(name));
        ObservableList<String> practices = FXCollections.observableArrayList(Practices.getInstance().listPractices(name));
        practiceListView.setItems(practices);
        originalListView.setItems(practices); //todo

        originalListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }



    public void selectNamePractice(MouseEvent event){
        String name = practiceListView.getSelectionModel().getSelectedItem();
        //nameLabel_3.setText(name);
        _selected = name;
        _type = "practice";

    }

    public void selectNameOriginal(MouseEvent event){
        String name = originalListView.getSelectionModel().getSelectedItem();
        //nameLabel_3.setText(name);
        _selected = name;
        _type = "original";
    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void deleteFile() {
        if (_type == "practice"){
            String name = Practices.getInstance().getCurrentName();
            Practices.getInstance().deletePractice(name, _selected);
        } else {
            //todo
        }
    }

    /**
     * Plays the original .wav file when selecting a name, and pressing the play button
     */
    public void play(ActionEvent event){
        System.out.println("played");
        String name = Practices.getInstance().getCurrentName();
        Media media = new Media(Practices.getInstance().getPractice(name, _selected));
        media.play();
    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void removeAudio() {
        Practices.getInstance().deletePractice(Practices.getInstance().getCurrentName(), Practices.getInstance().getFileName());
    }

}
