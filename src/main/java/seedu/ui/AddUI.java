package seedu.ui;

import seedu.module.Lesson;
import seedu.module.Module;
import seedu.timetable.Timetable;
import seedu.timetable.TimetableLesson;
import seedu.exceptions.IntegerException;
import java.util.ArrayList;

public class AddUI {
    private static final int BALANCE_ARRAY = 1;
    private static final int SERIAL_STARTING = 1;
    private static final int DIVIDER = 42;
    private static final int GAP = 46;
    private static final String SPACE = " ";
    private static final String DIV = "|";
    private static final String LAB = "lab";
    private static final String LINE = "_______________________________________   |   ";

    public void printLessonDetails(ArrayList<Lesson> lec, ArrayList<Lesson> tt,
            ArrayList<Lesson> lab, Timetable timetable, Module module) throws IntegerException {
        ArrayList<String> lectureLessons;
        ArrayList<String> tutorialLessons;
        ArrayList<String> labLessons;
        int length = Math.max(lec.size(), Math.max(tt.size(), lab.size()));
        lectureLessons = getLessonDetails(lec, length);
        tutorialLessons = getLessonDetails(tt, length);
        labLessons = getLessonDetails(lab, length);
        TextUi.printLessonHeader(lec, tt, lab);
        printLessons(lectureLessons, tutorialLessons, labLessons);
        try {
            getCommand(lec, timetable, module);
            getCommand(tt, timetable, module);
            getCommand(lab, timetable, module);
        } catch (IntegerException e) {
            throw new IntegerException("Invalid Integer");
        }
    }

    public ArrayList<String> getLessonDetails(ArrayList<Lesson> lessonType, int length) {
        ArrayList<String> completeList = new ArrayList<>();
        int serial = SERIAL_STARTING;
        for (int i = 0; length > i; i++) {
            if (isArrayExist(lessonType, i)) {
                Lesson lesson = lessonType.get(i);
                String detail = printLessonInfo(serial, lesson);
                completeList.add(detail);
                if (classNumberGap(lessonType, lesson)) {
                    completeList.add(LINE);
                    serial++;
                }
            }
        }
        return completeList;
    }

    public void printLessons(ArrayList<String> lec, ArrayList<String> tt, ArrayList<String> lab) {
        int index = Math.max(lec.size(), Math.max(tt.size(), lab.size()));
        for (int j = 0; index > j; j++) {
            String output = "";
            if (isExist(lec, j)) {
                output = output.concat(lec.get(j));
            } else if (isExist(tt, j) && isExist(lec, j)) {
                for (int i = 0; GAP > i; i++) {
                    output = output.concat(SPACE);
                }
            }
            if (isExist(tt, j)) {
                output = output.concat(tt.get(j));
            } else if (isExist(lab, j) && isExist(tt, j)) {
                for (int i = 0; GAP > i; i++) {
                    output = output.concat(SPACE);
                }
            }
            if (isExist(lab, j)) {
                output = output.concat(lab.get(j));
            }
            System.out.println(output);
        }
    }

    public void getCommand(ArrayList<Lesson> lessons,
            Timetable timetable, Module module) throws IntegerException {
        if (isArrayExist(lessons, 0)) {
            String select = TextUi.getLessonCommand(lessons.get(0).getLessonType());
            int indexOfLesson;
            try {
                indexOfLesson = Integer.parseInt(select) - BALANCE_ARRAY;
            } catch (NumberFormatException e) {
                throw new IntegerException("Input is not an integer");
            }
            String classNumber = lessons.get(indexOfLesson).getClassNo();
            for (Lesson lesson : lessons) {
                if (lesson.getClassNo().equals(classNumber)) {
                    timetable.addLesson(new TimetableLesson(module, timetable.getSemester(), lesson));
                }
            }
        }
        TextUi.printLessonAdded();
    }

    public String printLessonInfo(int serial, Lesson lesson) {
        String output = serial + ": " + lesson.lessonDetails();
        if (!lesson.getLessonType().equals(LAB)) {
            for (int index = output.length(); GAP > index; index++) {
                if (index == DIVIDER) {
                    output = output.concat(DIV);
                } else {
                    output = output.concat(SPACE);
                }
            }
        }

        return output;
    }

    public boolean classNumberGap(ArrayList<Lesson> lessonList, Lesson lesson) {
        int index = lessonList.indexOf(lesson);
        if (lessonList.size() != index + BALANCE_ARRAY) {
            String classNumber = lessonList.get(index).getClassNo();

            int nextIndex = index + BALANCE_ARRAY;
            String nextClassNumber = lessonList.get(nextIndex).getClassNo();

            return !classNumber.equals(nextClassNumber);
        }
        return true;
    }

    public boolean isExist(ArrayList<String> lesson, int index) {
        return lesson.size() > 0 && lesson.size() > index;
    }

    public boolean isArrayExist(ArrayList<Lesson> lesson, int index) {
        return lesson.size() > 0 && lesson.size() > index;
    }
}
