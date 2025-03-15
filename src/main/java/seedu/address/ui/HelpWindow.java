package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    public static final String COMMAND_SUMMARY = "Command Summary:\n" +
            "Add: add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​\n" +
            "e.g., add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague\n\n" +
            "Clear: clear\n\n" +
            "Delete: delete INDEX\n" +
            "e.g., delete 3\n\n" +
            "Edit: edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​\n" +
            "e.g., edit 2 n/James Lee e/jameslee@example.com\n\n" +
            "Find: find KEYWORD [MORE_KEYWORDS]\n" +
            "e.g., find James Jake\n\n" +
            "List: list\n\n" +
            "Help: help";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<String[]> commandTable;

    @FXML
    private TableColumn<String[], String> actionColumn;

    @FXML
    private TableColumn<String[], String> formatColumn;
    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        populateCommandSummary();
    }

    private void populateCommandSummary() {
        String[] actions = {
                "Add", "Clear", "Delete", "Edit", "Find", "List", "Help"
        };

        String[] formats = {
                "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY pr/PREMIUM_NAME PREMIUM_AMOUNT [t/TAG]…*\n" +
                        "e.g., add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 b/2002-11-24 pr/vivo360 1000 t/friend t/colleague",

                "clear",

                "delete INDEX\n" +
                        "e.g., delete 3",

                "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY] [t/TAG]…*\n" +
                        "e.g., edit 2 n/James Lee e/jameslee@example.com b/2001-02-13",

                "find KEYWORD [MORE_KEYWORDS]\n" +
                        "e.g., find James Jake",

                "list",

                "help"
        };

        // Set up the table columns to use array elements as values
        actionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        formatColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));

        // Populate the table with the action and format pairs
        for (int i = 0; i < actions.length; i++) {
            commandTable.getItems().add(new String[]{actions[i], formats[i]});
        }
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
