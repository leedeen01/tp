package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonSerializableUserProfileTest {

    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "johndoe@example.com";
    private static final String VALID_PHONE = "98765432";

    @Test
    public void toModelType_validUserProfile_createsUserProfile() throws IllegalValueException {
        // Create a UserProfile with valid data
        UserProfile validProfile = new UserProfile(new Name(VALID_NAME), new Email(VALID_EMAIL),
                new Phone(VALID_PHONE));

        // Convert to JsonSerializableUserProfile
        JsonSerializableUserProfile jsonSerializable = new JsonSerializableUserProfile(validProfile);

        // Convert back to UserProfile
        UserProfile modelProfile = jsonSerializable.toModelType();

        // Assert the model profile matches the original valid profile
        assertEquals(validProfile, modelProfile);
    }
}
