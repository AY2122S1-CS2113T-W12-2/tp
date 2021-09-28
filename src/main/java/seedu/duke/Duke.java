package seedu.duke;

import seedu.duke.JSONParser.ReadJSONFile;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("Output: ");
        ReadJSONFile readJSONFile = new ReadJSONFile();
        readJSONFile.read();
    }

    public static void startMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }
}
