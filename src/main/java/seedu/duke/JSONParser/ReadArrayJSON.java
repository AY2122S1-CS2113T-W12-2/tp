package seedu.duke.JSONParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * This class is used to download all module detail by parsing moduleCode into the writeJSONFile download
 * method
 */
public class ReadArrayJSON {
    private static final String FILEPATH = ".\\data\\Modules.json";

    public WriteJSONFile writeJSONFile = new WriteJSONFile();

    /**
     * Make sure that the ModuleInfo.json file exist or else method will not work
     * I have removed the ModuleInfo.json file as for now
     */
    public void read() {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(".\\data\\ModuleInfo.json")) {
            Object obj = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;

            jsonArray.forEach(module -> parseModule((JSONObject) module));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseModule(JSONObject module) {
        String moduleCode = (String) module.get("moduleCode");
//        try {
//            addToFile(moduleCode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.print("\"" + moduleCode + "\"" + ", ");
        try {
            writeJSONFile.download(moduleCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToFile(String input) throws IOException {
        FileWriter fileWrite = new FileWriter(FILEPATH, false);
        String output = input + "\n";
        fileWrite.append(output);
        fileWrite.close();
    }
}
