package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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
 */
public class Original {

    //i would probably have an update method, a getFullPath(name) method etc
    //ive also added your methods below:
    //i guess keep this class quite separate to Practices (except for creating the folder of course)
    //also its good practice to check if there already exists folders in the directory
    //e.g if (folder or file DOESNT exist...add it).

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
