package seedu.command;

import seedu.comparator.ClassNumComparator;
import seedu.exceptions.IntegerException;
import seedu.module.Lesson;
import seedu.module.Module;
import seedu.module.Semester;
import seedu.online.NusMods;
import seedu.timetable.Timetable;
import seedu.ui.AddUI;
import seedu.ui.TextUi;
import seedu.exceptions.AddException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//Indicate time clashes with current timetable in milestone v2.0 when adding lesson
public class AddCommand extends Command {
    private static final int SERIAL_STARTING = 1;
    private static final String LECTURE = "Lecture";
    private static final String TUTORIAL = "Tutorial";
    private static final String LAB = "Laboratory";
    private static final String SPACE = " ";
    private static final String DIV = "|";
    private final String moduleCode;
    private final int semester;
    private final Timetable timetable;
    public AddUI addUI = new AddUI();

    public AddCommand(String moduleCode, Timetable timetable) {
        this.moduleCode = moduleCode;
        this.semester = timetable.getSemester();
        this.timetable = timetable;
    }

    public void execute() throws AddException, IntegerException {

        Module module;
        try {
            module = NusMods.fetchModOnline(moduleCode);
        } catch (IOException e) {
            throw new AddException("Module Code does not exist");
        }
        TextUi.printAddMessage(moduleCode);

        Semester semesterData = module.getSemester(semester);
        ArrayList<Lesson> lecture;
        ArrayList<Lesson> tutorial;
        ArrayList<Lesson> laboratory;
        lecture = getLessonDetails(semesterData.getTimetable(), LECTURE);
        tutorial = getLessonDetails(semesterData.getTimetable(), TUTORIAL);
        laboratory = getLessonDetails(semesterData.getTimetable(), LAB);

        try {
            addUI.printLessonDetails(lecture, tutorial, laboratory, timetable, module);
        } catch (IntegerException e) {
            throw new IntegerException("Invalid Integer");
        }
    }

    public ArrayList<Lesson> getLessonDetails(ArrayList<Lesson> lessons, String lessonType) {
        ArrayList<Lesson> completeList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getLessonType().equals(lessonType)) {
                completeList.add(lesson);
            }
        }
        Collections.sort(completeList,new ClassNumComparator());
        return completeList;
    }
}
