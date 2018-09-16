package Model;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a collection of {@link Original} creations.
 * It is responsible for handling their behaviour as a group, not
 * as individual files.
 *
 * <p> This class can only be instantiated once because it denies
 * the possibility for multiple lists of Originals to be used.
 * The one list generated from scanning the contents of <dir>Recordings</dir>
 * is in essence, the only true list of Originals. </p>
 *
 * @author Eric Pedrido
 */
public class Originals {

    private List<Original> _originals = new ArrayList<>();
    private final static Originals _SINGLETON = new Originals();

    /**
     * Upon construction, all folders will be populated with each
     * Original having its own respective sub-folder. This must only
     * happen once.
     */
    private Originals() {
        updateModel();
        populateFolders();
    }

    /**
     * Reads all existing <code>Original</code> creations provided in
     * <dir>Recordings</dir> and populates <dir>Names</dir>.
     *
     * <p> Each creation of unique name gets its own sub-folders where the recording
     * and practices are stored. </p>
     *
     * <p> Should there be multiple creations of the same name, then
     * multiple recordings will be stored in the same directory,
     * but will have a number at the end indicating which
     * version it is. For example, <code>John Smith.wav, John Smith2.wav</code>
     * Note that the first version will keep its name. </p>
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

                if (Files.notExists(Paths.get("Names/" + name + "/Original/" + creation))) {
                    Files.copy(Paths.get("Recordings/" + creation),
                            Paths.get("Names/" + name + "/Original/" + creation),
                            StandardCopyOption.COPY_ATTRIBUTES);
                } else if (name.equals(names.get(i - 1))) {
                    int j = 2;
                    StringBuilder tempName = new StringBuilder(fileNames.get(i).substring(0, '.'));

                    // Append an integer n at the end for the nth duplicate of the name
                    while (Files.exists(Paths.get("Names/" + name + "/Original/" + creation))) {
                        j++;
                    }
                    tempName.append(j);
                    Files.copy(Paths.get("Recordings/" + creation),
                            Paths.get("Names/" + name + "/Original/" + tempName + ".wav"),
                            StandardCopyOption.COPY_ATTRIBUTES);
                }
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e); // todo: replace with an error message in GUI
        }
    }

    /**
     * Checks <dir>Recordings</dir> to build a <code>List<String></code>
     * containing the file name of every Original creation.
     *
     * <p> Note that a valid Original creation is any file that has
     * the file extension <code>.wav</code>. </p>
     *
     * @return a List of all existing original creations.
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
     * Takes the list of Originals and extracts just
     * the name of the creation, ignoring any other details in
     * the file name.
     *
     * @return a List containing just the names.
     */
    public List<String> listNames() {
        List<String> names = new ArrayList<>();
        for (Original creation : _originals) {
            names.add(creation.getName());
        }

        return names;
    }

    /**
     * Sets the values of {@link Originals#_originals} to be correct given the current
     * contents of <dir>Recordings</dir>.
     *
     * <p> If an {@code IOException} or {@code DirectoryIteratorException}
     * is thrown, then this method informs the View to display an error
     * pop-up. </p>
     */
    public void updateModel() {
        try {
            List<String> fileNames = listFileNames();

            for (String fileName : fileNames) {
                _originals.add(new Original(fileName));
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e); // todo GUI popup
        }
    }

    public List<Original> getOriginals() {
        return _originals;
    }

    /**
     * Ensures that only one object of <code>Originals</code>
     * can be instantiated.
     *
     * @return the singleton <code>Originals</code> object
     */
    public static Originals getInstance() {
        return _SINGLETON;
    }

    // JUST FOR TESTING PURPOSES
    public static void main(String[] args) {
    }
}
