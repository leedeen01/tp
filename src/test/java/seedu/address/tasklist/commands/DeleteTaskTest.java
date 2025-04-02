package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;

public class DeleteTaskTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void execute_deleteFromEmptyList() {
        TaskManagerException e = assertThrows(TaskManagerException.class, ()
                -> DeleteTask.execute("delete 1", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_nonIntegerIndex() {
        taskList.add(new ToDo("Buy groceries")); // Add at least 1 task
        TaskManagerException e = assertThrows(TaskManagerException.class, ()
                -> DeleteTask.execute("delete one", taskList));
        assertEquals("You need to pick a single index number to delete from the list. You can try again.",
                e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Do homework"));
        TaskManagerException e = assertThrows(TaskManagerException.class, ()
                -> DeleteTask.execute("delete 5", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_correctDeletion() throws TaskManagerException {
        taskList.add(new ToDo("Walk the dog"));
        taskList.add(new ToDo("Go jogging"));
        taskList.add(new ToDo("Read a book"));

        String result = DeleteTask.execute("delete 2", taskList);
        String expected = "Deleting [T][ ] Go jogging";

        assertEquals(expected, result);
        assertEquals(2, taskList.size()); // Ensure size updated
    }

    @Test
    void execute_correctDeletionOneLeft() throws TaskManagerException {
        taskList.add(new ToDo("Submit assignment"));
        taskList.add(new ToDo("Pay bills"));

        String result = DeleteTask.execute("delete 2", taskList);
        String expected = "Deleting [T][ ] Pay bills";

        assertEquals(expected, result);
        assertEquals(1, taskList.size()); // Ensure size updated
    }

    @Test
    void execute_correctDeletionAllDeleted() throws TaskManagerException {
        taskList.add(new ToDo("Cook dinner"));

        String result = DeleteTask.execute("delete 1", taskList);
        String expected = "Deleting [T][ ] Cook dinner";

        assertEquals(expected, result);
        assertEquals(0, taskList.size()); // Ensure list is now empty
    }
}
