package Control;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class control Page6.fxml
 */
public class ListenController implements Initializable {

    @FXML public Button playButton_3;
	@FXML public Button deleteButton_3;
    @FXML public ListView<String> practiceListView;
    @FXML public ListView<String> originalListView;
    @FXML public ListView<String> mainListView;
    @FXML public Label nameLabel_3;
	@FXML public ToggleGroup ratingGroup;
	@FXML public RadioButton rb1;
	@FXML public RadioButton rb2;
	@FXML public RadioButton rb3;
	@FXML public RadioButton rb4;
	@FXML public RadioButton rb5;
	@FXML public ProgressBar progressBar;
	@FXML public Label ratingLabel;

	/**
     * fields
     */
    String _selected;
    String _type;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    	showRatings(false);
	    playButton_3.setDisable(true);
	    deleteButton_3.setDisable(true);

    	ratingHandler();
        ObservableList<String> allNames = FXCollections.observableArrayList(Originals.getInstance().listNames());

        mainListView.setItems(allNames);
        mainListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void selectName(MouseEvent event) {
	    ratingLabel.setText("--");
	    showRatings(false);
	    clearRatings();

	    playButton_3.setDisable(true);
	    deleteButton_3.setDisable(true);

	    String name = mainListView.getSelectionModel().getSelectedItem();
        nameLabel_3.setText(name);
        Practices.getInstance().setCurrentName(name);
        populateSubLists();
    }


    public void selectNamePractice(MouseEvent event) {
	    String name = practiceListView.getSelectionModel().getSelectedItem();
    	if (name != null) {
    	    showRatings(false);
    	    playButton_3.setDisable(false);
    	    deleteButton_3.setDisable(false);
    	} else {
    		playButton_3.setDisable(true);
    		deleteButton_3.setDisable(true);
	    }

        //nameLabel_3.setText(name);
        _selected = name;
        _type = "practice";
    }

    public void selectNameOriginal(MouseEvent event) {
	    String fileName = originalListView.getSelectionModel().getSelectedItem();
	    String name = mainListView.getSelectionModel().getSelectedItem();

	    if (fileName != null) {
		    showRatings(true);
		    playButton_3.setDisable(false);
		    deleteButton_3.setDisable(true);
	    } else {
	    	playButton_3.setDisable(true);
	    }

        _selected = fileName;
        _type = "original";

        clearRatings();

        Original original;
	    if (Originals.getInstance().getFileName(name).size() > 1) {
		    original = Originals.getInstance().getOriginalWithVersions(fileName, name);
	    } else {
		    original = Originals.getInstance().getOriginal(fileName);
	    }
        loadRating(original);
    }

    public void populateSubLists() {

        String name = Practices.getInstance().getCurrentName();

        ObservableList<String> originals = FXCollections.observableArrayList(Originals.getInstance().getFileName(name));
        originalListView.setItems(originals); //todo
        originalListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        if (Practices.getInstance().listPractices(name) != null) {
            ObservableList<String> practices = FXCollections.observableArrayList(Practices.getInstance().listPractices(name));
            practiceListView.setItems(practices);
        } else {
        	practiceListView.getItems().clear();
		}
    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void deleteFile() {
        if (_type.equals("practice")){
            String name = Practices.getInstance().getCurrentName();
            if (name != null) {
	            Practices.getInstance().deletePractice(name, _selected);
	            practiceListView.getItems().remove(_selected);
	            if (practiceListView.getItems().size() < 1) {
	            	deleteButton_3.setDisable(true);
	            }
            }
        }
    }

    /**
     * Plays the original .wav file when selecting a name, and pressing the play button
     */
    public void play(ActionEvent event) {
        String name = Practices.getInstance().getCurrentName(); //getting the name
	    Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
			    Media media;
			    if (_type.equals("original")) { //if type is original

				    Original original;

				    if (Originals.getInstance().getFileName(name).size() > 1) {
					    original = Originals.getInstance().getOriginalWithVersions(_selected, name);
				    } else {
					    original = Originals.getInstance().getOriginal(_selected);
				    }

				    media = new Media(original);
			    } else { //type is practice
				    media = new Media(Practices.getInstance().getPractice(name, _selected));
			    }
			    media.play();
		    }
	    });
	    thread.setDaemon(true);
	    thread.start();
		if (_type.equals("original")) {
            Mediator.getInstance().showProgress(progressBar, "Original", _selected);
        } else {
			Mediator.getInstance().showProgress(progressBar, "Practices", _selected + ".wav");
		}

    }

    /**
     * gets rid of creation when user chooses to rerecord the audio.
     */
    public void removeAudio() {
        Practices.getInstance().deletePractice(Practices.getInstance().getCurrentName(), Practices.getInstance().getFileName());
    }


	/**
	 * Writes to <dir>Ratings.txt</dir> the desired rating of the user.
	 *
	 * <p> Puts all the buttons into a group so that only one can
	 * be selected at a time.</p>
	 */
	private void ratingHandler() {
		ratingGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			String fileName = originalListView.getSelectionModel().getSelectedItem();
			String name = mainListView.getSelectionModel().getSelectedItem();
			Original original;
			if (fileName != null) {
				if (Originals.getInstance().getFileName(name).size() > 1) {
					original = Originals.getInstance().getOriginalWithVersions(fileName, name);
				} else {
					original = Originals.getInstance().getOriginal(fileName);
				}
				if (newValue == rb1) {
					Originals.getInstance().setRating(original, 1);
				} else if (newValue == rb2) {
					Originals.getInstance().setRating(original, 2);
				} else if (newValue == rb3) {
					Originals.getInstance().setRating(original, 3);
				} else if (newValue == rb4) {
					Originals.getInstance().setRating(original, 4);
				} else if (newValue == rb5) {
					Originals.getInstance().setRating(original, 5);
				}
				loadRating(original);
			}
		});
	}

	private void loadRating(Original original) {
		ratingLabel.setText("" + Originals.getInstance().getRating(original));
	}

	private void clearRatings() {
		rb1.setSelected(false);
		rb2.setSelected(false);
		rb3.setSelected(false);
		rb4.setSelected(false);
		rb5.setSelected(false);
	}
	private void showRatings(boolean show) {
		show = !show;
		rb1.setDisable(show);
		rb2.setDisable(show);
		rb3.setDisable(show);
		rb4.setDisable(show);
		rb5.setDisable(show);
	}

}
