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
 * @author Eric Pedrido
 */
public class Mediator {

	private String _page;
	private static final Mediator MEDIATOR_SINGLETON = new Mediator();
	private MainController _main;
	private HeaderController _header;
	private List<String> _practiceList;
	private String _selectedFileName;
	private List<SubSceneController> _controllers = new ArrayList<>();

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

	public void showProgress(ProgressIndicator progress, double duration) {
		Timeline timeLine = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
				new KeyFrame(Duration.seconds(duration), Event::consume,
						new KeyValue(progress.progressProperty(), 1)));
		timeLine.setCycleCount(1);
		timeLine.play();
	}

	public void fireItemSelected() {
		for (SubSceneController controller : _controllers) {
			controller.itemSelected();
		}
	}

	public void fireOriginalSelected() {
		for (SubSceneController controller : _controllers) {
			controller.originalSelected();
		}
	}

	public void enableLists(boolean original, boolean main) {
		_main.enableLists(original, main);
	}

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
