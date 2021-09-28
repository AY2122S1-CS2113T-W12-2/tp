package seedu.duke.module;

import java.util.Date;

public class Module {
    // --- Available in https://api.nusmods.com/v2/2020-2021/modules/<MODULE_CODE>.json --- //
    private String moduleCode;
    private String title;
    private String description;
    private Double moduleCredit;
    private String department;
    private String faculty;
    private String prerequisite; //Disallow add when prerequisite not met?
    private String preclusion; //Account for preclusion when adding modules?
    private String corequisite; //Remind users to add corequisite modules?
    public class semesterData {
        private int semester;
        private Date examDate;
        private int examDuration;
        public class timetable {
            private String classNo;
            private int startTime;
            private int endTime;
            private String venue;
            private String day;
            private String lessonType;
            private int size;
        }
    }

    public void add(String attribute, String input) {
        switch(attribute) {
            case "moduleCode": {
                moduleCode = input;
                break;
            }
            case "title": {
                title = input;
                break;
            }
            case "description": {
                description = input;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return moduleCode + " \n" + description;
    }
}
