package seedu.address.tasklist.commands;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * Handles the application exit logic.
 */
public class Exit {

    /**
     * Returns an exit message and schedules the application to close after 2 seconds.
     *
     * @return A farewell message to the user.
     */
    public static String execute() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();

        return "Exiting Task Manager...";
    }
}
