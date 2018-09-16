package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles the original sound recordings from the Recordings folder.
 *
 * On initialisation it creates folders of all the names from the Recordings folder. (probably in constructor)
 * It then creates subfolders Original and Practices.
 * It then adds the correct audio files into their respective subfolders. e.g Mason.wav goes in Name/Mason/Original
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
     * version it is.
     * Note that the first version will keep its name.
     */
    private static void populateFolders() {
        List<String> creations = new ArrayList<>();

        // List all valid creations inside /Recordings
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("/Recordings"), "se206_*.wav")) {
            for (Path creation : stream) {
                creations.add(creation.getFileName().toString());
            }

            // Get only the name from the file names
            List<String> names = new ArrayList<>();
            for (String fileName : creations) {
                Pattern pattern = Pattern.compile("_[a-zA-Z ]*.wav");
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.find()) {
                    names.add(matcher.group(1).substring(0, '.'));
                }
            }

            // Make a folder for each creation containing sub-folders
            for (String name : names) {
                if (Files.notExists(Paths.get("Names/" + name))) {
                    Files.createDirectory(Paths.get("Names/" + name + "/Original"));
                    Files.createDirectory(Paths.get("Names/" + name + "/Practices"));
                }
            }

            // Put the respective creations in their folders
            for (int i = 0; i < creations.size(); i++) {
                String creation = creations.get(i);
                String name = names.get(i);

                if (Files.notExists(Paths.get("Names/" + name + "/Original/" + creation))) {
                    Files.copy(Paths.get("Recordings/" + creation),
                            Paths.get("Names/" + name + "/Original/" + creation),
                            StandardCopyOption.COPY_ATTRIBUTES);
                } else {
                    int j = 2;
                    StringBuilder tempName = new StringBuilder(creations.get(i).substring(0, '.'));

                    while (Files.exists(Paths.get("Names/" + name + "/Original/" + creation))) {
                        if (j > 2) {
                            // Remove the appended number if there already was one
                            tempName.setLength(tempName.length()-1);
                        }
                        tempName.append(j);
                        j++;
                    }
                    Files.copy(Paths.get("Recordings/" + creation),
                            Paths.get("Names/" + name + "/Original/" + tempName + ".wav"),
                            StandardCopyOption.COPY_ATTRIBUTES);
                }
            }

        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
    }


    /**
     * ADD YOUR STUFF IN HERE (YOU GET RID OF MY CODE LOL)
     *
     * @return
     */
    public void updateModel(String name) {
//        List<String> names = new ArrayList<>();
//
//        File file = new File("Names/" + name + "/Practice");
//
//        File[] fileList = file.listFiles();
//
//        for (File f : fileList) {
//            names.add(f.getName()); //adds the file names from directory into the list
//        }
//
//        for (String temp : names) {
//            temp = temp.substring(0, name.lastIndexOf('.'));
//            Practice practice = new Practice(temp);
//            _practices.add(practice);
//        }

        Path path = Paths.get("Names/" + name + "/Practice");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {

            for (Path file: stream) {
                String fileName = file.getFileName().toString();
               // _practices.add(new Practice(fileName.substring(0, fileName.lastIndexOf('.'))));
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println(e);
        }
    }


}
