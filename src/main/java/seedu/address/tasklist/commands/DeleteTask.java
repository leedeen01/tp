package seedu.address.tasklist.commands;

import java.util.ArrayList;

import seedu.address.tasklist.exception.TaskManagerException;
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
     * @throws TaskManagerException If the index is invalid.
     */
    public static String execute(String index, ArrayList<Task> taskList) throws TaskManagerException {
        try {
            int taskIndex = Integer.parseInt(index.split(" ")[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskList.size()) {
                throw new TaskManagerException("You need to pick an index from the existing task list.");
            }
            Task currTask = taskList.get(taskIndex);
            taskList.remove(taskIndex);
            return "Deleting " + currTask;
        } catch (NumberFormatException e) {
            throw new TaskManagerException("You need to pick a single index number "
                    + "to delete from the list. You can try again.");
        }
    }
}
