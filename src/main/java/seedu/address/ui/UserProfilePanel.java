package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.user.UserProfile;

/**
 * Panel containing the user profile information.
 */
public class UserProfilePanel extends UiPart<Region> {

    private static final String FXML = "UserProfilePanel.fxml";

    @FXML
    private VBox userProfileContainer;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    private final UserProfile userProfile;

    /**
     * Creates a {@code UserProfilePanel} with the given model.
     */
    public UserProfilePanel(UserProfile userProfile) {
        super(FXML);
        this.userProfile = userProfile;
        initializeUserProfile();
    }

    /**
     * Initializes and sets user profile data in the UI.
     */
    private void initializeUserProfile() {
        nameLabel.setText("Welcome, " + userProfile.getName().fullName);
        nameLabel.setMinWidth(500);
        nameLabel.setWrapText(false);
        nameLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        phoneLabel.setText("Phone: " + userProfile.getPhone().value);
        phoneLabel.setMinWidth(250);
        phoneLabel.setWrapText(false);
        phoneLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        emailLabel.setText("Email: " + userProfile.getEmail().value);
        emailLabel.setMinWidth(250);
        emailLabel.setWrapText(false);
        emailLabel.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
    }
}
