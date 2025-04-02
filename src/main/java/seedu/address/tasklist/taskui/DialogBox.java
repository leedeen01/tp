package seedu.address.tasklist.taskui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


/**
 * DialogBox represents a message bubble for the user and TaskManager.
 * It extends HBox and allows messages to be displayed with an image.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    /**
     * Constructs a DialogBox with a specified message and image.
     *
     * @param message The text to display.
     * @param img The image to display.
     */
    public DialogBox(String message, Image img) {
        text = new Label(message);
        text.setWrapText(true); //text wrapping
        text.setMaxWidth(320); //limit width - prevent overflow
        displayPicture = new ImageView(img);

        // Set size and style
        displayPicture.setFitWidth(50);
        displayPicture.setFitHeight(50);
        displayPicture.setPreserveRatio(true);
        text.setStyle("-fx-background-color: #6FE2D2; -fx-padding: 10; -fx-border-radius: 10; "
                + "-fx-background-radius: 10;");

        // Add timestamp (current time)
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        Label timestampLabel = new Label(timestamp);
        timestampLabel.setStyle("-fx-text-fill: grey; -fx-font-size: 10;");

        // Group message + timestamp vertically
        VBox messageWithTimestamp = new VBox(text, timestampLabel);
        messageWithTimestamp.setSpacing(2);
        // Create a circular clip for the image
        Rectangle clip = new Rectangle(50, 50);
        clip.setArcWidth(20); // controls roundness
        clip.setArcHeight(20);
        displayPicture.setClip(clip);

        this.setAlignment(Pos.TOP_RIGHT); // Default alignment for user
        this.setSpacing(2);
        this.setMaxWidth(Double.MAX_VALUE); //Allow the dialog box to take full width

        this.getChildren().addAll(messageWithTimestamp, displayPicture); // Add message, image and timestamp
    }
    /**
     * Flips the dialog box alignment to the left, for TaskManager responses.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        //Converts curr elements (text, images) in dialog box into list to manipulate
        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());
        //reverses the order of elements
        FXCollections.reverse(nodes);
        //update dialog box
        this.getChildren().setAll(nodes);
    }

    /**
     * Creates a DialogBox for user messages.
     *
     * @param message The user’s message.
     * @param userImage The user’s profile image.
     * @return A DialogBox aligned to the right.
     */
    public static DialogBox getUserDialog(String message, Image userImage) {
        DialogBox dialogBox = new DialogBox(message, userImage);
        dialogBox.setAlignment(Pos.TOP_RIGHT); //Ensure alignment to the right
        dialogBox.text.setStyle("-fx-background-color: #FB7749; -fx-text-fill: #EEEEEE; -fx-padding: 10; "
                + "-fx-border-radius: 10; -fx-background-radius: 10;");
        return dialogBox;
    }

    /**
     * Creates a DialogBox for TaskManager responses, with flipped alignment.
     *
     * @param message The TaskManager’s message.
     * @param chatImage The TaskManager’s profile image.
     * @return A DialogBox aligned to the left.
     */
    public static DialogBox getTaskManagerDialog(String message, Image chatImage) {
        DialogBox dialogBox = new DialogBox(message, chatImage);
        dialogBox.flip();
        dialogBox.text.setStyle("-fx-background-color: #684BFE; -fx-text-fill: #EEEEEE; -fx-padding: 10;"
                + " -fx-border-radius: 10; -fx-background-radius: 10;");
        return dialogBox;
    }
}
