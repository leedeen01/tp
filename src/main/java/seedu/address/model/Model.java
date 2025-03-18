package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.premium.Premium;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Premium> PREDICATE_SHOW_ALL_PREMIUMS = unused -> true; // New predicate

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

    // New methods for PremiumBook
    /**
     * Returns the user prefs' premium book file path.
     */
    Path getPremiumBookFilePath();

    /**
     * Sets the user prefs' premium book file path.
     */
    void setPremiumBookFilePath(Path premiumBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces premium book data with the data in {@code premiumBook}.
     */
    void setPremiumBook(ReadOnlyPremiumBook premiumBook); // New method

    /** Returns the PremiumBook */
    ReadOnlyPremiumBook getPremiumBook(); // New method

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    // New method for Premium
    /**
     * Returns true if a premium with the same identity as {@code premium} exists in the premium book.
     */
    boolean hasPremium(Premium premium); // New method

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    // New method for Premium
    /**
     * Deletes the given premium.
     * The premium must exist in the premium book.
     */
    void deletePremium(Premium target); // New method

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    // New method for Premium
    /**
     * Adds the given premium.
     * {@code premium} must not already exist in the premium book.
     */
    void addPremium(Premium premium); // New method

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    // New method for Premium
    /**
     * Replaces the given premium {@code target} with {@code editedPremium}.
     * {@code target} must exist in the premium book.
     * The premium identity of {@code editedPremium} must not be the same as another existing premium in the premium book.
     */
    void setPremium(Premium target, Premium editedPremium); // New method

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered premium list */
    ObservableList<Premium> getFilteredPremiumList(); // New method

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered premium list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPremiumList(Predicate<Premium> predicate); // New method
}
