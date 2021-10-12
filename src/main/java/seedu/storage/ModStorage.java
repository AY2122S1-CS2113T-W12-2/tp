package seedu.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import seedu.command.flags.SearchFlags;
import seedu.module.Module;
import seedu.online.NusMods;
import seedu.ui.TextUi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.logging.Logger;

import static seedu.online.NusMods.isMatch;

public class ModStorage {

    public static void saveModInfo(String moduleCode, InputStream inputStream) throws IOException, FileErrorException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            String path = System.getProperty("user.dir") + "data/Modules/" + moduleCode + ".json";
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

    public static void searchModsOffline(String searchTerm, SearchFlags searchFlags) {
        File dir = new File(System.getProperty("user.dir") + "data/Modules/");
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            try {
                searchFiles(fileList, searchTerm, searchFlags);
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

    private static void searchFiles(File[] fileList, String searchTerm, SearchFlags searchFlags) throws IOException {
        int count = 0;
        for (File file : fileList) {
            String extension = ".json";
            int extensionIndex = file.getName().indexOf(extension);
            String modCode = file.getName().substring(0, extensionIndex);
            Module module = new Module(modCode);
            if (NusMods.isMatch(module, searchTerm, searchFlags)) {
                count++;
            }
        }
        TextUi.printModsFound(count);
    }

    private static boolean fileNameContains(File file, String searchTerm) {
        return file.getName().toLowerCase().contains(searchTerm.toLowerCase());
    }

    public static Module loadModInfo(String moduleCode) throws IOException {
        File file = new File(System.getProperty("user.dir") + "data/Modules/" + moduleCode + ".json");
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
