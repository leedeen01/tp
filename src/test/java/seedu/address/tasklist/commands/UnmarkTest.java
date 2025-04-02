package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskListException;
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
        TaskListException e = assertThrows(TaskListException.class, () -> Unmark.execute("unmark one", taskList));
        assertEquals("You need to pick an index number to unmark in the list. You can try again.", e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Buy groceries"));
        TaskListException e = assertThrows(TaskListException.class, () -> Unmark.execute("unmark 2", taskList));
        assertEquals("You need to unmark something actually in the list, silly", e.getMessage());
    }

    @Test
    void execute_unmarkInEmptyList() {
        TaskListException e = assertThrows(TaskListException.class, () -> Unmark.execute("unmark 1", taskList));
        assertEquals("You need to unmark something actually in the list, silly", e.getMessage());
    }

    @Test
    void execute_unmarkAlrUnmarked() throws TaskListException {
        taskList.add(new ToDo("Buy pen"));
        assertEquals("The task is already unmarked.", Unmark.execute("unmark 1", taskList));
    }
    //correct test

    @Test
    void execute_unmarkSuccess() throws TaskListException {
        taskList.add(new ToDo("Buy pen"));
        Mark.execute("mark 1", taskList);
        assertEquals("Oops, no problem."
                        + "\nI've unmarked the task:\n  [T][ ] Buy pen",
                Unmark.execute("unmark 1", taskList));
    }
}
