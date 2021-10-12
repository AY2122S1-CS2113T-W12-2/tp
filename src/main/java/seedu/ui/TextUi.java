package seedu.ui;


import seedu.comparator.ClassNumComparator;
import seedu.module.Lesson;
import seedu.module.Module;
import seedu.module.Semester;
import seedu.timetable.Timetable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextUi {
    private static final int BALANCE_ARRAY = 1;
    private static final int SERIAL = 1;
    private static final String SMALL_GAP = "         ";
    private static final String BIG_GAP = "                         ";
    private static final String LECTURE_SLOT = "Lecture Lesson Slots";
    private static final String TUTORIAL_SLOT = "Tutorial Lesson Slots";
    private static final String LAB_SLOT = "Laboratory Lesson Slots";

    public static Scanner in = new Scanner(System.in);

    public static final String LINE = "____________________________________________________________________________\n";

    /*------------- PRIVATE LOGGING CONSTANTS ----------- */
    private static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    private static final String STARTUP = "Hello from \n " + LOGO;
    private static final String GREETING = "How can I help you today?";
    private static final String USER_PROMPT = "What would you like to do?";
    private static final String PROMPT_CURSOR = "==>";
    private static final String HELP_MESSAGE = LINE
            + "\tUNIMods accepts the following commands:-\n"
            + "\t\t| No.| Command Syntax          |            Command Action                      |\n"
            + "\t\t| 1. | search <module_code>    | Search module based on the given partial regex |\n"
            + "\t\t| 2. | show <module_code>      | Display module information                     |\n"
            + "\t\t| 3. | add <module_code>       | Add module to the Timetable                    |\n"
            + "\t\t| 4. | delete <module_code>    | Remove module from the Timetable               |\n"
            + "\t\t| 5. | clear                   | Remove all modules from the Timetable          |\n"
            + "\t\t| 6. | timetable               | Display the Timetable                          |\n"
            + "\t\t| 7. | exit                    | Exit From Program                              |\n"
            + "\t ** Note: For details, refer to the User Guide of NUSModsLite at: "
            + "\n\t\thttps://ay2122s1-cs2113t-w12-2.github.io/tp/UserGuide.html\n" + LINE;

    public static final String ERROR_MODULE_NOT_FOUND = "OOPS, this module does not exist in your timetable!";
    public static final String ERROR_INVALID_MODULE_CODE =
            "OOPS, it looks like the module code you entered doesn't exist, Please re-check!";
    public static final String ERROR_EMPTY_TIMETABLE = "OOPS, it seems that your timetable is already empty.";


    /*------------- PUBLIC COMMANDS ----------- */
    public static String getCommand() {
        System.out.println(LINE);
        System.out.println(USER_PROMPT);
        System.out.print(PROMPT_CURSOR);
        String input = in.nextLine();
        while (input.isEmpty()) {
            System.out.print(PROMPT_CURSOR);
            input = in.nextLine();
        }
        return input;
    }

    public static String getLessonCommand(String lessonType) {
        String output = "Which " + lessonType + " would you like to choose? ";
        System.out.print(output);
        String input = in.nextLine();
        while (input.isEmpty()) {
            input = in.next();
        }
        return input;
    }

    public static void printExitMessage() {
        System.out.print(LINE + "> Bye friend!\n> See you again! :)\n" + LINE);
    }

    public static void printHelpMessage() {
        System.out.print(HELP_MESSAGE);
    }

    public static void printInvalidCommandMessage() {
        System.out.print(LINE + "> Sorry friend, I don't know what that means. :/\n");
    }

    public static void printModBriefDescription(Module module) {
        System.out.println(module);
    }

    public static void printModFullDescription(Module module) {
        System.out.println(module.getFullInfo());
    }

    public static boolean printMatchingMod(Module module, String searchTerm) {
        if (codeContains(module, searchTerm)) {
            System.out.println(module);
            return true;
        }
        /*
        // title match not used for now
        if (codeMatch(module, searchTerm) || titleMatch(module, searchTerm)) {
            System.out.println(module);
        }
        */
        return false;
    }

    private static boolean codeContains(Module module, String searchTerm) {
        return module.getModuleCode().toLowerCase().contains(searchTerm.toLowerCase());
    }

    public static boolean codeMatch(Module module, String searchTerm) {
        return module.getModuleCode().equalsIgnoreCase(searchTerm.toLowerCase());
    }

    private static boolean titleMatch(Module module, String searchTerm) {
        return module.getTitle().toLowerCase().contains(searchTerm.toLowerCase());
    }

    public static void printErrorMessage() {
        System.out.println("Error occurred.");
    }

    public static void printUpdateStartMessage() {
        System.out.println("Updating, standby...");
    }

    public static void printUpdateSuccessMessage() {
        System.out.println("Local data updated successfully.");
    }

    public static void printWelcomeMessage() {
        System.out.println(STARTUP);
    }

    public static void printLoadStartMessage() {
        System.out.println("Loading modules from save...");
    }

    public static void printLoadError() {
        System.out.println("Save data failed to load. Fetch data from NUSMods with \"update\".");
    }

    public static void printLoadSuccessMessage(int count) {
        System.out.println(count + " mods loaded from save.");
        if (count < 10000) {
            System.out.println("Save data seems to be incomplete or missing. Please run \"update\".");
        }
    }

    public static void printNotFoundMessage() {
        System.out.println("No matching mod found.");
    }

    private static void printFullInfo(Module module) {
        System.out.println(module.getFullInfo());
    }

    public static void printNoConnectionMessage() {
        System.out.println(LINE + "Failed to connect to NUSMods API. Loading saved information.");
        System.out.println(LINE);
    }

    public static void printModsFound(int count) {
        String mods = (count == 1) ? " mod " : " mods ";
        System.out.println(count + mods + "found.");
    }

    public static void printLocalSearchMessage() {
        System.out.println(LINE + "!WARNING! This is a local search, data might not be up to date.");
        System.out.println(LINE);
    }

    public static void printAddMessage(String moduleCode) {
        System.out.println("Now adding " + moduleCode + " into timetable");
    }

    public static void printLessonMessage(String lessonType) {
        switch (lessonType) {
        case "Lecture":
            //System.out.println("Which Lecture time slot would you like to add ?");
            System.out.println("\nLecture Time slots:");
            break;
        case "Tutorial":
            //System.out.println("Which Tutorial time slot would you like to add ?");
            System.out.println("\nTutorial Time slots:");
            break;
        default:
            //System.out.println("Which Laboratory time slot would you like to add ?");
            System.out.println("\nLaboratory Time slots:");
            break;
        }
    }

    public static void printLessonAdded() {
        System.out.println("Lessons for all modules have been successfully added");
    }

    public static void printModuleDeleted(String moduleCode) {
        System.out.println(moduleCode + " is successfully deleted from your Timetable.");
    }

    public static void printTimetableCleared() {
        System.out.println("All modules have been successfully removed from your Timetable.");
    }

    public static String returnLine() {
        return "________________________________________";
    }

    public static void printLessonHeader(ArrayList<Lesson> lt, ArrayList<Lesson> tt, ArrayList<Lesson> lb) {
        String header = "";
        if (isExist(lt) && isExist(tt) && isExist(lb)) {
            header = SMALL_GAP + LECTURE_SLOT + BIG_GAP + TUTORIAL_SLOT + BIG_GAP + LAB_SLOT;
        } else if (isExist(lt) && isExist(tt)) {
            header = SMALL_GAP + LECTURE_SLOT + BIG_GAP + TUTORIAL_SLOT;
        } else if (isExist(lt) && isExist(lb)) {
            header = SMALL_GAP + LECTURE_SLOT + BIG_GAP + LAB_SLOT;
        } else if (isExist(tt) && isExist(lb)) {
            header = SMALL_GAP + TUTORIAL_SLOT + BIG_GAP + LAB_SLOT;
        } else if (isExist(lt)) {
            header = SMALL_GAP + LECTURE_SLOT;
        } else if (isExist(tt)) {
            header = SMALL_GAP + TUTORIAL_SLOT;
        } else if (isExist(lb)) {
            header = SMALL_GAP + LAB_SLOT;
        } else {
            header = "No Lesson Time Slots Found";
        }
        System.out.println(header);
    }

    public static boolean isExist(ArrayList<Lesson> lesson) {
        return lesson.size() > 0;
    }
}
