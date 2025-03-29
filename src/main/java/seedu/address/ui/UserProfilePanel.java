package seedu.address.ui;

import java.nio.file.Paths;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;

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
