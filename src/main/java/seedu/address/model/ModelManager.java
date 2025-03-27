package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final PolicyBook policyBook;
    private final UserPrefs userPrefs;
    private final UserProfile userProfile;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Person> filteredUpcomingBirthdays;
    private final FilteredList<Policy> filteredPolicies;
    private final Storage storage;

    /**
     * Initializes a ModelManager with the given addressBook, policyBook, and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyPolicyBook policyBook,
                        ReadOnlyUserPrefs userPrefs, UserProfile userProfile, Storage storage) {
        requireAllNonNull(addressBook, policyBook, userPrefs, userProfile);

        logger.fine("Initializing with address book: " + addressBook + ", policy book: " + policyBook
                + ", user prefs " + userPrefs + "and user profile " + userProfile);

        this.addressBook = new AddressBook(addressBook);
        this.policyBook = new PolicyBook(policyBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.userProfile = new UserProfile(userProfile.getName(), userProfile.getEmail(), userProfile.getPhone());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredUpcomingBirthdays = new FilteredList<>(this.addressBook.getPersonList());
        filteredPolicies = new FilteredList<>(this.policyBook.getPolicyList());
        this.storage = storage;
    }

    public ModelManager() {
        this(new AddressBook(), new PolicyBook(), new UserPrefs(), new UserProfile(), null);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getPolicyBookFilePath() {
        return userPrefs.getPolicyBookFilePath();
    }

    @Override
    public void setPolicyBookFilePath(Path policyBookPath) {
        requireNonNull(policyBookPath);
        userPrefs.setPolicyBookFilePath(policyBookPath);
    }

    @Override
    public Path getUserProfileFilePath() {
        return userPrefs.getUserProfileFilePath();
    }

    @Override
    public void setUserProfileFilePath(Path userProfileFilePath) {
        requireNonNull(userProfileFilePath);
        userPrefs.setUserProfileFilePath(userProfileFilePath);
    }

    @Override
    public UserProfile getUserProfile() {
        try {
            if (storage != null) {
                return storage.readUserProfile().orElse(userProfile);
            } else {
                return userProfile;
            }
        } catch (IOException | DataLoadingException e) {
            logger.warning("Failed to read user profile from storage. Using in-memory profile.");
            return userProfile;
        }
    }

    @Override
    public void setUserProfile(UserProfile currentProfile, UserProfile userProfile) {
        userProfile.setUserProfile(currentProfile, userProfile);
    }

    @Override
    public void saveUserProfile(UserProfile userProfile) throws IOException {
        storage.saveUserProfile(userProfile);
    }


    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== PolicyBook ================================================================================

    @Override
    public void setPolicyBook(ReadOnlyPolicyBook policyBook) {
        this.policyBook.resetData(policyBook);
    }

    @Override
    public ReadOnlyPolicyBook getPolicyBook() {
        return policyBook;
    }

    @Override
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return policyBook.hasPolicy(policy);
    }

    @Override
    public void deletePolicy(Policy target) {
        policyBook.removePolicy(target);
    }

    @Override
    public void addPolicy(Policy policy) {
        policyBook.addPolicy(policy);
        updateFilteredPolicyList(Model.PREDICATE_SHOW_ALL_POLICIES);
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        policyBook.setPolicy(target, editedPolicy);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Policy List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Policy} backed by the internal list of
     * {@code versionedPolicyBook}
     */
    @Override
    public ObservableList<Policy> getFilteredPolicyList() {
        return filteredPolicies;
    }

    @Override
    public void updateFilteredPolicyList(Predicate<Policy> predicate) {
        requireNonNull(predicate);
        filteredPolicies.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && policyBook.equals(otherModelManager.policyBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredPolicies.equals(otherModelManager.filteredPolicies);
    }

    //=========== Upcoming Birthdays  ============================================================

    @Override
    public ObservableList<Person> getUpcomingBirthdays() {
        LocalDate today = LocalDate.now();

        return FXCollections.observableArrayList(
            filteredUpcomingBirthdays.stream()
                .filter(p -> p.getBirthday().isWithinNext30Days())
                .sorted((p1, p2) -> {
                    LocalDate b1 = p1.getBirthday().getValue().withYear(today.getYear());
                    LocalDate b2 = p2.getBirthday().getValue().withYear(today.getYear());

                    if (b1.isBefore(today)) {
                        b1 = b1.plusYears(1);
                    }
                    if (b2.isBefore(today)) {
                        b2 = b2.plusYears(1);
                    }

                    return b1.compareTo(b2);
                }).collect(Collectors.toList())
        );
    }

    @Override
    public void updateUpcomingBirthdays() {
        filteredUpcomingBirthdays.setPredicate(p -> p.getBirthday().isWithinNext30Days());
    }
}
