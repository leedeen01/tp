package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPolicyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, PolicyBookStorage, UserPrefsStorage, UserProfileStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    void createDefaultAddressBook(Path filePath) throws IOException;

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getPolicyBookFilePath();

    @Override
    void createDefaultPolicyBook(Path filePath) throws IOException;

    @Override
    Optional<ReadOnlyPolicyBook> readPolicyBook() throws DataLoadingException;

    @Override
    Optional<ReadOnlyPolicyBook> readPolicyBook(Path filePath) throws DataLoadingException;

    @Override
    void savePolicyBook(ReadOnlyPolicyBook policyBook) throws IOException;

    @Override
    Path getUserProfileFilePath();

    @Override
    Optional<UserProfile> readUserProfile() throws DataLoadingException, IOException;

    @Override
    Optional<UserProfile> readUserProfile(Path filePath) throws DataLoadingException, IOException;

    @Override
    void saveUserProfile(UserProfile userProfile) throws IOException;

}
