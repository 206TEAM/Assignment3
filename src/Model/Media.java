package Model;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.file.FileSystems;

/**
 * Responsible for handling various interactions with the media
 * files referenced as {@link Original} and {@link Practice}.
 * Also contains methods allowing for a microphone test.
 *
 * @author Eric Pedrido
 */
public class Media {

	private String _fileName;
	private String _originalName;
	private File _directory;

	public Media(Practice practice) {
		_fileName =  practice.getFileName() + ".wav";
		_originalName = null;
		_directory = practice.getDirectory();
	}

	public Media(Original original) {
		_fileName = original.getFileName();
		_originalName = original.getName();
		_directory = original.getDirectory();
	}

	/**
	 * Plays the {@code Practice} or {@code Original}
	 * depending on which constructor is called.
	 */
	public void play() {
		String command = "ffplay -autoexit -nodisp -i " + _fileName;
		process(command, _directory);
	}

	/**
	 * Records a new {@code Practice} of the {@code Original}.
	 * This method can only be used when the constructor called
	 * is {@link #Media(Original)} because you cannot record
	 * a practice of a {@code Practice}.
	 */
	public void record()  {
		if (_originalName != null) {
			Practice practice = new Practice(_originalName);

			String command = "ffmpeg -f alsa -i default -t 5 " + practice.filePath();
			File directory = FileSystems.getDefault().getPath(".").toFile();
			process(command, directory);

			Practices.getInstance().addPractice(_originalName, practice);
		}
	}



	/**
	 * Processes a bash command.
	 *
	 * @param command bash command to be processed.
	 * @param directory working directory for bash command.
	 */
	public static void process(String command, File directory) {
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
		pb.directory(directory);

		try {
			Process process = pb.start();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
