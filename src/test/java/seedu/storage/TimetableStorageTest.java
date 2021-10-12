package seedu.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.junit.jupiter.api.Test;

import seedu.timetable.Timetable;

public class TimetableStorageTest {

    String path = System.getProperty("user.dir") + "/data/testSave.json";

    @Test
    public void loadSchedule_invalidFilePath_returnsNewTimetable() throws Exception {
        TimetableStorage storage = new TimetableStorage(path);
        Timetable timetable = storage.loadSchedule();

        assertNotNull(timetable);
        assertEquals(timetable.getSemester(), 1);

        File file = new File(path);
        file.delete();
    }

    //    @Test
    //    public void save_emptyTimetable_JsonFieldsCreated() throws Exception {
    //        TimetableStorage storage = new TimetableStorage(path);
    //        storage.save(new Timetable(1));
    //
    //        JsonObject savedTimetable = new Gson().fromJson(new FileReader(path), JsonObject.class);
    //        assertEquals(savedTimetable.get("semester"), new JsonPrimitive(1));
    //        assertNotNull(savedTimetable.get("monday"));
    //        assertNotNull(savedTimetable.get("tuesday"));
    //        assertNotNull(savedTimetable.get("wednesday"));
    //        assertNotNull(savedTimetable.get("thursday"));
    //        assertNotNull(savedTimetable.get("friday"));
    //        assertNotNull(savedTimetable.get("modules"));
    //
    //        File file = new File(path);
    //        file.delete();
    //    }
}
