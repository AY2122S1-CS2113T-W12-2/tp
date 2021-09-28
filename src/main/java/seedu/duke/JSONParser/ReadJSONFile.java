package seedu.duke.JSONParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.duke.module.Module;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadJSONFile {
    ArrayList<Module> modList = new ArrayList<>();

    public static String[] attributes() {
        return new String[]{"moduleCode", "title", "description"};
    }

    public static void read() {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(".\\data\\CG1112.json");
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject)obj;

            parseModObject(jsonObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseModObject(JSONObject jsonObject) {
        Module module = new Module();

        for (String i : ReadJSONFile.attributes()) {
            String input = (String) jsonObject.get(i);
            module.add(i, input);
        }

        String output = module.toString();
        System.out.println(output);
    }
}
