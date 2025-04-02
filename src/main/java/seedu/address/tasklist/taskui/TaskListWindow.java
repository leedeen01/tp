package seedu.address.tasklist.taskui;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.tasklist.exception.TaskManagerException;
import seedu.address.tasklist.main.TaskManager;

/**
 * MainWindow initializes and manages the graphical user interface (GUI) for TaskList.
 * It extends the JavaFX Application class and handles user interactions.
 */
public class TaskListWindow extends Application {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image chatImage = new Image(this.getClass().getResourceAsStream("/images/chat.png"));

    private TaskManager taskManager;

    /**
     * Starts the JavaFX application, setting up the primary stage and loading the UI components.
     *
     * @param stage The primary stage for the application.
     * @throws TaskManagerException If there is an error initializing TaskList.
     */
    @Override
    //overriding start method from Application
    public void start(Stage stage) throws TaskManagerException {
        this.taskManager = new TaskManager();

        try {
            // Load FXML file for UI layout - read by fxmlloader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TaskListWindow.fxml"));
            //attach anchor pane to window (Stage) using Scene.
            AnchorPane ap = fxmlLoader.load(); //Load UI components into AnchorPane
            TaskListWindow controller = fxmlLoader.getController();
            controller.setTaskManager(this.taskManager); // Pass initialized taskList to controller
            Scene scene = new Scene(ap); //scene = window content

            // Set up the stage (window)
            stage.setTitle("ClientNest - Task Manager");
            stage.setScene(scene);

            //Set  initial window size
            stage.setWidth(400);
            stage.setHeight(600);

            // define minimum window size when resizing
            stage.setMinWidth(400);
            stage.setMinHeight(600);
            //makes main UI (AnchorPane) always stretch to match scene width/height
            ap.prefWidthProperty().bind(scene.widthProperty());
            ap.prefHeightProperty().bind(scene.heightProperty());

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the UI components and sets up event listeners.
     */
    @FXML
    public void initialize() {
        // Make sure ScrollPane resizes with window
        scrollPane.fitToWidthProperty().set(true);

        // Auto-scroll as new messages appear
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        dialogContainer.getChildren().add(DialogBox.getTaskListDialog(Ui.showWelcomeMessage(), chatImage));

        // Ensure user input field expands properly
        AnchorPane.setLeftAnchor(userInput, 10.0);
        AnchorPane.setRightAnchor(userInput, 80.0);
        AnchorPane.setBottomAnchor(userInput, 10.0);

        // Ensure send button stays at the right
        AnchorPane.setRightAnchor(sendButton, 10.0);
        AnchorPane.setBottomAnchor(sendButton, 10.0);

        // Add key event listener for Enter key
        userInput.setOnAction(event -> handleUserInput());
    }

    /**
     * Handles user input and generates a response from TaskList.
     * Displays the conversation in the UI.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText(); // Get user's input from text field
        String response = taskManager.getResponse(input); // Get response from TaskList logic

        // Display user input and TaskList's response in the dialogContainer
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTaskListDialog(response, chatImage)
        );
        userInput.clear(); // Clear input field after sending msg

        if (input.equalsIgnoreCase("exit")) {
            // Delay closing the window for 1.5 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    /**
     * Sets the TaskList instance for the UI.
     *
     * @param taskManager The TaskManager instance.
     */
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
}
