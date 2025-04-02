package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskListException;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.TaskList;

public class AddTaskTest {
    TaskList taskList = new TaskList();

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void validToDo() throws TaskListException {
        String response = AddTask.todo("todo Buy groceries", taskList);
        assertEquals("New task added.\n [T][ ] Buy groceries\n1 task in the list.", response);
    }

    @Test
    void missingToDoTaskDescription() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.todo("todo", taskList);
        });
        assertEquals("No task entered.", thrown.getMessage());
    }

    @Test
    void duplicateToDo() throws TaskListException {
        AddTask.todo("todo Buy milk", taskList);
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.todo("todo Buy milk", taskList);
        });
        assertEquals("This task already exists in your list.", thrown.getMessage());
    }

    @Test
    void validDeadlineInput() throws TaskListException {
        String response = AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList);
        assertEquals("New task added.\n [D][ ] Submit report (by: 12 Mar 2025 06:00 PM)"
                + "\n1 task in the list.", response);
    }

    @Test
    void missingDeadlineDescription() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.deadline("deadline /by 12/3/2025 1800", taskList);
        });
        assertEquals("No task entered.", thrown.getMessage());
    }

    @Test
    void missingDeadlineDate() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.deadline("deadline Submit report", taskList);
        });
        assertEquals("No due-date entered.", thrown.getMessage());
    }

    @Test
    void invalidDeadlineFormat() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.deadline("deadline Submit report /by 32/13/2025 1800", taskList);
        });
        assertEquals("Invalid date format. Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800).",
                thrown.getMessage());
    }

    @Test
    void duplicateDeadline() throws TaskListException {
        AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList);
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.deadline("deadline Submit report /by 12/3/2025 1800", taskList);
        });
        assertEquals("This deadline already exists in your list.", thrown.getMessage());
    }

    @Test
    void validEventInput() throws TaskListException {
        String response = AddTask.event("event Team meeting /from 1/4/2025 0900 /to 1/4/2025 1100", taskList);
        assertEquals("New task added.\n [E][ ] Team meeting (from: 01 Apr 2025 09:00 AM to: 01 Apr 2025 11:00 AM)"
                + "\n1 task in the list.", response);
    }

    @Test
    void missingEventName() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event /from 1/4/2025 0900 /to 1/4/2025 1100", taskList);
        });
        assertEquals("No event entered.", thrown.getMessage());
    }

    @Test
    void missingEventStart() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event Conference /to 1/4/2025 1100", taskList);
        });
        assertEquals("Event start/end not entered.", thrown.getMessage());
    }

    @Test
    void missingEventEnd() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event Conference /from 1/4/2025 0900", taskList);
        });
        assertEquals("Event start/end not entered.", thrown.getMessage());
    }

    @Test
    void invalidEventFormat() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event Conference /from 32/13/2025 0900 /to 1/4/2025 1100", taskList);
        });
        assertEquals("Invalid date format. Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)."
                + "\nOr check that the start time is before end time.", thrown.getMessage());
    }

    @Test
    void invalidEventTimeRange() {
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event Conference /from 2/4/2025 1800 /to 1/4/2025 0900", taskList);
        });
        assertEquals("Invalid date format. Try again and use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)."
                + "\nOr check that the start time is before end time.", thrown.getMessage());
    }

    @Test
    void duplicateEvent() throws TaskListException {
        AddTask.event("event Project Demo /from 1/4/2025 1200 /to 1/4/2025 1300", taskList);
        TaskListException thrown = assertThrows(TaskListException.class, () -> {
            AddTask.event("event Project Demo /from 1/4/2025 1200 /to 1/4/2025 1300", taskList);
        });
        assertEquals("This event already exists in your list.", thrown.getMessage());
    }
}
