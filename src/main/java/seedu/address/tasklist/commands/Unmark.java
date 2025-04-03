package seedu.address.tasklist.commands;

import java.util.ArrayList;

import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.tasks.Task;

/**
 * Handles unmarking a task in the task list.
 */
public class Unmark {
    /**
     * Unmarks a task, marking it as incomplete, based on the provided index.
     *
     * @param index The command containing the task index.
     * @param taskList The list of tasks.
     * @return A success message after unmarking the task.
     * @throws TaskManagerException If the index is invalid or the task is already unmarked.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws TaskManagerException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new TaskManagerException("You need to pick an index from the existing task list.");
            }

            Task currTask = taskList.get(taskIndex);

            // Check if the task is already marked
            if (currTask.status().equals(" ")) {
                return "The task is already unmarked.";
            } else {
                // Mark the task and return success message
                currTask.unmark();
                return "Task unmarked:\n  " + currTask;
            }

        } catch (NumberFormatException e) {
            throw new TaskManagerException("You need to pick an integer (e.g. 1) from the existing task list.");
        }
    }
}
