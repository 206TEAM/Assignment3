package Control;

import Model.Mediator;
import Model.Original;
import Model.Originals;
import Model.Practices;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * This class controls Page3 where the user can choose to play and/or practice a name
 *
 * @author Lucy Chen
 * @author Eric Pedrido
 */
public class PracticeController {

	@FXML public Button playButton_3;
	@FXML public Button practiceButton_3;
	@FXML public ProgressBar progressBar;

	/**
	 * Plays the original .wav file when selecting a name, and pressing the play button
	 */
	public void playOriginal(ActionEvent event) {
		System.out.println("played");
		String name = Practices.getInstance().getCurrentName();
		Thread thread = new Thread(() -> {
			Originals.getInstance().playOriginal(name);
		});
		thread.setDaemon(true);
		thread.start();
		showProgress();
//        MediaPlayer mediaPlayer = new MediaPlayer(createMedia(name));
//        mediaPlayer.play();

	}

	/**
	 * creates a video from the .mp3 fileName
	 * ISSUE: doesnt work... something wrong with .wav files???
	 */
	public Media createMedia(String name) {
		File file = new File("Names/Ahn/Original/se206_18-5-2018_12-13-0_Ahn.wav"); //todo: add more methods in Originals
		String URL = file.toURI().toString();
		Media media = new Media(URL);
		return media;
	}

	/**
	 * when user clicks on practice button
	 *
	 * @param event
	 */
	public void practiceName(ActionEvent event) {
		Mediator.getInstance().setPage("Page4");
		Mediator.getInstance().loadPane();
	}

	private void showProgress() {
		double duration = 0;
		try {
			String name = Practices.getInstance().getCurrentName();
			String fileName = Originals.getInstance().getFileName(name).get(0);
			File file = new File("Names/" + name + "/Original/" + fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			AudioFormat format = ais.getFormat();

			long frames = ais.getFrameLength();
			duration = (frames+0.0) / format.getFrameRate();
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}

		Timeline timeLine = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
				new KeyFrame(Duration.seconds(duration), Event::consume,
						new KeyValue(progressBar.progressProperty(), 1)));
		timeLine.setCycleCount(1);
		timeLine.play();
	}
}
