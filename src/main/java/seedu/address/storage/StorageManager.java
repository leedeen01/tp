package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPolicyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private PolicyBookStorage policyBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserProfileStorage userProfileStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage, PolicyBookStorage policyBookStorage,
        UserPrefsStorage userPrefsStorage, UserProfileStorage userProfileStorage) {
        this.addressBookStorage = addressBookStorage;
        this.policyBookStorage = policyBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userProfileStorage = userProfileStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public void createDefaultAddressBook(Path filePath) throws IOException {
        logger.fine("Creating a default address book from: " + filePath);
        addressBookStorage.createDefaultAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ PolicyBook methods ==============================
    @Override
    public Path getPolicyBookFilePath() {
        return policyBookStorage.getPolicyBookFilePath();
    }

    @Override
    public void createDefaultPolicyBook(Path filePath) throws IOException {
        logger.fine("Creating a default policy book from: " + filePath);
        policyBookStorage.createDefaultPolicyBook(filePath);
    }

    @Override
    public Optional<ReadOnlyPolicyBook> readPolicyBook() throws DataLoadingException {
        return readPolicyBook(policyBookStorage.getPolicyBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPolicyBook> readPolicyBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return policyBookStorage.readPolicyBook(filePath);
    }

    @Override
    public void savePolicyBook(ReadOnlyPolicyBook policyBook) throws IOException {
        savePolicyBook(policyBook, policyBookStorage.getPolicyBookFilePath());
    }

    @Override
    public void savePolicyBook(ReadOnlyPolicyBook policyBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        policyBookStorage.savePolicyBook(policyBook, filePath);
    }

    @Override
    public Path getUserProfileFilePath() {
        return userProfileStorage.getUserProfileFilePath();
    }

    @Override
    public Optional<UserProfile> readUserProfile() throws DataLoadingException, IOException {
        return readUserProfile(userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public Optional<UserProfile> readUserProfile(Path filePath) throws DataLoadingException, IOException {
        logger.fine("Attempting to read user profile from file: " + filePath);
        return userProfileStorage.readUserProfile(filePath);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) throws IOException {
        saveUserProfile(userProfile, userProfileStorage.getUserProfileFilePath());
    }

    @Override
    public void saveUserProfile(UserProfile userProfile, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userProfileStorage.saveUserProfile(userProfile, filePath);
    }
}
