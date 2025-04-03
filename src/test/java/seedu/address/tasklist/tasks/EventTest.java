package seedu.address.tasklist.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.tasklist.exception.TaskManagerException;

public class EventTest {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    @Test
    public void constructor_invalidTimeRange_throwsException() {
        try {
            new Event("Conference",
                    LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER),
                    LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER));
            assertEquals(1, 0); // Constructor should not be able to reach this line -> else: fail
        } catch (TaskManagerException e) {
            assertEquals("Event start time must be before end time.", e.getMessage());
        }
    }

    @Test
    public void includesDate_withinEventPeriod_returnsTrue() throws TaskManagerException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        boolean result = event.includesDate(LocalDate.parse("11/3/2025", DateTimeFormatter.ofPattern("d/M/yyyy")));
        assertEquals(true, result);
    }

    @Test
    public void includesDate_withinEventPeriod_returnsFalse() throws TaskManagerException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        boolean result = event.includesDate(LocalDate.parse("9/3/2025", DateTimeFormatter.ofPattern("d/M/yyyy")));
        assertEquals(false, result);
    }

    @Test
    public void getDates_correctlyFormatsDates() throws TaskManagerException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        assertEquals("from: Monday, Mar 10 2025, to: Wednesday, Mar 12 2025", event.getDates());
    }

    @Test
    public void constructor_withIsDone_setsCorrectStatus() throws TaskManagerException {
        Event event = new Event("Hackathon",
                LocalDateTime.parse("15/4/2025 0900", INPUT_FORMATTER),
                LocalDateTime.parse("15/4/2025 1700", INPUT_FORMATTER),
                true);
        assertEquals("[E][X] Hackathon (from: tuesday, apr 15 2025, 9:00am to: tuesday, apr 15 2025, 5:00pm)",
                event.toString());
    }

    @Test
    public void toString_correctFormat() throws TaskManagerException {
        Event event = new Event("Workshop",
                LocalDateTime.parse("22/6/2025 1300", INPUT_FORMATTER),
                LocalDateTime.parse("22/6/2025 1500", INPUT_FORMATTER));
        assertEquals("[E][ ] Workshop (from: sunday, jun 22 2025, 1:00pm to: sunday, jun 22 2025, 3:00pm)",
                event.toString());
    }

    @Test
    public void equals_sameEvent_returnsTrue() throws TaskManagerException {
        Event e1 = new Event("Symposium",
                LocalDateTime.parse("1/5/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("1/5/2025 1200", INPUT_FORMATTER));
        Event e2 = new Event("Symposium",
                LocalDateTime.parse("1/5/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("1/5/2025 1200", INPUT_FORMATTER));
        assertEquals(true, e1.equals(e2));
    }

    @Test
    public void equals_differentTime_returnsFalse() throws TaskManagerException {
        Event e1 = new Event("Webinar",
                LocalDateTime.parse("1/5/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("1/5/2025 1200", INPUT_FORMATTER));
        Event e2 = new Event("Webinar",
                LocalDateTime.parse("1/5/2025 1300", INPUT_FORMATTER),
                LocalDateTime.parse("1/5/2025 1500", INPUT_FORMATTER));
        assertEquals(false, e1.equals(e2));
    }

    @Test
    public void includesDate_sameStartAndEnd_returnsTrue() throws TaskManagerException {
        Event event = new Event("One-day Event",
                LocalDateTime.parse("10/10/2025 0900", INPUT_FORMATTER),
                LocalDateTime.parse("10/10/2025 1700", INPUT_FORMATTER));

        assertEquals(true, event.includesDate(LocalDate.parse("10/10/2025", DateTimeFormatter.ofPattern("d/M/yyyy"))));
    }
}

