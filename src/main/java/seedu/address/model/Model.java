package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Policy> PREDICATE_SHOW_ALL_POLICIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    // New methods for PolicyBook
    /**
     * Returns the user prefs' policy book file path.
     */
    Path getPolicyBookFilePath();

    /**
     * Sets the user prefs' policy book file path.
     */
    void setPolicyBookFilePath(Path policyBookFilePath);

    // New methods for User Profile
    /**
     * Returns the user profile's file path.
     */
    Path getUserProfileFilePath();

    /**
     * Sets the user profile file path.
     */
    void setUserProfileFilePath(Path userProfileFilePath);

    /** Returns the user profile. */
    UserProfile getUserProfile();

    /** Sets the user profile. */
    void setUserProfile(UserProfile currentProfile, UserProfile userProfile);

    /** Sets the user profile individual values. */
    void setUserProfileName(Name name);
    void setUserProfileEmail(Email email);
    void setUserProfilePhone(Phone phone);

    /** Saves the user profile into JSON file */
    void saveUserProfile(UserProfile userProfile) throws IOException;

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces policy book data with the data in {@code policyBook}.
     */
    void setPolicyBook(ReadOnlyPolicyBook policyBook);

    /** Returns the PolicyBook */
    ReadOnlyPolicyBook getPolicyBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    // New method for Policy
    /**
     * Returns true if a policy with the same identity as {@code policy} exists in the policy book.
     */
    boolean hasPolicy(Policy policy);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    // New method for Policy
    /**
     * Deletes the given policy.
     * The policy must exist in the policy book.
     */
    void deletePolicy(Policy target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    // New method for Policy
    /**
     * Adds the given policy.
     * {@code policy} must not already exist in the policy book.
     */
    void addPolicy(Policy policy);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    // New method for Policy
    /**
     * Replaces the given policy {@code target} with {@code editedPolicy}.
     * {@code target} must exist in the policy book.
     * The policy identity of {@code editedPolicy} must not be the same as
     * another existing policy in the policy book.
     */
    void setPolicy(Policy target, Policy editedPolicy);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered policy list */
    ObservableList<Policy> getFilteredPolicyList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered policy list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPolicyList(Predicate<Policy> predicate);
}
