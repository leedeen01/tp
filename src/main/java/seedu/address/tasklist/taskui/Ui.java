package seedu.address.tasklist.taskui;

/**
 * Handles user interface interactions by displaying messages.
 */
public class Ui {
    /**
     * Displays the welcome message when the application starts.
     */
    public static String showWelcomeMessage() {
        return "Welcome to the Task List.\n\nEnter some tasks to get started.";
    }

    /**
     * Displays the exit message when the application is terminated.
     */
    public static String showExitMessage() {
        return "Exiting Task List ...";
    }

    /**
     * Displays a given message within a formatted separator.
     *
     * @param message The message to be displayed.
     */
    public static void showMessage(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
}
