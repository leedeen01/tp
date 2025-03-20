package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.policy.Policy;

/**
 * An UI component that displays information of a {@code Policy}.
 */
public class PolicyCard extends UiPart<Region> {

    private static final String FXML = "PolicyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Policy policy;

    @FXML
    private HBox cardPane;
    @FXML
    private Label policyName;
    @FXML
    private Label policyId;
    @FXML
    private Label policyNumber;
    @FXML
    private Label providerCompany;
    @FXML
    private Hyperlink policyLink;

    /**
     * Creates a {@code PolicyCard} with the given {@code Policy} and index to
     * display.
     */
    public PolicyCard(Policy policy, int displayedIndex) {
        super(FXML);
        this.policy = policy;
        policyId.setText(displayedIndex + ". ");
        policyName.setText(policy.getPolicyName().policyName);
        policyNumber.setText(policy.getPolicyNumber().value);
        providerCompany.setText(policy.getProviderCompany().value);
        policyLink.setText(policy.getPolicyLink().value);
        // Set an action to copy the URL to the clipboard when clicked
        policyLink.setOnAction(event -> {
            // Obtain the system clipboard
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            // Put the URL string into the clipboard content
            content.putString(policyLink.getText());
            // Set the clipboard content
            clipboard.setContent(content);
            System.out.println("URL copied to clipboard: " + policyLink.getText());
        });
    }
}
