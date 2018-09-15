package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a list of practices for each Name
 *
 */
public class Practices {

    private String _name;
    protected LinkedList<Practice> _practices;

    /**
     * constructor that creates the linkedlist for Practices
     */
    public Practices(String name) {
        _name = name;
        updateModel(name);
    }

    /**
     * goes through each file and sees
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
    }

    /**
     * returns a list of the NAMES of the practices
     */
    public List<String> listPractices() {
        List<String> practiceList = new ArrayList<String>();
        for (Practice practice : _practices) {
            practiceList.add(practice.getFileName());
        }
        return practiceList;
    }

    /**
     * adds the practice to the model
     *
     * @param practice
     */
    public void addPractice(Practice practice) {
        _practices.add(practice);
    }

    /**
     * returns the file of the practice name
     *
     * @param name
     * @return
     */
    public File getFile(String name) {
        Practice practice = getPractice(name);
        return practice.mp4File();
    }

    /**
     * adds a practice via the name of the practice
     *
     * @param name
     */
    public void addPracticeString(String name) {
        // Create a new practice with the given title.
        Practice practice = new Practice(name);
        addPractice(practice);
    }

    /**
     * deletes the practice from directory AND the list
     *
     * @param name
     */
    public void deletePractice(String name) {
        Practice practiceDelete = getPractice(name);
        practiceDelete.delete(); // delete the practice
        _practices.remove(practiceDelete);
    }


    /**
     * returns the practice from the name of the practice
     *
     * @param name
     * @return
     */
    protected Practice getPractice(String name) {
        int index = 0;
        for (int i = 0; i < _practices.size(); i++) {
            if (_practices.get(i).getFileName().equals(name)) {
                index = i;
                break;
            }
        }
        return _practices.get(index);
    }

    /**
     * sees if the practice model contains the practice or not
     *
     * @param name
     * @return
     */
    public boolean containsPractice(String name) {
        boolean found = false;
        for (Practice practice : _practices) {
            if (name.equals(practice.getFileName())) {
                found = true;
            }
        }
        return found;
    }

    public static void main(String[] args) {
        Practices MasonName = new Practices("mason");
        MasonName.addPracticeString("mason");
    }


}




