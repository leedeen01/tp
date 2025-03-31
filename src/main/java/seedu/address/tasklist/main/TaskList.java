package seedu.address.tasklist.main;

import java.util.ArrayList;
import java.util.Scanner;

import seedu.address.tasklist.commands.AddTask;
import seedu.address.tasklist.commands.CommandType;
import seedu.address.tasklist.commands.DayPlan;
import seedu.address.tasklist.commands.DeleteTask;
import seedu.address.tasklist.commands.Find;
import seedu.address.tasklist.commands.Help;
import seedu.address.tasklist.commands.ListCommand;
import seedu.address.tasklist.commands.Mark;
import seedu.address.tasklist.commands.Unmark;
import seedu.address.tasklist.exception.TaskListException;
import seedu.address.tasklist.storage.TaskStorage;
import seedu.address.tasklist.tasks.Task;
import seedu.address.tasklist.ui.Ui;


/**
 * The main class for the PiggyPlanner application.
 * This class validates and processes commands.
 */
public class TaskList {
    private final ArrayList<Task> taskList;
    private final Scanner reader;

    /**
     * Constructs a new PiggyPlanner instance.
     * Initializes the task list by loading stored tasks and sets up a scanner for user input.
     *
     * @throws TaskListException if there is an error loading the stored tasks.
     */
    public TaskList() throws TaskListException {
        this.taskList = TaskStorage.loadList();
        this.reader = new Scanner(System.in);
    }

    /**
     * Processes the user's command and returns the result.
     *
     * @param userInput The input command string from the user.
     * @return The result message after executing the command.
     * @throws TaskListException If an invalid command or incorrect arguments are provided.
     */
    private String processCommand(String userInput) throws TaskListException {
        assert userInput != null : "User input should never be null";
        assert !userInput.trim().isEmpty() : "User input should never be empty";
        assert taskList != null : "Task list should not be null when processing a command";

        String[] inputParts = userInput.trim().split(" ", 2); // Split: command, arguments
        CommandType command = CommandType.fromString(inputParts[0]);
        assert command != null : "Command type should never be null in processCommand()";
        validateArguments(command, inputParts);

        switch (command) {
        case LIST:
            return ListCommand.execute(taskList);

        case MARK:
            assert inputParts.length == 2 : command + " should have an argument";
            String markResponse = Mark.execute(userInput, taskList);
            TaskStorage.updateList(taskList);
            return markResponse;

        case UNMARK:
            assert inputParts.length == 2 : command + " should have an argument";
            String unmarkResponse = Unmark.execute(userInput, taskList);
            TaskStorage.updateList(taskList);
            return unmarkResponse;

        case TODO:
            String todoResponse = AddTask.todo(userInput, taskList);
            TaskStorage.updateList(taskList);
            return todoResponse;

        case DEADLINE:
            String deadlineResponse = AddTask.deadline(userInput, taskList);
            TaskStorage.updateList(taskList);
            return deadlineResponse;

        case EVENT:
            String eventResponse = AddTask.event(userInput, taskList);
            TaskStorage.updateList(taskList);
            return eventResponse;

        case DELETE:
            assert inputParts.length == 2 : command + " should have an argument";
            String deleteResponse = DeleteTask.execute(userInput, taskList);
            TaskStorage.updateList(taskList);
            return deleteResponse;

        case FIND:
            assert inputParts.length >= 2 : command + " should have an argument";
            return Find.execute(userInput, taskList);

        case DAYPLAN:
            assert inputParts.length >= 2 : command + " should have an argument";
            return DayPlan.execute(userInput, taskList);
        case HELP:
            return Help.execute();
        case EXIT:
            //Platform.exit();
            return "Goodbye!";


        case UNKNOWN:
        default:
            assert false : "processCommand should never receive an UNKNOWN command";
            throw new TaskListException("Unknown command.");
        }
    }

    /**
     * Handles user input from the GUI and returns the response to be displayed.
     *
     * @param input The user's input string.
     * @return The response string from TaskList.
     */
    public String getResponse(String input) {
        try {
            return processCommand(input);
        } catch (TaskListException e) {
            return e.getMessage(); // Return error messages to GUI
        }
    }

    /**
     * Runs the TaskList application in the console.
     * Continuously listens for user commands until the user exits.
     */
    public void run() {
        Ui.showWelcomeMessage();

        while (true) {
            String userInput = reader.nextLine(); // Get user input from console

            assert userInput != null : "User input should never be null when reading from console";

            try {
                String response = processCommand(userInput);
                Ui.showMessage(response);

                if (CommandType.fromString(userInput.split(" ")[0]) == CommandType.EXIT) {
                    Ui.showExitMessage();
                    return; // Exit
                }

            } catch (TaskListException e) {
                Ui.showMessage(e.getMessage());
            }
        }
    }
    /**
     * Validates the arguments provided for a given command.
     * Ensures the user input meets the expected format for each command.
     *
     * @param command The command type provided by the user.
     * @param inputParts The split user input containing command and arguments.
     * @throws TaskListException if the provided arguments are incorrect or missing.
     */
    private void validateArguments(CommandType command, String[] inputParts) throws TaskListException {
        assert command != null : "Command type should never be null in validateArguments";
        int argLength = inputParts.length; // Check number of arguments

        switch (command) {
        case LIST:
        case EXIT:
            assert argLength == 1 : "LIST and EXIT commands should not have arguments";
            break;

        case MARK:
        case UNMARK:
        case DELETE:
            assert argLength == 2 : "MARK, UNMARK, and DELETE commands require exactly one argument";
            break;
        case FIND:
            assert argLength >= 2 : "FIND command should have at least one keyword";
            break;

        case TODO:
            assert argLength == 2 && !inputParts[1].trim().isEmpty()
                    : "TODO command must have a valid task description";
            break;

        case DEADLINE:
            assert argLength >= 2 && inputParts[1].contains("/by") : "DEADLINE command must have a valid date format";
            break;

        case EVENT:
            assert argLength >= 2 && inputParts[1].contains("/from") && inputParts[1].contains("/to")
                    : "EVENT command must have a valid start and end time";
            break;

        case DAYPLAN:
            assert argLength == 2 : "DAYPLAN command requires a date argument";
            break;

        case UNKNOWN:
        default:
            assert false : "validateArguments should never receive an UNKNOWN command";
        }
    }

    /**
     * The entry point for the PiggyPlanner application.
     * Initializes and runs the application.
     *
     * @param args Command-line arguments (not used yet).
     */
    public static void main(String[] args) {
        try {
            new TaskList().run();
        } catch (TaskListException e) {
            System.out.println("An error occurred while starting the program: " + e.getMessage());
        }
    }
}
