package seedu.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import seedu.module.Module;
import seedu.ui.TextUi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;


public class ModStorage {

    private final String path;
    private final File file;

    public ModStorage(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public static void saveModInfo(String moduleCode, InputStream inputStream) throws IOException, FileErrorException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            String path = "data/Modules/" + moduleCode + ".json";
            createModJson(path);
            addToFile(inputLine, path);
        }
        in.close();
    }

    private static void addToFile(String input, String path) throws IOException {
        FileWriter fw = new FileWriter(path, false);
        String output = input + "\n";
        fw.append(output);
        fw.close();
    }

    public static boolean createModJson(String savePath) throws FileErrorException {
        try {
            File modFile = new File(savePath);
            if (!modFile.exists()) {
                modFile.getParentFile().mkdirs();
            }
            if (modFile.createNewFile()) {
                return true;
            }
        } catch (IOException e) {
            throw new FileErrorException();
        } catch (SecurityException e) {
            throw new SecurityException();
        }
        return false;
    }

    public static void searchModsOffline(String searchTerm) {
        File dir = new File("data/Modules/");
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            try {
                searchFiles(fileList, searchTerm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        TextUi.printLocalSearchMessage();
    }

    private static void searchFiles(File[] fileList, String searchTerm) throws IOException {
        int count = 0;
        for (File file : fileList) {
            if (fileNameContains(file, searchTerm)) {
                InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                Module module = new Gson().fromJson(reader, Module.class);
                TextUi.printModBriefDescription(module);
                count++;
            }
        }
        TextUi.printModsFound(count);
    }

    private static boolean fileNameContains(File file, String searchTerm) {
        return file.getName().toLowerCase().contains(searchTerm.toLowerCase());
    }

    public static Module loadModInfo(String moduleCode) throws IOException {
        File file = new File("data/Modules/" + moduleCode + ".json");
        InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        return new Gson().fromJson(reader, Module.class);
    }

    public static void showModOffline(String moduleCode) {
        try {
            Module module = loadModInfo(moduleCode);
            TextUi.printModFullDescription(module);
        } catch (IOException e) {
            TextUi.printNotFoundMessage();
        }
        TextUi.printLocalSearchMessage();
    }

    public static class FileErrorException extends Exception {
    }

}
