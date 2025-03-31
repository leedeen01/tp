package seedu.address.tasklist.exception;

/**
 * Represents a custom exception specific to the PiggyPlanner application.
 * This exception is thrown when an error occurs due to invalid user input
 * or any other domain-specific issues within the application.
 */
public class TaskListException extends Exception {
    /**
     * Constructs a new PiggyException with the specified message giving detail about the nature of the source of error.
     *
     * @param message The error message describing the exception.
     */
    public TaskListException(String message) {
        super(message); // Pass error message to Exception class
    }
}
