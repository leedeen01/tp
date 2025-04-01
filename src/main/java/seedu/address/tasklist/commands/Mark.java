package seedu.address.tasklist.commands;

import java.util.ArrayList;

import seedu.address.tasklist.exception.TaskListException;
import seedu.address.tasklist.tasks.Task;

/**
 * Handles marking a task as completed in the task list.
 */
public class Mark {
    /**
     * Marks a task as completed based on the provided index.
     *
     * @param index The command containing the task index.
     * @param taskList The list of tasks.
     * @return A success message after marking the task.
     * @throws TaskListException If the index is invalid or the task is already marked.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws TaskListException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new TaskListException("You need to pick an index from the existing task list.");
            }

            Task currTask = taskList.get(taskIndex);

            // Check if the task is already marked
            if (currTask.status().equals("X")) {
                return "The task is already marked.";
            } else {
                // Mark the task and return success message
                currTask.mark();

                return "Task marked as done:\n  " + currTask;
            }

        } catch (NumberFormatException e) {
            throw new TaskListException("You need to pick an index number to mark in the list.");
        }
    }
}

