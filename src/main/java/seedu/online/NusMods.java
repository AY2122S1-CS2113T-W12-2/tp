package seedu.online;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import seedu.command.flags.SearchFlags;
import seedu.exceptions.FetchException;
import seedu.module.Module;
import seedu.storage.ModStorage;
import seedu.ui.TextUi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NusMods {

    private static final Logger logger = Logger.getLogger("");

    private static final String MODULE_API = "https://api.nusmods.com/v2/2021-2022/modules/";

    /**
     * Retrieves mod list from NUSMods API then iterates through it and prints all matching mods.
     *
     * @param searchTerm  searchTerm that has to be contained in the moduleCode.
     * @param searchFlags secondary variables that will be checked to narrow the search.
     * @throws IOException if there is no connection.
     */
    public static void searchModsOnline(String searchTerm, SearchFlags searchFlags) throws IOException {
        InputStream inputStream = getOnlineModList();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        int count = 0;
        reader.beginArray();
        while (reader.hasNext()) {
            Module module = new Gson().fromJson(reader, Module.class);
            if (isMatch(module, searchTerm, searchFlags)) {
                count++;
            }
        }
        reader.endArray();
        TextUi.printModsFound(count);
    }

    /**
     * Checks if mod matches search term and all flags, and prints all matching mods.
     *
     * @param module      module to check.
     * @param searchTerm  search term to be compared.
     * @param searchFlags flags to check with mod details.
     * @return true if everything matches, false otherwise.
     * @throws IOException if there is no connection.
     */
    public static boolean isMatch(Module module, String searchTerm, SearchFlags searchFlags) throws IOException {
        if (module.meetsPreliminaryConditions(searchTerm, searchFlags)) {
            String moduleCode = module.getModuleCode();
            try {
                module = fetchModOnline(moduleCode);
            } catch (FetchException e) {
                throw new IOException();
            }
            if (module.meetsSecondaryConditions(searchFlags)) {
                TextUi.printModBriefDescription(module);
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches a mod from NUSMods, saves it, then returns it.
     *
     * @param moduleCode code of the module to fetch.
     * @return module that was fetched from NUSMods.
     * @throws FetchException if there is no connection.
     */
    public static Module fetchModOnline(String moduleCode) throws FetchException {
        try {
            InputStream inputStream = getOnlineModInfo(moduleCode);
            ModStorage.saveModInfo(moduleCode, inputStream);
            return ModStorage.loadModInfo(moduleCode);
        } catch (Exception e) {
            throw new FetchException("Unable to fetch module");
        }
    }

    /**
     * Retrieves the mod list from NUSMods and returns it as an Inputstream.
     *
     * @return list of mods as an Inputstream.
     * @throws IOException if there is no connection.
     */
    private static InputStream getOnlineModList() throws IOException {
        String url = "https://api.nusmods.com/v2/2021-2022/moduleInfo.json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return con.getInputStream();
    }

    /**
     * Retrives detailed mod information from NUSMods and returns it as an Inputstream.
     *
     * @param moduleCode mod to retrieve.
     * @return detailed mod information as an Inputstream.
     * @throws IOException if there is no connection.
     */
    private static InputStream getOnlineModInfo(String moduleCode) throws IOException {
        String url = MODULE_API + moduleCode + ".json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        return con.getInputStream();
    }

//    /**
//     * Fetches all mods from NUSMods and saves all of them into the local storage.
//     *
//     * @throws IOException if there is no connection.
//     */
//    public static void update() throws IOException, ModStorage.FileErrorException {
//        TextUi.printUpdateStartMessage();
//        InputStream inputStream = getOnlineModList();
//        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
//        int count = 0;
//        reader.beginArray();
//        while (reader.hasNext()) {
//            if (TextUi.interruptCommand()) {
//                return;
//            }
//            Module module = new Gson().fromJson(reader, Module.class);
//            String moduleCode = module.getModuleCode();
//            try {
//                InputStream modStream = getOnlineModInfo(moduleCode);
//                ModStorage.saveModInfo(moduleCode, modStream);
//                count++;
//                System.out.println(count);
//            } catch (Exception e) {
//                logger.log(Level.WARNING, "Failed to save mod" + moduleCode);
//                TextUi.printErrorMessage();
//            }
//        }
//        reader.endArray();
//        TextUi.printUpdateSuccessMessage();
//    }

    /**
     * Fetches all mods from NUSMods and saves all of them into the local storage.
     *
     * @throws IOException if there is no connection.
     */
    public static void updateSequence() throws IOException {
        UpdateThread updateThread = new UpdateThread();
        updateThread.start();
        InterruptThread interruptThread = new InterruptThread(updateThread);
        interruptThread.start();
        while (updateThread.isAlive()) {
            //locks code here while thread is running
        }
    }

    /**
     * Fetches all mods from NUSMods and saves all of them into the local storage.
     *
     * @throws IOException if there is no connection.
     */



    /**
     * Attempts to first fetch module info from online API.
     * If fails, fetches module information from local save.
     *
     * @param moduleCode Module to be fetched
     * @return a Module instance representing the relevant information
     */
    public static Module fetchMod(String moduleCode) {
        Module module = null;
        try {
            module = fetchModOnline(moduleCode);
            logger.log(Level.INFO, "Online search done");
        } catch (FetchException e) {
            TextUi.printNoConnectionMessage();
            logger.log(Level.INFO, "Unable to retrieve data from NUSMods, searching offline");
            try {
                module = ModStorage.loadModInfo(moduleCode);
                logger.log(Level.INFO, "Offline search done");
                TextUi.printLocalSearchMessage();
                return module;
            } catch (IOException e2) {
                logger.log(Level.INFO, "Unable to find mod locally.");
            }
        }
        return module;
    }

    public static class InterruptThread extends Thread {

        private final Thread thread;

        public InterruptThread(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            while (thread.isAlive()) {
                Scanner s = new Scanner(System.in);
                s.nextLine();
                thread.interrupt();
            }
        }
    }

    public static class SearchThread extends Thread {
    }

    public static class UpdateThread extends Thread {

        public void run() {
            try {
                update();
            } catch (IOException e) {
                TextUi.printNoConnectionMessage();
            } catch (InterruptedException e) {
                TextUi.printErrorMessage();
            }
        }

        public static void update() throws IOException, InterruptedException {
            TextUi.printUpdateStartMessage();
            sleep(1000);
            InputStream inputStream = getOnlineModList();
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
            int count = 0;
            reader.beginArray();
            while (reader.hasNext()) {
                if (currentThread().isInterrupted()) {
                    TextUi.printUpdateInterruptMessage();
                    return;
                }
                Module module = new Gson().fromJson(reader, Module.class);
                String moduleCode = module.getModuleCode();
                try {
                    InputStream modStream = getOnlineModInfo(moduleCode);
                    ModStorage.saveModInfo(moduleCode, modStream);
                    count++;
                    System.out.println(count);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Failed to save mod" + moduleCode);
                    TextUi.printErrorMessage();
                }
            }
            reader.endArray();
            TextUi.printUpdateSuccessMessage();
        }
    }
}
