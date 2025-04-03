package seedu.address.tasklist.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Deadline;
import seedu.address.tasklist.tasks.Event;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.tasks.ToDo;


/**
 * Handles adding different types of tasks (ToDo, Deadline, Event) to the task list.
 */
public class AddTask {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Adds a ToDo type task to the task list.
     *
     * @param userInput The full user command containing the task description.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws TaskManagerException If the task description is missing.
     */
    public static String todo(String userInput, ArrayList<Task> taskList) throws TaskManagerException {
        assert userInput != null : "User input should never be null in AddTask.todo()";
        assert taskList != null : "Task list should never be null in AddTask.todo()";

        if (userInput.length() <= 5) {
            throw new TaskManagerException("No task entered.");
        }
        String taskName = userInput.substring(5).trim();
        validateNonEmpty(taskName, "No task entered.");
        Task newTask = new ToDo(taskName);
        if (isDuplicate(newTask, taskList)) {
            throw new TaskManagerException("This task already exists in your list.");
        }
        taskList.add(newTask);

        assert taskList.contains(newTask) : "Task should be successfully added to taskList in AddTask.todo()";
        return taskAddedMsg(newTask, taskList.size());
    }

    /**
     * Adds a Deadline type task to the task manager.
     *
     * @param userInput The full user command containing the task and due date.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws TaskManagerException If the task description or deadline is missing or incorrectly formatted.
     */
    public static String deadline(String userInput, ArrayList<Task> taskList) throws TaskManagerException {
        assert userInput != null : "User input should never be null in AddTask.deadline()";
        assert taskList != null : "Task list should never be null in AddTask.deadline()";
        if (!userInput.contains("/by")) {
            throw new TaskManagerException("No due-date entered.");
        }
        int dueDateIndex = userInput.indexOf("/by");
        String taskName = userInput.substring(9, dueDateIndex).trim();
        String dueDateStr = userInput.substring(dueDateIndex + 4).trim();
        validateNonEmpty(taskName, "No task entered.");
        validateNonEmpty(dueDateStr, "No due-date entered.");

        LocalDateTime dueDate;
        try {
            dueDate = LocalDateTime.parse(dueDateStr, INPUT_FORMATTER);
        } catch (Exception e) {
            throw new TaskManagerException("Invalid date format. Try again and use:"
                    + " d/M/yyyy HHmm (e.g., 2/12/2019 1800).");
        }

        Task newTask = new Deadline(taskName, dueDate);
        if (isDuplicate(newTask, taskList)) {
            throw new TaskManagerException("This deadline already exists in your list.");
        }
        taskList.add(newTask);
        assert taskList.contains(newTask) : "Task should be successfully added to taskList in AddTask.deadline()";
        return taskAddedMsg(newTask, taskList.size());
    }

    /**
     * Adds an Event type task to the task manager.
     *
     * @param userInput The full user command containing the task, start time, and end time.
     * @param taskList The list where the new task will be added.
     * @return A success message confirming the addition.
     * @throws TaskManagerException If the task description, start or enf time, is missing or wrong format.
     */
    public static String event(String userInput, ArrayList<Task> taskList) throws TaskManagerException {
        assert userInput != null : "User input should never be null in AddTask.event()";
        assert taskList != null : "Task list should never be null in AddTask.event()";
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new TaskManagerException("Event start/end not entered.");
        }
        int fromIndex = userInput.indexOf("/from");
        int toIndex = userInput.indexOf("/to");
        String taskName = userInput.substring(6, fromIndex).trim();
        String startTimeStr = userInput.substring(fromIndex + 6, toIndex).trim();
        String endTimeStr = userInput.substring(toIndex + 4).trim();
        validateNonEmpty(taskName, "No event entered.");
        validateNonEmpty(startTimeStr, "Event start not entered.");
        validateNonEmpty(endTimeStr, "Event end not entered.");

        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(startTimeStr, INPUT_FORMATTER);
            endTime = LocalDateTime.parse(endTimeStr, INPUT_FORMATTER);
        } catch (Exception e) {
            throw new TaskManagerException("Invalid date format. Try again and use:"
                    + " d/M/yyyy HHmm (e.g., 2/12/2019 1800)."
                    + "\nOr check that the start time is before the end time.");
        }

        if (!startTime.isBefore(endTime)) {
            throw new TaskManagerException("Event start time must be before end time.");
        }

        Task newTask = new Event(taskName, startTime, endTime);
        if (isDuplicate(newTask, taskList)) {
            throw new TaskManagerException("This event already exists in your list.");
        }
        taskList.add(newTask);
        assert taskList.contains(newTask) : "Task should be successfully added to taskList in AddTask.event()";
        return taskAddedMsg(newTask, taskList.size());
    }

    /**
     * Validates that the given input is not empty.
     *
     * @param input The input string to validate.
     * @param errorMessage The error message to display if validation fails.
     * @throws TaskManagerException If the input is empty or null.
     */
    private static void validateNonEmpty(String input, String errorMessage) throws TaskManagerException {
        if (input == null || input.trim().isEmpty()) {
            throw new TaskManagerException(errorMessage);
        }
    }

    /**
     * Constructs a formatted success message when a task is added.
     *
     * @param task The newly added task.
     * @param totalTasks The total number of tasks in the list.
     * @return A formatted success message.
     */
    private static String taskAddedMsg(Task task, int totalTasks) {
        // Create the message based on the total number of tasks
        String taskCountMessage;
        if (totalTasks == 1) {
            taskCountMessage = "1 task in the list.";
        } else {
            taskCountMessage = totalTasks + " tasks in the list.";
        }
        // Return the final success message
        return "New task added.\n " + task + "\n" + taskCountMessage;
    }

    /**
     * Checks if a task with the same description and relevant time details
     * (if applicable) already exists in the provided task list.
     *
     * @param newTask  The task to check for duplicates.
     * @param taskList The list of tasks to search for duplicates.
     * @return true if a task with the same details exists in the list; false otherwise.
     */
    private static boolean isDuplicate(Task newTask, ArrayList<Task> taskList) {
        for (Task task : taskList) {
            if (task.equals(newTask)) {
                return true;
            }
        }
        return false;
    }
}


