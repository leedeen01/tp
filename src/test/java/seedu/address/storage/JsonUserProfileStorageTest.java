package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonUserProfileStorageTest {

    @TempDir
    public Path testFolder;

    private Path getTestFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    private Optional<UserProfile> readUserProfile(Path filePath) throws DataLoadingException, IOException {
        return new JsonUserProfileStorage(filePath).readUserProfile(filePath);
    }

    @Test
    public void readUserProfile_fileNotExists() throws DataLoadingException, IOException {
        Path filePath = getTestFilePath("userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(new UserProfile(), result.get(),
                "Expected an empty UserProfile when the file doesn't exist.");
    }

    @Test
    public void readUserProfile_fileExists() throws DataLoadingException, IOException {
        Path filePath = getTestFilePath("userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        UserProfile testProfile = new UserProfile(new Name("Alice"), new Email("alice@example.com"),
                new Phone("123456789"));
        jsonUserProfileStorage.saveUserProfile(testProfile);

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The returned UserProfile should match the saved profile.");
    }

    @Test
    public void saveUserProfile_success() throws IOException, DataLoadingException {
        Path filePath = getTestFilePath("userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        UserProfile testProfile = new UserProfile(new Name("Bob"), new Email("bob@example.com"),
                new Phone("987654321"));
        jsonUserProfileStorage.saveUserProfile(testProfile);

        assertTrue(Files.exists(filePath), "The profile file should exist after saving.");

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();
        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The saved UserProfile should match the input.");
    }

    @Test
    public void saveUserProfile_fileDoesNotExist() throws IOException, DataLoadingException {
        Path filePath = getTestFilePath("non_existent_directory/userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        UserProfile testProfile = new UserProfile(new Name("Charlie"), new Email("charlie@example.com"),
                new Phone("555123456"));
        jsonUserProfileStorage.saveUserProfile(testProfile);

        assertTrue(Files.exists(filePath), "The profile file should be created after saving.");

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();
        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(testProfile, result.get(), "The saved UserProfile should match the input.");
    }

    @Test
    public void readUserProfile_fileCreatedWhenMissing() throws DataLoadingException, IOException {
        Path filePath = getTestFilePath("userProfile.json");
        JsonUserProfileStorage jsonUserProfileStorage = new JsonUserProfileStorage(filePath);

        Files.deleteIfExists(filePath);

        Optional<UserProfile> result = jsonUserProfileStorage.readUserProfile();

        assertTrue(result.isPresent(), "Expected a UserProfile to be returned.");
        assertEquals(new UserProfile(), result.get(),
                "Expected the default profile when the file doesn't exist.");

        assertTrue(Files.exists(filePath), "The profile file should be created when it doesn't exist.");
    }
}
