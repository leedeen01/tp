package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        phoneLabel.setText("Phone: " + userProfile.getPhone().value);
        emailLabel.setText("Email: " + userProfile.getEmail().value);
    }
}
