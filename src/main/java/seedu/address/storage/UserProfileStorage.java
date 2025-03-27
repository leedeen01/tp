package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;

/**
 * Represents a storage for {@link UserProfile}.
 */
public interface UserProfileStorage {

    /**
     * Returns the file path of the user profile data file.
     */
    Path getUserProfileFilePath();

    /**
     * Returns UserProfile data as a {@link UserProfile}.
     * Returns an empty Optional if storage file is not found.
     */
    Optional<UserProfile> readUserProfile() throws DataLoadingException, IOException;

    /**
     * Returns UserProfile data as a {@link UserProfile}.
     * Returns an empty Optional if storage file is not found.
     */
    Optional<UserProfile> readUserProfile(Path filePath) throws DataLoadingException, IOException;

    /**
     * Saves the given {@link UserProfile} to the storage.
     * @param userProfile cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserProfile(UserProfile userProfile) throws IOException;

    /**
     * Saves the given {@link UserProfile} to storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserProfile(UserProfile userProfile, Path filePath) throws IOException;
}
