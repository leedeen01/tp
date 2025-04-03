package seedu.address.tasklist.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Deadline;
import seedu.address.tasklist.tasks.Event;
import seedu.address.tasklist.tasks.Task;

public class DayPlanTest {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
    }

    @Test
    void noTasks() throws TaskManagerException {
        String result = DayPlan.execute("agenda for 10/2/2025", taskList);
        assertEquals("You have no tasks at the moment. Free all day!", result);
    }

    @Test
    void variousTasksInDay() throws TaskManagerException {
        taskList.add(new Deadline("Submit proposal", LocalDateTime.parse("10/2/2025 2359", INPUT_FORMATTER)));
        taskList.add(new Event("Conference",
                LocalDateTime.parse("10/2/2025 0900", INPUT_FORMATTER),
                LocalDateTime.parse("10/2/2025 1700", INPUT_FORMATTER)));

        String expectedOutput = "Here's what's happening on Monday, Feb 10 2025:\n"
                + "\nDEADLINES:\nSubmit proposal due at: 11:59pm\nYou have 1 deadline on this day.\n"
                + "\nEVENTS:\nConference from: Monday, Feb 10 2025, to: Monday, Feb 10 2025\n"
                + "You have 1 event on this day.";

        String result = DayPlan.execute("agenda for 10/2/2025", taskList);
        assertEquals(expectedOutput, result);
    }

    @Test
    void missingDate_throwsException() {
        try {
            DayPlan.execute("agenda for", taskList);
        } catch (TaskManagerException e) {
            assertEquals("Missing date. Please provide a valid date "
                    + "in the format d/M/yyyy (e.g., 2/12/2023).", e.getMessage());
        }
    }

    @Test
    void wrongFormatKeyword_throwsException() {
        try {
            DayPlan.execute("agenda 2/12/2023", taskList);
        } catch (TaskManagerException e) {
            assertEquals("Wrong command format. Try:\n "
                    + "agenda for d/M/yyyy (e.g., agenda for 2/12/2023)", e.getMessage());
        }
    }

    @Test
    void invalidDateFormat_throwsException() {
        try {
            DayPlan.execute("agenda for 12-25-2023", taskList);
        } catch (TaskManagerException e) {
            assertEquals("Invalid date. \nPlease check the day, month, "
                    + "and format (d/M/yyyy, e.g., 2/12/2023).", e.getMessage());
        }
    }

    @Test
    void multipleDeadlinesAndEvents_correctlySummarized() throws TaskManagerException {
        taskList.add(new Deadline("Submit 1", LocalDateTime.parse("11/2/2025 1200", INPUT_FORMATTER)));
        taskList.add(new Deadline("Submit 2", LocalDateTime.parse("11/2/2025 1700", INPUT_FORMATTER)));
        taskList.add(new Event("Hackathon",
                LocalDateTime.parse("10/2/2025 2300", INPUT_FORMATTER),
                LocalDateTime.parse("11/2/2025 0500", INPUT_FORMATTER)));
        taskList.add(new Event("Seminar",
                LocalDateTime.parse("11/2/2025 0800", INPUT_FORMATTER),
                LocalDateTime.parse("11/2/2025 1000", INPUT_FORMATTER)));

        String result = DayPlan.execute("agenda for 11/2/2025", taskList);

        assert result.contains("You have 2 deadlines on this day.");
        assert result.contains("You have 2 events on this day.");
    }
}
