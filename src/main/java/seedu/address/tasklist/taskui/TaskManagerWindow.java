package seedu.address.tasklist.taskui;

import java.io.IOException;

import javafx.animation.PauseTransition;
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
public class TaskManagerWindow {
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
    private Stage taskManagerStage;

    /**
     * Launches the Task Manager window as a standalone JavaFX Stage.
     * Loads the TaskManagerWindow FXML layout, sets up the scene and window properties,
     * and shows the window. Also initializes the controller with a new TaskManager instance
     * and provides the Stage reference so it can be closed later.
     *
     * Catches and prints any IOException from FXML loading and TaskManagerException during setup.
     */
    public static void launchTaskManagerWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerWindow.class.getResource("/view/TaskManagerWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Stage taskStage = new Stage();
            Scene scene = new Scene(ap);
            taskStage.setScene(scene);
            taskStage.setTitle("ClientNest - Task Manager");
            taskStage.setWidth(400);
            taskStage.setHeight(600);
            taskStage.setMinWidth(400);
            taskStage.setMinHeight(600);

            ap.prefWidthProperty().bind(scene.widthProperty());
            ap.prefHeightProperty().bind(scene.heightProperty());

            TaskManagerWindow controller = fxmlLoader.getController();
            try {
                controller.setTaskManager(new TaskManager());
            } catch (TaskManagerException e) {
                e.printStackTrace();
            }
            controller.setStage(taskStage);

            taskStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Stage for this Task Manager window.
     * This allows the controller to later close the window programmatically.
     *
     * @param stage The JavaFX Stage to be associated with this window.
     */
    public void setStage(Stage stage) {
        this.taskManagerStage = stage;
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
        dialogContainer.getChildren().add(DialogBox.getTaskManagerDialog(Ui.showWelcomeMessage(), chatImage));

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
        String response = taskManager.getResponse(input); // Get response from TaskManager logic

        // Display user input and TaskManager's response in the dialogContainer
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTaskManagerDialog(response, chatImage)
        );
        userInput.clear(); // Clear input field after sending msg

        if (input.equalsIgnoreCase("exit")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> taskManagerStage.close());
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
