package seedu.address.commons.core.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class UserProfileTest {

    private UserProfile userProfile;
    private UserProfile defaultProfile;

    @BeforeEach
    public void setUp() {
        // Create a default profile for comparison
        defaultProfile = new UserProfile();
        userProfile = new UserProfile(new Name("John Doe"), new Email("john.doe@gmail.com"), new Phone("98765432"));
    }

    @Test
    public void constructor_defaultProfile_initializesWithDefaultValues() {
        assertEquals("Guest Name", defaultProfile.getName().fullName);
        assertEquals("guest@gmail.com", defaultProfile.getEmail().value);
        assertEquals("91234567", defaultProfile.getPhone().value);
    }

    @Test
    public void constructor_customProfile_initializesWithGivenValues() {
        assertEquals("John Doe", userProfile.getName().fullName);
        assertEquals("john.doe@gmail.com", userProfile.getEmail().value);
        assertEquals("98765432", userProfile.getPhone().value);
    }

    @Test
    public void setUserProfileName_updatesOnlyName() {
        Name newName = new Name("Alice");
        userProfile.setUserProfileName(newName);
        assertEquals("Alice", userProfile.getName().fullName);
    }

    @Test
    public void setUserProfileEmail_updatesOnlyEmail() {
        Email newEmail = new Email("alice.email@gmail.com");
        userProfile.setUserProfileEmail(newEmail);
        assertEquals("alice.email@gmail.com", userProfile.getEmail().value);
    }

    @Test
    public void setUserProfilePhone_updatesOnlyPhone() {
        Phone newPhone = new Phone("87654321");
        userProfile.setUserProfilePhone(newPhone);
        assertEquals("87654321", userProfile.getPhone().value);
    }

    @Test
    public void setUserProfile_doesNotOverrideWhenNewValuesAreSame() {
        // Setting values that are already the same
        UserProfile newProfile = new UserProfile(new Name("John Doe"), new Email("john.doe@gmail.com"),
                new Phone("98765432"));
        userProfile.setUserProfile(defaultProfile, newProfile);
        assertEquals("John Doe", userProfile.getName().fullName);
        assertEquals("john.doe@gmail.com", userProfile.getEmail().value);
        assertEquals("98765432", userProfile.getPhone().value);
    }

    @Test
    public void equals_identicalProfiles_areEqual() {
        UserProfile profile1 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        UserProfile profile2 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        assertTrue(profile1.equals(profile2));
    }

    @Test
    public void equals_differentProfiles_areNotEqual() {
        UserProfile profile1 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        UserProfile profile2 = new UserProfile(new Name("John Doe"), new Email("john.doe@gmail.com"),
                new Phone("98765432"));
        assertFalse(profile1.equals(profile2));
    }

    @Test
    public void hashCode_identicalProfiles_haveSameHashCode() {
        UserProfile profile1 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        UserProfile profile2 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        assertEquals(profile1.hashCode(), profile2.hashCode());
    }

    @Test
    public void hashCode_differentProfiles_haveDifferentHashCodes() {
        UserProfile profile1 = new UserProfile(new Name("Jane Doe"), new Email("jane.doe@gmail.com"),
                new Phone("91234567"));
        UserProfile profile2 = new UserProfile(new Name("John Doe"), new Email("john.doe@gmail.com"),
                new Phone("98765432"));
        assertNotEquals(profile1.hashCode(), profile2.hashCode());
    }

    @Test
    public void toString_formatCorrect() {
        UserProfile profile = new UserProfile(new Name("Alice"), new Email("alice@example.com"),
                new Phone("12345678"));
        String expected = "seedu.address.commons.core.user.UserProfile{name=Alice, "
                + "email=alice@example.com, phone=12345678}";
        assertEquals(expected, profile.toString());
    }
}
