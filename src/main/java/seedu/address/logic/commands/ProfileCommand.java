package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_PHONE;

import java.io.IOException;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Views and edits current profile.
 */
public class ProfileCommand extends Command {

    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit your profile information.\n"
            + "Parameters: "
            + "[" + PREFIX_USER_NAME + "NAME] "
            + "[" + PREFIX_USER_PHONE + "PHONE] "
            + "[" + PREFIX_USER_EMAIL + "EMAIL]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USER_NAME + "John Doe "
            + PREFIX_USER_PHONE + "98765432 "
            + PREFIX_USER_EMAIL + "johnd@example.com";

    final UserProfile updatedProfile;
    /**
     * Creates a ProfileCommand to update the specified {@code UserProfile}
     */
    public ProfileCommand(UserProfile userProfile) {
        this.updatedProfile = userProfile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UserProfile currentProfile = model.getUserProfile();
        model.setUserProfile(currentProfile, updatedProfile);

        // Save the updated profile to storage
        try {
            model.saveUserProfile(updatedProfile);
        } catch (IOException e) {
            throw new CommandException("Failed to save profile: " + e.getMessage());
        }

        String messageProfileEditSuccess = "Profile successfully updated!\n"
                + "Your current profile information:\n"
                + "Name: " + model.getUserProfile().getName()
                + "\nPhone: " + model.getUserProfile().getPhone()
                + "\nEmail: " + model.getUserProfile().getEmail();

        return new CommandResult(messageProfileEditSuccess);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ProfileCommand
                && updatedProfile.equals(((ProfileCommand) other).updatedProfile));
    }
}
