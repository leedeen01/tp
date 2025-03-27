package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonUserProfileStorageTest {

    @Test
    public void readUserProfile_fileNotExists() throws DataLoadingException, IOException {
        Path filePath = Path.of("data", "userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Delete the file if it exists
        Files.deleteIfExists(filePath);

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(new UserProfile(), result.get(),
                "Expected an empty UserProfile when the file doesn't exist.");
    }

    @Test
    public void readUserProfile_fileExists() throws DataLoadingException, IOException {
        Path filePath = Path.of("data", "userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Prepare a valid UserProfile to be stored in the file
        UserProfile testProfile = new UserProfile(new Name("Alice"), new Email("alice@example.com"),
                new Phone("123456789"));

        // Save the test profile to the file
        jsonUserProfileStorage.saveUserProfile(testProfile);

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The returned UserProfile should match the saved profile.");
    }

    @Test
    public void saveUserProfile_success() throws IOException, DataLoadingException {
        Path filePath = Path.of("data", "userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Prepare a UserProfile to save
        UserProfile testProfile = new UserProfile(new Name("Bob"), new Email("bob@example.com"),
                new Phone("987654321"));

        // Save the test profile
        jsonUserProfileStorage.saveUserProfile(testProfile);

        // Verify that the file exists
        assertTrue(Files.exists(filePath), "The profile file should exist after saving.");

        // Read the file and verify that the saved profile matches the one we tried to save
        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();
        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The saved UserProfile should match the input.");
    }

    @Test
    public void saveUserProfile_fileDoesNotExist() throws IOException, DataLoadingException {
        Path filePath = Path.of("non_existent_directory", "userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Prepare a UserProfile to save
        UserProfile testProfile = new UserProfile(new Name("Charlie"), new Email("charlie@example.com"),
                new Phone("555123456"));

        // Save the test profile
        jsonUserProfileStorage.saveUserProfile(testProfile);

        // Verify that the file now exists
        assertTrue(Files.exists(filePath), "The profile file should be created after saving.");

        // Read the file and verify that the saved profile matches the one we tried to save
        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();
        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The saved UserProfile should match the input.");
    }

    @Test
    public void readUserProfile_fileCreatedWhenMissing() throws DataLoadingException, IOException {
        Path filePath = Path.of("data", "userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        // Delete the file if it exists
        Files.deleteIfExists(filePath);

        // Read the user profile, which should create the file with the default profile
        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(new UserProfile(), result.get(),
                "Expected the default profile when the file doesn't exist.");

        // Verify that the file has been created
        assertTrue(Files.exists(filePath), "The profile file should be created when it doesn't exist.");
    }
}
