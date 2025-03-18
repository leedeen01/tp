package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.premium.Premium;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final PremiumBook premiumBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Premium> filteredPremiums;

    /**
     * Initializes a ModelManager with the given addressBook, premiumBook, and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyPremiumBook premiumBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, premiumBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", premium book: " + premiumBook 
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.premiumBook = new PremiumBook(premiumBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredPremiums = new FilteredList<>(this.premiumBook.getPremiumList());
    }

    public ModelManager() {
        this(new AddressBook(), new PremiumBook(), new UserPrefs());
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
    public Path getPremiumBookFilePath() {
        return userPrefs.getPremiumBookFilePath();
    }

    @Override
    public void setPremiumBookFilePath(Path premiumBookFilePath) {
        requireNonNull(premiumBookFilePath);
        userPrefs.setPremiumBookFilePath(premiumBookFilePath);
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

    //=========== PremiumBook ================================================================================

    @Override
    public void setPremiumBook(ReadOnlyPremiumBook premiumBook) {
        this.premiumBook.resetData(premiumBook);
    }

    @Override
    public ReadOnlyPremiumBook getPremiumBook() {
        return premiumBook;
    }

    @Override
    public boolean hasPremium(Premium premium) {
        requireNonNull(premium);
        return premiumBook.hasPremium(premium);
    }

    @Override
    public void deletePremium(Premium target) {
        premiumBook.removePremium(target);
    }

    @Override
    public void addPremium(Premium premium) {
        premiumBook.addPremium(premium);
        updateFilteredPremiumList(Model.PREDICATE_SHOW_ALL_PREMIUMS);
    }

    @Override
    public void setPremium(Premium target, Premium editedPremium) {
        requireAllNonNull(target, editedPremium);

        premiumBook.setPremium(target, editedPremium);
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

    //=========== Filtered Premium List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Premium} backed by the internal list of
     * {@code versionedPremiumBook}
     */
    @Override
    public ObservableList<Premium> getFilteredPremiumList() {
        return filteredPremiums;
    }

    @Override
    public void updateFilteredPremiumList(Predicate<Premium> predicate) {
        requireNonNull(predicate);
        filteredPremiums.setPredicate(predicate);
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
                && premiumBook.equals(otherModelManager.premiumBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredPremiums.equals(otherModelManager.filteredPremiums);
    }
    
}
