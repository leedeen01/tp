package seedu.address.tasklist.main;

import javafx.application.Application;

/**
 * This class serves as the entry point to the TaskList application.
 * It is responsible for launching the JavaFX graphical user interface (GUI).
 */
public class Launcher {
    /**
     * The main method that starts the TaskList application.
     * This method delegates to JavaFX to initialize the UI.
     *
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) { //entry point to Java app
        Application.launch(seedu.address.tasklist.taskui.TaskListWindow.class, args);
    }
}
