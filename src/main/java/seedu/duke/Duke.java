package seedu.duke;

import seedu.command.Command;
import seedu.module.Module;
import seedu.online.NusMods;
import seedu.parser.CommandParser;
import seedu.storage.ModStorage;
import seedu.timetable.Timetable;
import seedu.timetable.TimetableLesson;
import seedu.ui.TextUi;

import java.io.IOException;


public class Duke {
    private static String path = "data/Modules.json";
    public static Timetable timetable = new Timetable(1);
    public static CommandParser commandParser = new CommandParser();
    public ModStorage modStorage;

    public static void main(String[] args) {
        new Duke().setup();
    }

    private void setup() {
        this.modStorage = new ModStorage(path);
        TextUi.printWelcomeMessage();
        setupTT();
        run();
    }

    // EXAMPLES FOR HOW TO ADD A CLASS INTO TIMETABLE FOR @POOPIES99
    private void setupTT() {
        try {
            Module module = fetchMod("CG2271");
            timetable.addLesson(new TimetableLesson(module, 1, module.getLesson(1, 1)));
            timetable.addLesson(new TimetableLesson(module, 1, module.getLesson(1, 5)));
            Module u = fetchMod("EG2401A");
            timetable.addLesson(new TimetableLesson(u, 1, module.getLesson(1, 3)));
        } catch (IOException e) {
            TextUi.printErrorMessage();
        }
    }

    //This is a placeholder fetchMod function
    public Module fetchMod(String moduleCode) throws IOException {
        try {
            return NusMods.fetchModOnline(moduleCode);
        } catch (IOException e) {
            TextUi.printNoConnectionMessage();
            return ModStorage.loadModInfo(moduleCode);
        }
    }

    private void run() {
        Command command;
        do {
            command = commandParser.parseCommand(TextUi.getCommand());
            executeCommand(command);
        } while (!command.isExit());
    }

    private void executeCommand(Command command) {
        command.execute();
    }
}
