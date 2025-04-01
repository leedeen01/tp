package seedu.address.tasklist.commands;

import java.util.ArrayList;

import seedu.address.tasklist.tasks.Task;

/**
 * Handles displaying all tasks in the list.
 */
public class ListCommand {
    /**
     * Displays all tasks in the list.
     *
     * @param taskList The list of tasks.
     * @return A formatted list of tasks or a message if the list is empty.
     */
    public static String execute(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "You have no tasks at the moment.";
        }
        StringBuilder fullList = new StringBuilder("Here is the to-do list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            fullList.append(String.format("%d. %s\n", i + 1, taskList.get(i).toString()));

        }
        return fullList.toString().trim();
    }
}

