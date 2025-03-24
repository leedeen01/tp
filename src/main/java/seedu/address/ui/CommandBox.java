package seedu.address.ui;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int HISTORY_LIMIT = 5;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    private final LinkedList<String> commandHistory = new LinkedList<>();
    private int historyPointer = -1;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // Add event handler for arrow keys
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                navigateHistory(-1); // Up key pressed, go to previous command
            } else if (event.getCode() == KeyCode.DOWN) {
                navigateHistory(1); // Down key pressed, go to next command
            }
        });
    }

    /**
     * Navigates through the command history.
     * @param direction -1 for up, 1 for down
     */
    private void navigateHistory(int direction) {
        historyPointer += direction;

        if (historyPointer < 0) {
            historyPointer = 0;
        } else if (historyPointer >= commandHistory.size()) {
            historyPointer = commandHistory.size() - 1;
        }

        if (!commandHistory.isEmpty() && historyPointer >= 0 && historyPointer < commandHistory.size()) {
            commandTextField.setText(commandHistory.get(historyPointer));
            commandTextField.positionCaret(commandTextField.getText().length());
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            addToHistory(commandText);
            commandTextField.setText("");
            historyPointer = commandHistory.size();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Adds a command to the history list, keeping the list size within the limit.
     */
    public void addToHistory(String commandText) {
        if (commandHistory.size() >= HISTORY_LIMIT) {
            commandHistory.removeFirst();
        }
        commandHistory.addLast(commandText);
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
