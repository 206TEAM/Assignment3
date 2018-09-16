package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class represents a list of practices for each Name
 * _practices is a hashmap that contains the Name as the key value, and practice list for each name as the value.
 *
 * @author Lucy Chen
 */
public class Practices {

    protected HashMap<String, ArrayList<Practice>> _practices;

    /**
     * This method adds a practice recording into the practice list
     * @param nameKey
     * @param practice
     */
    public void addPractice(String nameKey, Practice practice) {
        ArrayList<Practice> practiceList = _practices.get(nameKey);

        // if list does not exist create it
        if(practiceList == null) {
            practiceList = new ArrayList<Practice>();
            practiceList.add(practice);
            _practices.put(nameKey, practiceList);
        } else {
            // add if item is not already in list
            if(!practiceList.contains(practice)) {
                practiceList.add(practice);
            }
        }
    }

    /**
     * Populates the _practices with existing recording files in each folder.
     * iterates through the names list from Original.java
     * goes into each folder and adds to the list
     */
    public void updateModel() {
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
    }

    /**
     * returns a list of the fileNames of the practices given the nameKey
     */
    public List<String> listPractices(String nameKey) {
        ArrayList<Practice> practiceList = _practices.get(nameKey);
        List<String> practiceNames = new ArrayList<String>();

        for (Practice practice : practiceList) {
            practiceNames.add(practice.getFileName());
        }
        return practiceNames;
    }

    /**
     * returns the file of the practice, based on the fileName and nameKey
     *
     * @param fileName
     * @return
     */
    public File getFile(String nameKey, String fileName) {
        Practice practice = getPractice(nameKey, fileName);
        return practice.filePath();
    }

    /**
     * deletes the practice from directory AND the practices list
     * @param nameKey
     * @param fileName
     */
    public void deletePractice(String nameKey, String fileName) {
        Practice practiceDelete = getPractice(nameKey, fileName);
        practiceDelete.delete(); // delete the practice

        _practices.get(nameKey).remove(practiceDelete); //not sure if this works yet (needs testing)
    }


    /**
     * gets the practice from the filename of practice and the nameKey
     *
     * @param fileName
     * @return
     */
    protected Practice getPractice(String nameKey, String fileName) {
        ArrayList<Practice> practiceList = _practices.get(nameKey);

        int index = 0;
        for (int i = 0; i < practiceList.size(); i++) {
            if (practiceList.get(i).getFileName().equals(fileName)) {
                index = i;
                break;
            }
        }
        return practiceList.get(index);
    }

    /**
     * this method generates a new recording based on the nameKey
     * it then adds it to the _practices list
     * @param nameKey
     * @return
     */
    public void addNewPractice(String nameKey){
        // Create a new practice of the given name
        Practice practice = new Practice(nameKey);
        addPractice(nameKey, practice);
    }

    public static void main(String[] args) {
    }

}




