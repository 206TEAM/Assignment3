package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the original sound recordings from the Recordings folder.
 *
 * On initialisation it creates folders of all the names from the Recordings folder.
 * It then creates subfolders Original and Practices.
 * It then adds the correct audio files into their respective subfolders.
 *
 * It also stores each original file in a List (probably a field)
 *
 * If specified the name of the original file, there are methods which retrieve the full path of the file
 * giving it in the format of e.g "Names/Mason/Originals/mason.wav (note the .wav extension)
 *
 * @author Eric Pedrido
 */
public class Original {

    //i would probably have an update method, a getFullPath(name) method etc
    //ive also added your methods below:
    //i guess keep this class quite separate to Practices (except for creating the folder of course)
    //also its good practice to check if there already exists folders in the directory
    //e.g if (folder or file DOESNT exist...add it).

    private String _name;
    private String _fileName;

    /**
     * An Original creation is provided by <dir>Recordings</dir> and so
     * this constructor doesn't construct an Original creation.
     * Instead, it creates a reference to the corresponding file.
     *
     * @param fileName
     *        Name of the original creation to reference.
     *
     * @throws FileNotFoundException
     *         If the requested file doesn't exist.
     */
    public Original(String fileName) throws FileNotFoundException {
        // Find the file with the enterred filename
        if (Files.exists(Paths.get("Recordings/" + fileName))) {
            _fileName = fileName;

        } else {
            throw new FileNotFoundException();
        }
    }

}
