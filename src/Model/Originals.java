package Model;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a collection of {@link Original} creations.
 * It is responsible for handling their behaviour as a group, not
 * as individual files.
 *
 * @author Eric Pedrido
 */
public class Originals {

    private List<String> _fileNames = new ArrayList<>();
    private List<String> _names = new ArrayList<>();

    /**
     * Reads all existing <code>Original</code> creations provided in
     * <dir>Recordings</dir> and populates <dir>Names</dir>.
     *
     * <p> Each creation of unique name gets its own sub-folders where the recording
     * and practices are stored.
     *
     * <p> Should there be multiple creations of the same name, then
     * multiple recordings will be stored in the same directory,
     * but will have a number at the end indicating which
     * version it is. For example, <code>John Smith.wav, John Smith2.wav</code>
     * Note that the first version will keep its name.
     */
    private void populateFolders() {
        try {
            updateModel();

            // Make a folder for each creation containing sub-folders
            for (String name : _names) {
                if (Files.notExists(Paths.get("Names/" + name))) {
                    Files.createDirectory(Paths.get("Names/" + name + "/Original"));
                    Files.createDirectory(Paths.get("Names/" + name + "/Practices"));
                }
            }

            // Put the respective creations in their folders
            for (int i = 0; i < _fileNames.size(); i++) {
                String creation = _fileNames.get(i);
                String name = _names.get(i);

                if (Files.notExists(Paths.get("Names/" + name + "/Original/" + creation))) {
                    Files.copy(Paths.get("Recordings/" + creation),
                            Paths.get("Names/" + name + "/Original/" + creation),
                            StandardCopyOption.COPY_ATTRIBUTES);
                } else if (name.equals(_names.get(i-1))) {
                    int j = 2;
                    StringBuilder tempName = new StringBuilder(_fileNames.get(i).substring(0, '.'));

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
     * the file extension <code>.wav</code>.
     *
     * @return a List of all existing original creations.
     *
     * @throws IOException
     *         if an I/O error occurs.
     * @throws DirectoryIteratorException
     *         if an error occurs while iterating through
     *         the directory.
     */
    public List<String> listOriginals() throws IOException, DirectoryIteratorException {
        List<String> creations = new ArrayList<>();
        DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("/Recordings"), "*.wav");

        for (Path creation : stream) {
            creations.add(creation.getFileName().toString());
        }
        return creations;
    }

    /**
     * Takes the list of Original creation names and extracts just
     * the name of the creation, ignoring any other details in
     * the file name.
     *
     * <p> The naming convention for an Original creation is
     * <code(course or upi)_(date)_(xx-yy-zz)_(name).wav</code> where
     * x, y and z are numbers. This method extracts just the name.
     *
     * @param fileNames
     *        a List of file names to extract names from.
     *
     * @return a List containing just the names.
     */
    public List<String> listNames(List<String> fileNames) {
        List<String> names = new ArrayList<>();
        for (String fileName : fileNames) {
            Pattern pattern = Pattern.compile("_[a-zA-Z ]*.wav");
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                names.add(matcher.group(1).substring(0, '.'));
            }
        }
        return names;
    }

    /**
     * Sets the values of {@link Originals#_fileNames} and
     * {@link Originals#_names} to be correct given the current
     * contents of <dir>Recordings</dir>.
     *
     * <p> If an {@code IOException} or {@code DirectoryIteratorException}
     * is thrown, then this method informs the View to display an error
     * pop-up.
     */
    public void updateModel() {
        try {
            _fileNames = listOriginals();
            _names = listNames(_fileNames);
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e); // todo
        }
    }
}
