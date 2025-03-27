package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;

/**
 * A class to access UserProfile stored in the hard disk as a JSON file.
 */
public class JsonUserProfileStorage implements UserProfileStorage {

    private final Path filePath;

    public JsonUserProfileStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserProfileFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserProfile> readUserProfile() throws DataLoadingException {
        try {
            return readUserProfile(filePath);
        } catch (IOException e) {
            throw new DataLoadingException(e);
        }
    }

    @Override
    public Optional<UserProfile> readUserProfile(Path filePath) throws DataLoadingException, IOException {
        if (!Files.exists(filePath)) {
            saveUserProfile(new UserProfile(), filePath); // Save default profile if missing
            return Optional.of(new UserProfile());
        }

        // Using JsonUtil to read from the JSON file and convert to UserProfile
        return JsonUtil.readJsonFile(filePath, UserProfile.class);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) throws IOException {
        saveUserProfile(userProfile, filePath);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile, Path filePath) throws IOException {
        requireNonNull(userProfile);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(userProfile, filePath);
    }
}
