package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.TaskList;

public class AddTaskTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void validToDo() throws TaskManagerException {
        String response = AddTask.todo("todo Buy groceries", taskList.getAllTasks());
        Task task = taskList.getAllTasks().get(0);
        String expected = "New task added.\n " + task + "\n1 task in the list.";
        assertEquals(expected, response);
    }

    @Test
    void missingToDoTaskDescription() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.todo("todo", taskList.getAllTasks());
        });
        assertEquals("No task entered.", thrown.getMessage());
    }

    @Test
    void duplicateToDo() throws TaskManagerException {
        AddTask.todo("todo Buy milk", taskList.getAllTasks());
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.todo("todo Buy milk", taskList.getAllTasks());
        });
        assertEquals("This task already exists in your list.", thrown.getMessage());
    }

    @Test
    void validDeadlineInput() throws TaskManagerException {
        String response = AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList.getAllTasks());
        Task task = taskList.getAllTasks().get(0);
        String expected = "New task added.\n " + task + "\n1 task in the list.";
        assertEquals(expected, response);
    }

    @Test
    void missingDeadlineDescription() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.deadline("deadline /by 12/3/2025 1800", taskList.getAllTasks());
        });
        assertEquals("No task entered.", thrown.getMessage());
    }

    @Test
    void missingDeadlineDate() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.deadline("deadline Submit report", taskList.getAllTasks());
        });
        assertEquals("No due-date entered.", thrown.getMessage());
    }

    @Test
    void invalidDeadlineFormat() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.deadline("deadline Submit report /by 32/13/2025 1800", taskList.getAllTasks());
        });
        assertEquals("Invalid date format. Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).",
                thrown.getMessage());
    }

    @Test
    void duplicateDeadline() throws TaskManagerException {
        AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList.getAllTasks());
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList.getAllTasks());
        });
        assertEquals("This deadline already exists in your list.", thrown.getMessage());
    }

    @Test
    void validEventInput() throws TaskManagerException {
        String response = AddTask.event("event Team meeting /from 1/4/2025 0900 /to 1/4/2025 1100",
                taskList.getAllTasks());
        Task task = taskList.getAllTasks().get(0);
        String expected = "New task added.\n " + task + "\n1 task in the list.";
        assertEquals(expected, response);
    }

    @Test
    void missingEventName() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event /from 1/4/2025 0900 /to 1/4/2025 1100", taskList.getAllTasks());
        });
        assertEquals("No event entered.", thrown.getMessage());
    }

    @Test
    void missingEventStart() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event Conference /to 1/4/2025 1100", taskList.getAllTasks());
        });
        assertEquals("Event start/end not entered.", thrown.getMessage());
    }

    @Test
    void missingEventEnd() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event Conference /from 1/4/2025 0900", taskList.getAllTasks());
        });
        assertEquals("Event start/end not entered.", thrown.getMessage());
    }

    @Test
    void invalidEventFormat() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event Conference /from 32/13/2025 0900 /to 1/4/2025 1100", taskList.getAllTasks());
        });
        assertEquals("Invalid date format. Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)."
                + "\nOr check that the start time is before the end time.", thrown.getMessage());
    }

    @Test
    void invalidEventTimeRange() {
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event Conference /from 2/4/2025 1800 /to 1/4/2025 0900", taskList.getAllTasks());
        });
        assertEquals("Event start time must be before end time.", thrown.getMessage());
    }

    @Test
    void duplicateEvent() throws TaskManagerException {
        AddTask.event("event Project Demo /from 1/4/2025 1200 /to 1/4/2025 1300", taskList.getAllTasks());
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () -> {
            AddTask.event("event Project Demo /from 1/4/2025 1200 /to 1/4/2025 1300", taskList.getAllTasks());
        });
        assertEquals("This event already exists in your list.", thrown.getMessage());
    }
}
