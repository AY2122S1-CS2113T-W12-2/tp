package seedu.command;

public class AddCommand extends Command {

    private final String moduleCode;
    //public Timetable timetable = new Timetable();

    public AddCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void execute() {
        /*
        String path = "data/Modules/" + moduleCode + ".json";
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Module module = gson.fromJson(reader, Module.class);
            reader.close();
            timetable.add(module);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        timetable.print();
        */
    }

}
