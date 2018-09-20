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
 * _practiceNames is a list of the names that the user is currently practicing (originally selected)
 * _currentName is the name that the user is currently working on
 *
 * @author Lucy Chen
 */
public class Practices {

    protected HashMap<String, ArrayList<Practice>> _practices;
    protected List<String> _practiceNames;
    protected String _currentName;
    protected final static Practices instance = new Practices();
    protected String _currentfileName;

    //private Practices(){
    //   List<String> names = Originals.getInstance().listNames();
    //   for (String name : names){
    //     _practices.put(name,null);
    //}

    //}

    /**
     * @return the hashmap of practices
     */
    public HashMap<String, ArrayList<Practice>> getPracticesMap() {
        return _practices;
    }

    /**
     * gets the current name that is being worked on or selected
     *
     * @return
     */
    public String getCurrentName() {
        return _currentName;
    }

    /**
     * sets the current name being worked on
     *
     * @param name
     */
    public void setFileName(String name) {
        _currentfileName = name;
    }

    /**
     * gets the current name that is being worked on or selected
     *
     * @return
     */
    public String getFileName() {
        return _currentfileName;
    }

    /**
     * sets the current name being worked on
     *
     * @param name
     */
    public void setCurrentName(String name) {
        _currentName = name;
    }

    /**
     * returns the Practice instance
     *
     * @return
     */
    public static Practices getInstance() {
        return instance;
    }

    /**
     * creates a currently practicing names list
     *
     * @param names
     */
    public void addNames(List<String> names) {
        _practiceNames = names;

    }

    /**
     * returns practice names list
     *
     * @return
     */
    public List<String> getPracticeNames() {
        return _practiceNames;
    }

    /**
     * This method adds a practice recording into the practice list
     *
     * @param nameKey
     * @param practice returns filename
     */
    public String addPractice(String nameKey, Practice practice) {
        ArrayList<Practice> practiceList = new ArrayList<Practice>();

        if (_practices.containsKey(nameKey)) {
            practiceList = _practices.get(nameKey); //todo add thing already.

            // if list does not exist create it
            if (practiceList == null) {
                practiceList = new ArrayList<Practice>();
                practiceList.add(practice);
                _practices.put(nameKey, practiceList);
            } else {
                // add if item is not already in list
                if (!practiceList.contains(practice)) {
                    practiceList.add(practice);
                }
            }
        } else {
            practiceList = new ArrayList<Practice>();
            practiceList.add(practice);
            _practices.put(nameKey, practiceList);
        }

        return practice.getFileName();

        }

        /**
         * Populates the _practices with existing recording files in each folder.
         * iterates through the names list from Original.java
         * goes into each folder and adds to the list
         */
        public void updateModel () {
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
        public List<String> listPractices (String nameKey){
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
        public File getFile (String nameKey, String fileName){
            Practice practice = getPractice(nameKey, fileName);
            return practice.filePath();
        }

        /**
         * deletes the practice from directory AND the practices list
         * @param nameKey
         * @param fileName
         */
        public void deletePractice (String nameKey, String fileName){
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
        protected Practice getPractice (String nameKey, String fileName){
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
         * @return practice
         */
        public String addNewPractice (String nameKey){
            // Create a new practice of the given name
            Practice practice = new Practice(nameKey);
            practice.create();
            addPractice(nameKey, practice);
            return practice.getFileName();
        }


        public static void main (String[]args){
        }

    }




