package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PremiumList;
import seedu.address.storage.Storage;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePremiumCommand}.
 */
public class DeletePremiumCommandTest {

    private Storage storage;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
            new UserProfile(), storage);

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON);

        // Create expected person with empty premium list
        Person expectedPerson = new PersonBuilder(personToModify)
                .withPremiumList(new PremiumList())
                .build();

        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(expectedPerson));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getPolicyBook(), new UserPrefs(), model.getUserProfile(), storage);
        expectedModel.setPerson(personToModify, expectedPerson);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(outOfBoundIndex);

        assertCommandFailure(deletePremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON);

        // Create expected person with empty premium list
        Person expectedPerson = new PersonBuilder(personToModify)
                .withPremiumList(new PremiumList())
                .build();

        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(expectedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getPolicyBook(), new UserPrefs(), model.getUserProfile(), storage);
        expectedModel.setPerson(personToModify, expectedPerson);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(outOfBoundIndex);

        assertCommandFailure(deletePremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personWithNoPremiums_success() {
        // First create a person with no premiums
        Person personWithNoPremiums = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPremiumList(new PremiumList())
                .build();

        // Update the model with this person
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personWithNoPremiums);

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON);

        // Expected result should be the same person (no change)
        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(personWithNoPremiums));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getPolicyBook(), new UserPrefs(), model.getUserProfile(), storage);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeletePremiumCommand deleteFirstCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON);
        DeletePremiumCommand deleteSecondCommand = new DeletePremiumCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePremiumCommand deleteFirstCommandCopy = new DeletePremiumCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(targetIndex);
        String expected = DeletePremiumCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deletePremiumCommand.toString());
    }
}