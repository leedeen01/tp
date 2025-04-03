package seedu.address.tasklist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.tasks.Event;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;

public class TaskStorageTest {
    private static final String FILE_PATH = "data/taskList.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @BeforeEach
    void setUp() {
        // Clear file before each test
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void cleanUp() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

        File backup = new File(FILE_PATH + ".bak");
        if (backup.exists()) {
            backup.delete();
        }
    }

    @Test
    public void loadList_malformedLines_skipsThem() throws Exception {
        File file = new File(FILE_PATH);
        try (var writer = new java.io.FileWriter(file)) {
            writer.write("X | T: Valid ToDo | --\n");
            writer.write("Corrupted line without proper format\n");
            writer.write(" | D: Incomplete | missing time info\n");
            writer.write("X | E: Meeting | from: 20/4/2025 1000, to: 20/4/2025 1200\n");
        }

        ArrayList<Task> loaded = TaskStorage.loadList();
        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0) instanceof ToDo);
        assertTrue(loaded.get(1) instanceof Event);
    }

}
