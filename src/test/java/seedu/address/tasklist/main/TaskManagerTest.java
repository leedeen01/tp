package seedu.address.tasklist.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.storage.TaskStorage;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;

public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() throws TaskManagerException {
        ArrayList<Task> initialTasks = new ArrayList<>();
        initialTasks.add(new ToDo("Test Task"));
        TaskStorage.updateList(initialTasks); // Reset with known state

        taskManager = new TaskManager();
    }

    @Test
    void getResponse_listCommand_returnsListOfTasks() {
        String response = taskManager.getResponse("list");
        assertTrue(response.contains("Test Task"));
    }

    @Test
    void getResponse_todoCommand_addsTask() {
        String response = taskManager.getResponse("todo Buy milk");
        assertTrue(response.contains("Buy milk"));
    }

    @Test
    void getResponse_deadlineCommand_addsDeadlineTask() {
        String response = taskManager.getResponse("deadline Submit report /by 18/2/2025 2359");
        assertTrue(response.contains("Submit report"));
    }

    @Test
    void getResponse_eventCommand_addsEventTask() {
        String response = taskManager.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");
        assertTrue(response.contains("Team meeting"));
    }

    @Test
    void getResponse_markCommand_marksTaskAsDone() {
        taskManager.getResponse("todo Complete project");
        String response = taskManager.getResponse("mark 2");
        assertTrue(response.contains("[X] Complete project"));
    }

    @Test
    void getResponse_unmarkCommand_unmarksTask() {
        taskManager.getResponse("todo Complete project");
        taskManager.getResponse("mark 2");
        String response = taskManager.getResponse("unmark 2");
        assertTrue(response.contains("[ ] Complete project"));
    }

    @Test
    void getResponse_findCommand_findsTasksWithKeyword() {
        String response = taskManager.getResponse("find Test");
        assertTrue(response.contains("Test Task"));
    }

    @Test
    void getResponse_dayPlanCommand_showsTasksForDate() {
        taskManager.getResponse("deadline Submit report /by 18/2/2025 2359");
        taskManager.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");

        String response = taskManager.getResponse("agenda for 18/2/2025");
        assertTrue(response.contains("Submit report"));
        assertTrue(response.contains("Team meeting"));
    }

    @Test
    void getResponse_emptyInput_returnsErrorMessage() {
        String response = taskManager.getResponse("");
        assertTrue(response.contains("Input cannot be empty!"));
    }

    @Test
    void getResponse_unknownCommand_returnsErrorMessage() {
        String response = taskManager.getResponse("hi");
        assertTrue(response.contains("Unknown command"));
    }

    @Test
    void getResponse_markCommandMissingArg_returnsError() {
        String response = taskManager.getResponse("mark");
        assertTrue(response.contains("requires exactly one argument"));
    }

    @Test
    void getResponse_todoCommandMissingDescription_returnsError() {
        String response = taskManager.getResponse("todo   ");
        assertTrue(response.contains("must include a task description"));
    }

    @Test
    void getResponse_deadlineCommandMissingBy_returnsError() {
        String response = taskManager.getResponse("deadline Finish homework");
        assertTrue(response.contains("DEADLINE format"));
    }

    @Test
    void getResponse_eventCommandMissingFromOrTo_returnsError() {
        String response = taskManager.getResponse("event Team sync /from 2pm");
        assertTrue(response.contains("EVENT format"));
    }
}
