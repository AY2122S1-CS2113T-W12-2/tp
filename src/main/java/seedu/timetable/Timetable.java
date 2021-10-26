package seedu.timetable;

import seedu.exceptions.UniModsException;
import seedu.logger.TimetableLogger;
import seedu.module.Lesson;
import seedu.module.Module;
import seedu.ui.TextUi;
import seedu.ui.TimetableUI;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * The Timetable Class, which will track all added modules and lessons that you
 * have signed up for. Each cell in the timetable will display the module code,
 * lesson type and venue of the lesson Each Timetable should be assigned a
 * semester number --> Academic Year semester (1 OR 2).
 */
public class Timetable implements Comparable<Timetable> {

    private static final int DEFAULT_START = 9;
    private static final int DEFAULT_END = 16;

    private int semester;
    private int earliestHour;
    private int latestHour;

    private final ArrayList<Module> modules;
    private final TimetableLogger logger = new TimetableLogger();

    private TimetableItem[] monday = new TimetableItem[24];
    private TimetableItem[] tuesday = new TimetableItem[24];
    private TimetableItem[] wednesday = new TimetableItem[24];
    private TimetableItem[] thursday = new TimetableItem[24];
    private TimetableItem[] friday = new TimetableItem[24];
    private TimetableItem[] saturday = new TimetableItem[24];
    private TimetableItem[] sunday = new TimetableItem[24];

    /**
     * Creates a Timetable assigned to a specific semester of the Academic Year.
     *
     * @param semester Semester 1 OR 2 of the Academic Year
     */
    public Timetable(int semester) {
        this.modules = new ArrayList<>();
        this.semester = semester;
        this.earliestHour = DEFAULT_START;
        this.latestHour = DEFAULT_END;
    }

    public Timetable(int semester, int earliestHour, int latestHour, ArrayList<Module> modules, TimetableItem[] mon,
            TimetableItem[] tues, TimetableItem[] wed, TimetableItem[] thurs, TimetableItem[] fri, TimetableItem[] sat,
            TimetableItem[] sun) {
        this.semester = semester;
        this.earliestHour = earliestHour;
        this.latestHour = latestHour;
        this.modules = modules;
        this.monday = mon;
        this.tuesday = tues;
        this.wednesday = wed;
        this.thursday = thurs;
        this.friday = fri;
        this.saturday = sat;
        this.sunday = sun;
    }

    /**
     * Adds a Timetable Lesson to the timetable and adds the corresponding module to
     * an internal list if not already added. This can be a Lecture, Tutorial or
     * Laboratory
     *
     * @param module   module to be added to the timetable
     * @param semester semester of the timetable
     * @param lesson   lesson to be added to the timetable
     * @see TimetableLesson
     */
    public void addLesson(Module module, Integer semester, Lesson lesson) {
        TimetableLesson timetableLesson = new TimetableLesson(module, semester, lesson);

        switch (timetableLesson.getDayOfWeek()) {
        case MONDAY:
            addItemToSchedule(timetableLesson, monday);
            break;
        case TUESDAY:
            addItemToSchedule(timetableLesson, tuesday);
            break;
        case WEDNESDAY:
            addItemToSchedule(timetableLesson, wednesday);
            break;
        case THURSDAY:
            addItemToSchedule(timetableLesson, thursday);
            break;
        case FRIDAY:
            addItemToSchedule(timetableLesson, friday);
            break;
        case SATURDAY:
            addItemToSchedule(timetableLesson, saturday);
            break;
        case SUNDAY:
            addItemToSchedule(timetableLesson, sunday);
            break;
        default:
            break;
        }

        addModuleToList(module);

        logger.log(Level.INFO, String.format("%s added to timetable",
                timetableLesson.getTitle() + ", " + timetableLesson.getLessonType()));
    }

    /**
     * Resizes the starting hour and ending hour of the displayed timetable.
     * If the item has an earlier starting hour, the starting hour = item's starting hour
     * And same with ending hour
     *
     * @param start The starting hour of the item to be added
     * @param end The ending hour of the item to be added
     */
    private void adjustStartAndEndHours(int start, int end) {
        if (start < earliestHour) {
            earliestHour = start;
        }

        if (end > latestHour) {
            latestHour = end;
        }
        assert earliestHour < latestHour : "Earliest hour of the day is should be earlier than latest hour of the day";
    }

