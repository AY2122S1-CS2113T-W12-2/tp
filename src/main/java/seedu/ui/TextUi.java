package seedu.ui;

import seedu.module.Module;

import java.util.Scanner;

public class TextUi {

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
    private static final String HELP_MESSAGE = LINE
            + "\tNUSModsLite accepts the following commands:-\n"
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

    /*------------- PUBLIC COMMANDS ----------- */
    public static String getCommand() {
        System.out.println();
        String input = in.nextLine();
        while (input.isEmpty()) {
            input = in.nextLine();
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
        System.out.print(LINE + "> Sorry friend, I don't know what that means. :/\n" + LINE);
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
        System.out.println("Generic greeting.");
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
}
