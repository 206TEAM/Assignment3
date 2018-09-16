package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single recording for a particular name.
 * This file is saved as a .wav in the directory:
 * Names/<_nameKey>/Practices/<fileName>.wav
 *
 * @author: Lucy Chen
 */


public class Practice {

    private String _fileName;
    private String _nameKey;
    private File DIRECTORY = new File("Names/" + _nameKey + "/Practice");

    /**
     * Constructor for the class
     * @param nameKey
     */
    public Practice(String nameKey) {
        _nameKey = nameKey;
        _fileName = generateFileName(nameKey);
    }

    /**
     * creates a practice
     */
    public void create() {
        justAudio();
    }

    /**
     * this deletes a practice
     */
    public void delete() {
        deleteFile(_fileName + ".mp4");
    }

    /**
     * this creates the audio component of the practice
     */
    public void justAudio() {
        String command = "ffmpeg -f pulse -loglevel quiet -i default -t 5 \""+ _fileName + "\".mp3";
        process(command);
    }

      /**
     *deletes a file specified by the fileName
     * @param fileName must of the format filename.mp3 filename.mp4 etc
     */
    public void deleteFile(String fileName) {
        Path path = Paths.get("Names/" + _nameKey + "Practices/" + fileName);
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
     * generates file name based on other recordings...
     * !!!!!!!!!bad method, find a better way
     * @param nameKey
     * @return
     */
    // todo: find a better way
    private String generateFileName(String nameKey){
        List<String> names = new ArrayList<String>();

        File file = new File("Names/" + nameKey + "/Practice");
        File[] fileList = file.listFiles();

        for (File f : fileList) {
           names.add(f.getName()); //adds the file names from directory into the list
        }

        return nameKey + "Practices" + Integer.toString(names.size());
        
    }

    /**
     * @return fileName without extension
     */
    public String getFileName(){
        return _fileName;
    }

    /**
     * Return the final .wav filepath
     */
    public File filePath() {
        return new File("Names" + System.getProperty("file.separator") + _nameKey + System.getProperty("file.separator") + "Practices" + System.getProperty("file.separator") +_fileName + ".mp4");
    }
}