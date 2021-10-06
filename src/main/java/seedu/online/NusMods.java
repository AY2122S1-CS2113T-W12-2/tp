package seedu.online;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import seedu.module.Module;
import seedu.storage.ModStorage;
import seedu.ui.TextUi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NusMods {

    private static final String MODULE_API = "https://api.nusmods.com/v2/2021-2022/modules/";

    public static void searchModsOnline(String searchTerm) throws IOException {
        InputStream inputStream = getOnlineModList();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        int count = 0;
        reader.beginArray();
        while (reader.hasNext()) {
            Module module = new Gson().fromJson(reader, Module.class);
            if (module.codeContains(searchTerm)) {
                String moduleCode = module.getModuleCode();
                TextUi.printModBriefDescription(fetchModOnline(moduleCode));
                count++;
            }
        }
        reader.endArray();
        TextUi.printModsFound(count);
    }

    public static Module fetchModOnline(String moduleCode) throws IOException {
        try {
            InputStream inputStream = getOnlineModInfo(moduleCode);
            ModStorage.saveModInfo(moduleCode, inputStream);
            return ModStorage.loadModInfo(moduleCode);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    private static InputStream getOnlineModList() throws IOException {
        String url = "https://api.nusmods.com/v2/2021-2022/moduleInfo.json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return con.getInputStream();
    }

    private static InputStream getOnlineModInfo(String moduleCode) throws IOException {
        String url = MODULE_API + moduleCode + ".json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return con.getInputStream();
    }

    public static void showModOnline(String moduleCode) throws IOException {
        Module module = fetchModOnline(moduleCode);
        TextUi.printModFullDescription(module);
    }

    public static void update() throws IOException, ModStorage.FileErrorException {
        TextUi.printUpdateStartMessage();
        InputStream inputStream = getOnlineModList();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        int count = 0;
        reader.beginArray();
        while (reader.hasNext()) {
            try {
                Module module = new Gson().fromJson(reader, Module.class);
                String moduleCode = module.getModuleCode();
                InputStream modStream = getOnlineModInfo(moduleCode);
                ModStorage.saveModInfo(moduleCode, modStream);
                count++;
                System.out.println(count);
            } catch (Exception e) {
                TextUi.printErrorMessage();
            }
        }
        reader.endArray();
        TextUi.printUpdateSuccessMessage();
    }

}