    /**
     * Adds a timetable item to a specific day schedule E.g.
     * addLessonToSchedule(lesson, monday) will add the lesson to the monday
     * schedule
     *
     * @param timetableItem Item to be added to a day's schedule
     * @param schedule      Day's schedule (i.e monday/tuesday/.. etc) to add the
     *                      lesson to
     */
    private void addItemToSchedule(TimetableItem timetableItem, TimetableItem[] schedule) {
        int start = timetableItem.getStartHour();
        int end = timetableItem.getEndHour();
        for (int i = start; i < end; i++) {
            schedule[i] = timetableItem;
        }
        adjustStartAndEndHours(start, end);
    }

    /**
     * Adds the lesson's module to the internal tracking list. This is to be
     * displayed later
     *
     * @param module Module to be added
     * @see Module
     */
    private void addModuleToList(Module module) {
        if (!modules.contains(module)) {
            modules.add(module);
        }
    }

    /**
     * Deletes the module from the list of modules taken by the user.
     *
     * @param module Module to be Deleted
     * @see Module
     */
    public void deleteModuleFromList(Module module) throws UniModsException {
        String moduleCode = module.getModuleCode();
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleCode().equals(moduleCode)) {
                modules.remove(modules.get(i));
                TextUi.printModuleDeleted(moduleCode);
                deleteFromLessons(module);
                logger.log(Level.INFO, String.format("%s added to timetable", module.getModuleCode()));
                return;
            }
        }
        throw new UniModsException(TextUi.ERROR_MODULE_NOT_FOUND);
    }

    /**
     * Calls the function deleteFromSchedule to delete the module from the
     * timetable's daily plan.
     *
     * @param module Module to be Deleted
     * @see Module
     */
    public void deleteFromLessons(Module module) {
        deleteFromSchedule(module, monday);
        deleteFromSchedule(module, tuesday);
        deleteFromSchedule(module, wednesday);
        deleteFromSchedule(module, thursday);
        deleteFromSchedule(module, friday);
        deleteFromSchedule(module, saturday);
        deleteFromSchedule(module, sunday);
    }

    /**
     * Sets the TimetableLesson for the particular time slot to be null where the
     * timetableLesson is scheduled for the module to be deleted.
     * 
     * @param schedule Schedule contains the lessons of the user for a particular
     *                 day of the week.
     * @param module   Module to be Deleted
     * @see Module
     */
    public void deleteFromSchedule(Module module, TimetableItem[] schedule) {
        for (int i = 0; i < schedule.length; i++) {
            if (schedule[i] != null && schedule[i].getTitle().equals(module.getModuleCode())) {
                schedule[i] = null;
            }
        }
    }

    /**
     * Removes all modules from the list of modules taken by the user. Calls the
     * clearTimetableFromLessons() function to delete all the scheduled lessons.
     */
    public void clearTimetable() throws UniModsException {
        if (modules.size() > 0) {
            modules.clear();
            clearTimetableFromLessons();
            TextUi.printTimetableCleared();
            logger.log(Level.INFO, "All modules removed from timetable");
        } else {
            throw new UniModsException(TextUi.ERROR_EMPTY_TIMETABLE);
        }
    }

    /**
     * Calls the function clearTimetableFromLessons() to clear the scheduled lessons
     * from the timetable's daily plan.
     */
    public void clearTimetableFromLessons() {
        clearSchedule(monday);
        clearSchedule(tuesday);
        clearSchedule(wednesday);
        clearSchedule(thursday);
        clearSchedule(friday);
        clearSchedule(saturday);
        clearSchedule(sunday);
    }

    /**
     * Clears the TimetableLesson for all non-null time slots .
     *
     * @param schedule Schedule contains the lessons of the user for a particular
     *                 day of the week.
     */
    public void clearSchedule(TimetableItem[] schedule) {
        assert (schedule.length > 0);
        for (int i = 0; i < schedule.length; i++) {
            if (schedule[i] != null) {
                schedule[i] = null;
            }
        }
    }

    /**
     * Displays the timetable over Command Line Interface Draws a grid where the
     * Horizontal Axis represents the hour timing and the Vertical Axis represents
     * the Day (MON/TUES/WED/... etc.) Displays the lessons in each grid cell that
     * had been added to the timetable
     *
     * <p>Also displays all the modules taken and the total number of MCs taken for the
     * timetable
     */
    public void showTimetable() {
        TimetableUI.printScheduleHours(earliestHour, latestHour);
        TimetableUI.printDaySchedule("MON", monday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("TUE", tuesday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("WED", wednesday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("THU", thursday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("FRI", friday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("SAT", saturday, earliestHour, latestHour);
        TimetableUI.printDaySchedule("SUN", sunday, earliestHour, latestHour);
        TimetableUI.printModules(modules, this.semester);
    }

    public void showUserItemsTimetable() {

        TimetableUI.printScheduleHours(earliestHour, latestHour);
        TimetableUI.printDaySchedule("MON", getUserItems(monday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("TUE", getUserItems(tuesday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("WED", getUserItems(wednesday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("THU", getUserItems(thursday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("FRI", getUserItems(friday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("SAT", getUserItems(saturday), earliestHour, latestHour);
        TimetableUI.printDaySchedule("SUN", getUserItems(sunday), earliestHour, latestHour);
        TimetableUI.printModules(modules, this.semester);
    }

    public TimetableItem[] getUserItems(TimetableItem[] schedule) {
        TimetableItem[] userItemSchedule = schedule.clone();
        for (int i = 0; i < userItemSchedule.length; i++) {
            if (userItemSchedule[i] instanceof TimetableLesson) {
                userItemSchedule[i] = null;
            }
        }
        return userItemSchedule;
    }

    public TimetableLesson getLesson(DayOfWeek day, int startHour) {
        TimetableItem lesson;
        switch (day) {
        case MONDAY:
            lesson = monday[startHour];
            break;
        case TUESDAY:
            lesson = tuesday[startHour];
            break;
        case WEDNESDAY:
            lesson = wednesday[startHour];
            break;
        case THURSDAY:
            lesson = thursday[startHour];
            break;
        case FRIDAY:
            lesson = friday[startHour];
            break;
        case SATURDAY:
            lesson = saturday[startHour];
            break;
        case SUNDAY:
            lesson = sunday[startHour];
            break;
        default:
            return null;
        }
        if (lesson instanceof TimetableLesson) {
            return (TimetableLesson) lesson;
        }
        return null;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        assert (semester > 0 && semester < 5);
        this.semester = semester;
    }

    public int getEarliestHour() {
        return earliestHour;
    }

    public int getLatesthour() {
        return latestHour;
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public TimetableItem[] getMonday() {
        return this.monday;
    }

    public TimetableItem[] getTuesday() {
        return this.tuesday;
    }

    public TimetableItem[] getWednesday() {
        return this.wednesday;
    }

    public TimetableItem[] getThursday() {
        return this.thursday;
    }

    public TimetableItem[] getFriday() {
        return this.friday;
    }

    public TimetableItem[] getSaturday() {
        return this.saturday;
    }

    public TimetableItem[] getSunday() {
        return this.sunday;
    }

    @Override
    public int compareTo(Timetable timetable) {
        int flag = 0;
        boolean isSemesterSame = this.getSemester() == timetable.getSemester();
        boolean areModulesSame = this.modules.equals(timetable.modules);
        if (isSemesterSame && areModulesSame) {
            flag = 1;
        }
        return flag;
    }

    public boolean isConflict(Lesson lesson) {
        ArrayList<Integer> duration = lesson.getStartToEndTime();
        for (Integer time : duration) {
            if (isLessonExist(lesson, time)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLessonExist(Lesson lesson, Integer time) {
        return getLesson(lesson.getParsedDay(), time) != null;
    }
}
