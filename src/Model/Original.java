package Model;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the individual sound recordings from <dir>Recordings</dir>.
 * A sound recording in that directory is referred to as an <code>Original</code>.
 *
 * <p> Originals cannot be altered, deleted, or created from within the program.
 * Any interaction other than playing the sound recording must be done externally
 * by altering files in <dir>Recordings</dir>. </p>
 *
 * <p> An Original is identified by its file name exactly as it is written.
 * However, the user friendly name can be retrieved from {@link Original#getName()}
 * and is stored in {@link Original#_name}. </p>
 *
 * <p> Behaviour of all the <code>Original</code> in a group is different.
 * This behaviour is inplemented in {@link Originals}</p>
 *
 * @author Eric Pedrido
 */
public class Original {

    private String _name;
    private String _fileName;

    /**
     * The Originals are provided by <dir>Recordings</dir> and so
     * this constructor does not construct an Original creation.
     * Instead, it creates a reference to the corresponding file.
     *
     * @param fileName Name of the Original to reference.
     * @throws FileNotFoundException If the requested file doesn't exist.
     */
    public Original(String fileName) throws FileNotFoundException {
        // Find the file with the entered filename
        if (Files.exists(Paths.get("Recordings/" + fileName))) {
            _fileName = fileName;
            _name = getName();
        } else {
            throw new FileNotFoundException();
        }
    }

    /**
     * Plays the audio from the sound file that this <code>Original</code>
     * object is referencing.
     * <p>
     * todo implement this method.
     */
    public void play() {

    }

    /**
     * Extracts a name that is user-friendly from the filename
     * of an Original.
     *
     * <p> The naming convention for an Original creation is
     * <code>(user code)_(date)_(xx-yy-zz)_(name).wav</code> where
     * x, y and z are numbers. This method extracts just the name
     * without any file extensions. </p>
     *
     * @return just the name with no file extensions.
     */
    public String getName() {
        Pattern pattern = Pattern.compile("[ a-zA-Z]+.wav"); //todo REGEX FOR SOME REASON NOT WORKING
        Matcher matcher = pattern.matcher(_fileName);

        return matcher.group(0).substring(0, '.');
    }

    public String getFileName() {
        return _fileName;
    }
}
