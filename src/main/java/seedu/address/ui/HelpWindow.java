package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103-f10-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

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

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    private void populateCommandSummary() {
        String[] actions = {
            "Add", "Clear", "Delete", "Edit", "Find", "List", "Help", "Add Policy", "List Policy", "Find Policy",
            "Edit Policy", "Delete Policy", "Add Premium", "Edit Premium", "Delete Premium", "Profile"
        };

        String[] formats = {
            "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY"
                    + " [pr/PREMIUM_NAME PREMIUM_AMOUNT] [t/TAG]…*\n"
                    + "e.g., add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, "
                    + "1234665 b/2002-11-24 pr/vivo360 1000 t/friend t/colleague",
            "clear",
            "delete INDEX\n"
                    + "e.g., delete 3",
            "edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY] "
                    + "[pr/PREMIUM_NAME PREMIUM_AMOUNT] [t/TAG]…*\n"
                    + "e.g., edit 2 n/James Lee e/jameslee@example.com b/2001-02-13",
            "find KEYWORD [MORE_KEYWORDS]\n"
                    + "e.g., find James Jake",
            "list",
            "help",
            "addpolicy pn/POLICY_NUMBER n/PREMIUM_NAME pc/PROVIDER_COMPANY pl/POLICY_LINK\n"
                    + "e.g., addpolicy pn/POL123 n/LifeShield pc/ShieldCorp pl/https://www.shieldcorp.com/policy123\n",
            "listpolicy",
            "findpolicy KEYWORD_IN_NAME [MORE KEYWORD_IN_NAME]\n"
                    + "e.g., findpolicy Life",
            "editpolicy INDEX [n/ POLICY_NAME] [pn/ POLICY_NUMBER] [pc/ PROVIDER_COMPANY] [pl/ POLICY_LINK]\n"
                    + "e.g., editpolicy 1 n/Life Shield pl/https://www.lifeshield.com",
            "deletepolicy INDEX\n"
                    + "e.g., deletepolicy 3",
            "addpr INDEX pr/ PREMIUM_NAME PREMIUM_AMOUNT\n"
                    + "e.g., addpr 1 pr/FamilyPlus 500",
            "editpr INDEX pr/ PREMIUM_NAME PREMIUM_AMOUNT\n"
                    + "e.g., editpr 1 pr/LifeShield 300",
            "deletepr INDEX pr/PREMIUM_NAME\n"
                    + "e.g., deletepr 3 pr/LifeShield",
            "profile [n/USER_NAME] [e/USER_EMAIL] [p/USER_PHONE]\n"
                    + "e.g., profile n/James Ho e/jamesho@example.com p/22224444",
        };

        // Set up the table columns to use array elements as values
        actionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        formatColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));

        commandTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double totalWidth = newValue.doubleValue();
            double actionWidth = totalWidth * 0.11;
            double formatWidth = totalWidth * 0.87;

            actionColumn.setPrefWidth(actionWidth);
            actionColumn.setResizable(false);

            formatColumn.setPrefWidth(formatWidth);
            formatColumn.setResizable(false);
        });

        // Populate the table with the action and format pairs
        for (int i = 0; i < actions.length; i++) {
            commandTable.getItems().add(new String[]{actions[i], formats[i]});
        }
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException
     *                               <ul>
     *                               <li>
     *                               if this method is called on a thread other than
     *                               the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or
     *                               layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
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
