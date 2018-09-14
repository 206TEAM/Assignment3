package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * This class creates a practice creation
 */

public class Practice {

    private String _fileName;
    private String _name;

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
        justVideo();
        justAudio();
        combine();
        deleteAudioVideo();
    }

    public void create2() {
        String command = "> " +  _fileName + ".txt";
        //process(command);
    }

    /**
     * this deletes a creation
     */
    public void delete() {
        deleteFile(_fileName + ".mp4");
    }

    /**
     * this creates the video component for the creation
     */
    public void justVideo() {
        String command = "ffmpeg -f lavfi -i " + "color=c=black:s=320x240:d=5 -vf "
                + "\"drawtext=fontfile=/path/to/font.ttf:fontsize=30:"
                + " fontcolor=white:x=(w-text_w)/2:y=(h-text_h)/2:text='" + _fileName + "'\" "
                + "\"output.mp4\"";
        process(command);
    }

    /**
     * this creates the audio component of the creation
     */
    public void justAudio() {
        String command = "ffmpeg -f pulse -loglevel quiet -i default -t 5 \"output\".mp3";
        process(command);
    }

    /**
     * this combines the audio and video components of the creation
     */
    public void combine() {
        String command = "ffmpeg -i \"output\".mp4 -loglevel quiet -i \"output\".mp3 -c:v copy -c:a aac -strict experimental \"" + _fileName + "\".mp4";
        process(command);
    }

    /**
     * this removes the audio and video components of the creation (unnecessary files)
     */
    public void deleteAudioVideo(){
        deleteFile("output.mp3");
        deleteFile("output.mp4");
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
        pb.directory();

        try {
            java.lang.Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return file name without extension e.g mark
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