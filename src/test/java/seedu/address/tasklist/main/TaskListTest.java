package seedu.address.tasklist.main;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskListException;
import seedu.address.tasklist.storage.TaskStorage;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;

public class TaskListTest {

    private TaskList TaskList;

    @BeforeEach
    void setUp() throws TaskListException {
        ArrayList<Task> initialTasks = new ArrayList<>();
        initialTasks.add(new ToDo("Test Task"));
        TaskStorage.updateList(initialTasks); // Ensure the file starts with this known state

        TaskList = new TaskList();
    }

    @Test
    void getResponse_listCommand_returnsListOfTasks() {
        String response = TaskList.getResponse("list");
        assert (response.contains("Test Task"));
    }

    @Test
    void getResponse_todoCommand_addsTask() {
        String response = TaskList.getResponse("todo Buy milk");
        assert (response.contains("Buy milk"));
    }

    @Test
    void getResponse_deadlineCommand_addsDeadlineTask() {
        String response = TaskList.getResponse("deadline Submit report /by 18/2/2025 2359");
        assert (response.contains("Submit report"));
    }

    @Test
    void getResponse_eventCommand_addsEventTask() {
        String response = TaskList.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");
        assert (response.contains("Team meeting"));
    }

    @Test
    void getResponse_markCommand_marksTaskAsDone() {
        TaskList.getResponse("todo Complete project");
        String response = TaskList.getResponse("mark 2");
        assert (response.contains("[X] Complete project"));
    }

    @Test
    void getResponse_unmarkCommand_unmarksTask() {
        TaskList.getResponse("todo Complete project");
        TaskList.getResponse("mark 2");
        String response = TaskList.getResponse("unmark 2");
        assert (response.contains("[ ] Complete project"));
    }


    @Test
    void getResponse_findCommand_findsTasksWithKeyword() {
        String response = TaskList.getResponse("find Test");
        assert (response.contains("Test Task"));
    }

    @Test
    void getResponse_dayPlanCommand_showsTasksForDate() {
        TaskList.getResponse("deadline Submit report /by 18/2/2025 2359");
        TaskList.getResponse("event Team meeting /from 18/2/2025 1400 /to 18/2/2025 1600");

        String response = TaskList.getResponse("agenda for 18/2/2025");
        assert (response.contains("Submit report"));
        assert (response.contains("Team meeting"));
    }

}
