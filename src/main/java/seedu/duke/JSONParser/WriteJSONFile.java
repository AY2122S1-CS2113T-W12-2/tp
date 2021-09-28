package seedu.duke.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WriteJSONFile {

    public void download(String moduleCode) throws Exception {
        String url = "https://api.nusmods.com/v2/2020-2021/modules/" + moduleCode + ".json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            String filePath = createFile(moduleCode);
            addToFile(inputLine, filePath);
        }
        in.close();
    }

    private void addToFile(String input, String filePath) throws IOException {
        FileWriter fileWrite = new FileWriter(filePath, false);
        String output = input + "\n";
        fileWrite.append(output);
        fileWrite.close();
    }

    private String createFile(String module) {
        File file = new File(getFilePath(module));
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println("Module Path: " + getFilePath(module));
        return getFilePath(module);
    }

    private String getFilePath(String module) {
        String output =  ".\\data\\" + module + ".json";
        return output;
    }
}
