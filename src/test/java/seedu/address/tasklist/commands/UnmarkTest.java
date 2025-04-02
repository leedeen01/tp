package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;

public class UnmarkTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }


    @Test
    void execute_nonIntegerIndex() {
        taskList.add(new ToDo("Buy groceries"));
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Unmark.execute("unmark one", taskList));
        assertEquals("You need to pick an integer (e.g. 1) from the existing task list.", e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Buy groceries"));
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Unmark.execute("unmark 2", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_unmarkInEmptyList() {
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Unmark.execute("unmark 1", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_unmarkAlrUnmarked() throws TaskManagerException {
        taskList.add(new ToDo("Buy pen"));
        assertEquals("The task is already unmarked.", Unmark.execute("unmark 1", taskList));
    }
    //correct test

    @Test
    void execute_unmarkSuccess() throws TaskManagerException {
        taskList.add(new ToDo("Buy pen"));
        Mark.execute("mark 1", taskList);
        assertEquals("Task unmarked:\n  [T][ ] Buy pen",
                Unmark.execute("unmark 1", taskList));
    }
}
