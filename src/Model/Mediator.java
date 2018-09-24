package Model;

import Control.HeaderController;
import Control.MainController;
import Control.SubSceneController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a mediator for which various controllers
 * can communicate with each other.
 *
 */
public class Mediator {

	private String _page;
	private static final Mediator MEDIATOR_SINGLETON = new Mediator();
	private MainController _main;
	private HeaderController _header;
	private List<String> _practiceList;
	private String _selectedFileName;
	private List<SubSceneController> _controllers = new ArrayList<>();
	private boolean _safe;

	public void setState(Boolean bool){ _safe = bool; }

	public Boolean getState(){ return _safe; }

	public void setPage(String page) {
		_page = page;
	}

	public void setMain(MainController main) {
		_main = main;
	}

	public void setHeader(HeaderController header) {
		_header = header;
	}

	public void addController(SubSceneController controller) {
		_controllers.add(controller);
	}

	public String getPage() {
		return _page;
	}

	public void setPracticeList(List<String> practiceList) {
		_practiceList = practiceList;
	}

	public void setSelectedFileName(String fileName) {
		_selectedFileName = fileName;
	}

	public String getSelectedFileName() {
		return _selectedFileName;
	}

	public void loadMainPane() {
		_main.loadPane();
	}

	public void loadHeaderPane() {
		_header.loadPane();
	}

	/**
	 * Sets the duration of which the progress bar goes from
	 * 0 to 100 to be the length that the audio file it is
	 * playing plays for.
	 *
	 * @param progress the {@code ProgressIndicator} being displayed
	 * @param dir whether it is an {@code Original} or a {@code Practice}
	 * @param fileName name of the file being played
	 */
	public void showProgress(ProgressIndicator progress, String dir, String fileName) {
		double duration = 0;
		try {
			String name = Practices.getInstance().getCurrentName();
			File file = new File("Names/" + name + "/" + dir +  "/" + fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			AudioFormat format = ais.getFormat();

			long frames = ais.getFrameLength();
			duration = (frames+0.0) / format.getFrameRate();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}

		showProgress(progress, duration);
	}

	/**
	 * Sets the duration of which the progress bar goes
	 * from 0 to 100 to be the duration entered.
	 *
	 * @param progress the {@code ProgressIndicator} being displayed
	 * @param duration the duration (in seconds)
	 */
	public void showProgress(ProgressIndicator progress, double duration) {
		Timeline timeLine = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
				new KeyFrame(Duration.seconds(duration), Event::consume,
						new KeyValue(progress.progressProperty(), 1)));
		timeLine.setCycleCount(1);
		timeLine.play();
	}

	/**
	 * Notify all {@code SubSceneController} classes that an item
	 * in {@link MainController#mainListView} has been selected
	 */
	public void fireItemSelected() {
		for (SubSceneController controller : _controllers) {
			controller.itemSelected();
		}
	}

	/**
	 * Notify all {@code SubSceneController} classes that an item
	 * in {@link MainController#originalListView} has been selected
	 */
	public void fireOriginalSelected() {
		for (SubSceneController controller : _controllers) {
			controller.originalSelected();
		}
	}

	/**
	 * Allows the user to select items on the list
	 *
	 * @param original if the user should be able to select
	 *                 items on {@link MainController#originalListView}
	 * @param main if the user should be able to select items on
	 *             {@link MainController#mainListView}
	 */
	public void enableLists(boolean original, boolean main) {
		_main.enableLists(original, main);
	}

	/**
	 * Similar to {@link #enableLists(boolean, boolean)} except
	 * it disables users from selecting items on the lists.
	 *
	 * @param original if the user should not be able to
	 *                 select items on {@link MainController#originalListView}
	 * @param main if the user should not be able to
	 *             select items on {@link MainController#mainListView}
	 */
	public void disableLists(boolean original, boolean main) {
		_main.disableLists(original, main);
	}

	public void reloadMain() {
		_main.reload();
	}

	public static Mediator getInstance() {
		return MEDIATOR_SINGLETON;
	}
}
