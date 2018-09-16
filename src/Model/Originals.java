package Model;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a collection of {@link Original} objects.
 * It is responsible for handling their behaviour as a group, not
 * as individual files.
 *
 * <p> This class can only be instantiated once because it denies
 * the possibility for multiple lists of {@code Originals} to be used.
 * The one list generated from scanning the contents of <dir>Recordings</dir>
 * is in essence, the only true list of {@code Originals}.</p>
 *
 * @author Eric Pedrido
 */
public class Originals {

    private List<Original> _originals = new ArrayList<>();
    private final static Originals _SINGLETON = new Originals();

    /**
     * Upon construction, all folders will be populated with each
     * {@code Original} having its own respective sub-folder.
     * This must only happen on start-up of program.
     */
    private Originals() {
        updateModel();
        populateFolders();
    }

    /**
     * Reads all existing {@code Original} objects provided in <dir>Recordings</dir>
     * and creates <dir>Names/(name)</dir> if such folders do not already exist.
     *
     * <p> Each {@code Original} of unique name gets its own sub-folders where
     * the {@code Original} and {@code Practice} are stored.</p>
     *
     * <p> Should there be multiple {@code Original} files of the same name (not file name),
     * then multiple recordings will be stored in the same directory,
     * but will have a number at the end indicating which
     * version it is. For example
     * <dir>John Smith1.wav</dir>
     * <dir>John Smith2.wav</dir>.</p>
     */
    private void populateFolders() {
        try {
            List<String> names = listNames();
            List<String> fileNames = listFileNames();

            // Make a folder for each creation containing sub-folders
            for (String name : names) {
                if (Files.notExists(Paths.get("Names/" + name))) {
                    Files.createDirectories(Paths.get("Names/" + name + "/Original"));
                    Files.createDirectories(Paths.get("Names/" + name + "/Practices"));
                }
            }

            // Put the respective creations in their folders
            for (int i = 0; i < fileNames.size(); i++) {
                String creation = fileNames.get(i);
                String name = names.get(i);
                String finalName = creation;
                // Insert an int n for the nth version of that Original.
                if (names.lastIndexOf(name) != names.indexOf(name)) {
                    // The path will never be null, as the directory is always created above should it not exist.
                    int version = new File ("Names/" + name + "/Original").listFiles().length + 1;
                    StringBuilder tempName = new StringBuilder(creation);
                    tempName.insert(tempName.length() - 4, version);

                    finalName = tempName.toString();
                }
                if (Files.notExists(Paths.get("Names/" + name + "/Original/" + creation)))
                Files.copy(Paths.get("Recordings/" + creation),
                        Paths.get("Names/" + name + "/Original/" + finalName),
                        StandardCopyOption.COPY_ATTRIBUTES);
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e); // todo: replace with an error message in GUI
        }
    }

    /**
     * Checks <dir>Recordings</dir> to build a {@code List<String>}
     * containing the file name of every {@code Original}.
     *
     * <p> Note that a valid {@code Original} is any file that has
     * the file extension <q>.wav</q>.</p>
     *
     * @return a list of the file names of all existing {@code Original} files.
     * @throws IOException                if an I/O error occurs.
     * @throws DirectoryIteratorException if an error occurs while iterating through
     *                                    the directory.
     */
    public List<String> listFileNames() throws IOException, DirectoryIteratorException {
        List<String> fileNames = new ArrayList<>();
        DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("Recordings"), "*.wav");

        for (Path creation : stream) {
            fileNames.add(creation.getFileName().toString());
        }
        return fileNames;
    }

    /**
     * Takes {@link #_originals} and extracts just
     * the name of the {@code Original}, ignoring any other details in
     * the file name.
     *
     * @return a list containing the names of all existing {@code Original} files.
     */
    public List<String> listNames() {
        List<String> names = new ArrayList<>();
        try {
            for (Original creation : _originals) {
                names.add(creation.getName());
            }
        } catch (InvalidNameException e) {
            // todo GUI popup saying that an Original in Recordings doesn't have correct name format
            System.err.println(e);
        }

        return names;
    }

    /**
     * Sets the values of {@link #_originals} to be correct given the current
     * contents of <dir>Recordings</dir>.
     *
     * <p> If an {@code IOException}, {@code DirectoryIteratorException} or
     * {@code InvalidNameException} is thrown, then this method informs
     * the (todo insert class here)
     * to display an error pop-up.</p>
     */
    public void updateModel() {
        try {
            List<String> fileNames = listFileNames();

            for (String fileName : fileNames) {
                _originals.add(new Original(fileName));
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e); // todo GUI popup saying an unexpected error occured
        } catch (InvalidNameException e) {
            System.err.println(e); // todo Different GUI popup
        }
    }

    public List<Original> getOriginals() {
        return _originals;
    }

    /**
     * Ensures that only one object of {@code Originals}
     * can be instantiated.
     *
     * @return the singleton {@code Originals} object.
     */
    public static Originals getInstance() {
        return _SINGLETON;
    }

    // JUST FOR TESTING PURPOSES
    public static void main(String[] args) {
    }
}
