package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * This class represents a single recording for a particular name.
 * This file is saved as a .wav in the directory:
 * Names/<Name>/Practices/<file>.wav
 */


public class Practice {

    private String _fileName;
    private String _name;
    private File DIRECTORY = new File("Names/" + _name + "/Practice");

    /**
     * Constructor for the class
     * @param name
     */
    public Practice(String name) {
        _fileName = name;
        _name = name;
    }

    /**
     * creates a creaton
     */

    public void create() {
        justAudio();
    }

    /**
     * this deletes a creation
     */
    public void delete() {
        deleteFile(_fileName + ".mp4");
    }

    /**
     * this creates the audio component of the creation
     */
    public void justAudio() {
        String command = "ffmpeg -f pulse -loglevel quiet -i default -t 5 \"output\".mp3";
        process(command);
    }

      /**
     *deletes a file specified by param
     * @param filePath must of the format filename.mp3 filename.mp4 etc
     */
    public void deleteFile(String filePath) {
        Path path = Paths.get(_name + "Practices/" + filePath);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    /**
     * method processes a bash command
     */
    protected void process(String command) {
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
        pb.directory(DIRECTORY);

        try {
            java.lang.Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return file name without extension
     */
    public String getFileName(){
        return _fileName;
    }

    /**
     * Return the final .mp4 file
     */
    public File mp4File() {
        return new File(_name + "Practices/" + System.getProperty("file.separator") +_fileName + ".mp4");
    }
}