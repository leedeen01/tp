package seedu.address.tasklist.commands;

import java.util.ArrayList;

import seedu.address.tasklist.exception.TaskListException;
import seedu.address.tasklist.tasks.Task;


/**
 * Handles deleting a task from the task list.
 */
public class DeleteTask {
    /**
     * Deletes a task from the list based on the provided index.
     *
     * @param index The command containing the task index.
     * @param taskList The list of tasks.
     * @return A success message after deletion.
     * @throws TaskListException If the index is invalid.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws TaskListException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new TaskListException("You need to pick an index from the existing task list.");
            }
            Task currTask = taskList.get(taskIndex);
            taskList.remove(taskIndex);
            if (taskList.size() == 1) {
                return "Phew! We got rid of " + currTask
                        + "\nNow you only have 1 task to worry about.";
            }
            return "Phew! We got rid of " + currTask
                    + "\nNow you have " + taskList.size() + " tasks to worry about.";
        } catch (NumberFormatException e) {
            throw new TaskListException("You need to pick a single index number "
                    + "to delete from the list. You can try again.");
        }
    }
}
