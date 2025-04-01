package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddPremiumCommand.
 */
public class AddPremiumCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
            new UserProfile(), null);

    @Test
    public void execute_validIndexUnfilteredList_success() throws ParseException {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PremiumList premiumToAdd = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();

        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumToAdd);

        Person editedPerson = new PersonBuilder(personInList)
                .withPremiumList(premiumToAdd)
                .build();

        String expectedMessage = String.format(AddPremiumCommand.MESSAGE_ADD_PREMIUM_SUCCESS,
                Messages.formatPremium(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getTypicalPolicyBook(), new UserPrefs(), new UserProfile(), null);
        expectedModel.setPerson(personInList, editedPerson);

        assertCommandSuccess(addPremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PremiumList premiumToAdd = new PremiumListBuilder().withPremium(VALID_PREMIUM_BOB).build();

        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumToAdd);

        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withPremiumList(premiumToAdd)
                .build();

        String expectedMessage = String.format(AddPremiumCommand.MESSAGE_ADD_PREMIUM_SUCCESS,
                Messages.formatPremium(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                getTypicalPolicyBook(), new UserPrefs(), new UserProfile(), null);
        expectedModel.setPerson(personInFilteredList, editedPerson);

        assertCommandSuccess(addPremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePremium_failure() throws ParseException {
        Person personInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PremiumList existingPremiums = personInList.getPremiumList();

        // Create command with premium that person already has
        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(INDEX_FIRST_PERSON, existingPremiums);

        assertCommandFailure(addPremiumCommand, model, AddPremiumCommand.MESSAGE_DUPLICATE_PREMIUM);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PremiumList premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();

        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(outOfBoundIndex, premiumList);

        assertCommandFailure(addPremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Add premium to filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(outOfBoundIndex,
                new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build());

        assertCommandFailure(addPremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        PremiumList premiumListAmy = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();
        PremiumList premiumListBob = new PremiumListBuilder().withPremium(VALID_PREMIUM_BOB).build();

        final AddPremiumCommand standardCommand = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListAmy);

        // same values -> returns true
        AddPremiumCommand commandWithSameValues = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListAmy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPremiumCommand(INDEX_SECOND_PERSON, premiumListAmy)));

        // different premium -> returns false
        assertFalse(standardCommand.equals(new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListBob)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        PremiumList premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();
        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(index, premiumList);
        String expected = AddPremiumCommand.class.getCanonicalName() + "{index=" + index + ", premiumList="
                + premiumList + "}";
        assertEquals(expected, addPremiumCommand.toString());
    }

    @Test
    public void createAddedPerson_validInputs_success() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PremiumList premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();

        AddPremiumCommand command = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumList);
        Person addedPerson = command.createAddedPerson(person, premiumList);

        // Verify that the new person has all the same attributes except for premium list
        assertEquals(person.getName(), addedPerson.getName());
        assertEquals(person.getPhone(), addedPerson.getPhone());
        assertEquals(person.getEmail(), addedPerson.getEmail());
        assertEquals(person.getAddress(), addedPerson.getAddress());
        assertEquals(person.getBirthday(), addedPerson.getBirthday());
        assertEquals(person.getTags(), addedPerson.getTags());

        // Verify that the premium list is as expected
        assertEquals(premiumList, addedPerson.getPremiumList());
    }
}