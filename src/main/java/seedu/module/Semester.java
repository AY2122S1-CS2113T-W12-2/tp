package seedu.module;

import java.util.ArrayList;
import java.util.Date;

public class Semester {
    private int semester;
    private Date examDate;
    private int examDuration;
    private ArrayList<Lesson> timetable;

    public ArrayList<Lesson> getTimetable() {
        return timetable;
    }

    public Lesson getLesson(int idx) {
        return timetable.get(idx);
    }

    public int getSemester() {
        return semester;
    }

    public Date getExamDate() {
        return examDate;
    }

    public int getExamDuration() {
        return examDuration;
    }

    @Override
    public String toString() {
        return String.valueOf(semester);
    }
}
