package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;



public class MarkTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }


    @Test
    void execute_nonIntegerIndex() {
        taskList.add(new ToDo("Buy groceries"));
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Mark.execute("mark one", taskList));
        assertEquals("You need to pick an index number to mark in the list.", e.getMessage());
    }

    @Test
    void execute_outOfBoundsIndex() {
        taskList.add(new ToDo("Buy groceries"));
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Mark.execute("mark 2", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_markInEmptyList() {
        TaskManagerException e = assertThrows(TaskManagerException.class, () -> Mark.execute("mark 1", taskList));
        assertEquals("You need to pick an index from the existing task list.", e.getMessage());
    }

    @Test
    void execute_markAlrMarked() throws TaskManagerException {
        taskList.add(new ToDo("Buy pen"));
        Mark.execute("mark 1", taskList);
        assertEquals("The task is already marked.", Mark.execute("mark 1", taskList));
    }
    //correct test

    @Test
    void execute_markSuccess() throws TaskManagerException {
        taskList.add(new ToDo("Buy pen"));
        assertEquals("Task marked as done:\n  [T][X] Buy pen",
                Mark.execute("mark 1", taskList));
    }

}
