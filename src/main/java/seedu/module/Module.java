package seedu.module;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.text.WordUtils.wrap;

public class Module {
    private final int FINALISE_INDEX = 1;

    private String moduleCode;
    private String title;
    private String description;
    private double moduleCredit;
    private String department;
    private String faculty;
    private String prerequisite;
    private String preclusion;
    private String corequisite;
    private String acadYear;
    private Attributes attributes;
    private ArrayList<Semester> semesterData;

    public Module(String name) {
        this.moduleCode = name;
    }

    public double getModuleCredit() {
        return moduleCredit;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public Lesson getLesson(int sem, int idx) {
        for (Semester s : semesterData) {
            if (s.getSemester() == sem) {
                return s.getLesson(idx);
            }
        }
        return null;
    }

    public List<Integer> getSemesters() {
        return null;
    }

    public Semester getSemester(int sem) {
        for (Semester s : semesterData) {
            if (s.getSemester() == sem) {
                return s;
            }
        }
        //What is the point of this? Shouldn't we just print out an error
        return new Semester();
    }

    @Override
    public String toString() {
        return moduleCode + " " + title + " " + (int)moduleCredit + "MC";
    }

    public String getFullInfo() {
        String isSUable = "No data";
        if (attributes != null) {
            isSUable = attributes.isSUable();
        }
        String fullInfo = "Title: " + title + "\n"
                + "MCs: " + (int)moduleCredit + "\n"
                + "Department: " + department + "\n"
                + wrap(description, 70) + "\n"
                + "Prerequisites: " + prerequisite + "\n"
                + "S/U able: " + isSUable + "\n"
                + "Semester Availability: " + semesterData + "\n";
        return fullInfo;
    }

    public boolean codeContains(String searchTerm) {
        return moduleCode.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
