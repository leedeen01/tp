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
import seedu.address.model.PolicyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PremiumList;
import seedu.address.storage.Storage;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePremiumCommand}.
 */
public class DeletePremiumCommandTest {

    private Storage storage;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
            new UserProfile(), storage);

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(
                outOfBoundIndex, new PremiumList());

        assertCommandFailure(deletePremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Create a premium list with the first premium from the person's list
        PremiumList premiumsToDelete = new PremiumList();
        if (!personToModify.getPremiumList().premiumList.isEmpty()) {
            premiumsToDelete.add(personToModify.getPremiumList().premiumList.get(0));
        }

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumsToDelete);

        // Create expected person with updated premium list (without the first premium)
        PremiumList expectedPremiumList = new PremiumList();
        for (int i = 1; i < personToModify.getPremiumList().premiumList.size(); i++) {
            expectedPremiumList.add(personToModify.getPremiumList().premiumList.get(i));
        }

        Person expectedPerson = new PersonBuilder(personToModify)
                .withPremiumList(expectedPremiumList)
                .build();

        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(expectedPerson));

        // Create a fresh expected model by copying the current state of the model
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()),
                new PolicyBook(model.getPolicyBook()),
                new UserPrefs(),
                new UserProfile(),
                storage
        );

        // Update the expected model with the change we expect
        expectedModel.setPerson(personToModify, expectedPerson);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(
                outOfBoundIndex, new PremiumList());

        assertCommandFailure(deletePremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personWithNoPremiums_success() {
        // First create a person with no premiums
        Person personWithNoPremiums = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON
                .getZeroBased()))
                .withPremiumList(new PremiumList())
                .build();

        // Update the model with this person
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personWithNoPremiums);

        // Create a premium list with a premium that doesn't exist in the person's list
        PremiumList premiumsToDelete = new PremiumListBuilder()
                .withPremium("NonExistentPremium $100")
                .build();

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumsToDelete);

        // Expected result should be the same person (no change)
        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(personWithNoPremiums));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getPolicyBook(), new UserPrefs(), model.getUserProfile(), storage);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteSpecificPremiums_success() {
        // Create a person with multiple premiums
        PremiumList fullPremiumList = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .withPremium("HealthCare $150")
                .withPremium("RetirePlan $300")
                .build();

        Person personWithMultiplePremiums = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON
                .getZeroBased()))
                .withPremiumList(fullPremiumList)
                .build();

        // Update the model with this person
        model.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                personWithMultiplePremiums);

        // Create premium list to delete (only the second premium)
        PremiumList premiumsToDelete = new PremiumListBuilder()
                .withPremium("HealthCare $150")
                .build();

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumsToDelete);

        // Create expected premium list after deletion (first and third premiums)
        PremiumList expectedPremiumList = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .withPremium("RetirePlan $300")
                .build();

        Person expectedPerson = new PersonBuilder(personWithMultiplePremiums)
                .withPremiumList(expectedPremiumList)
                .build();

        String expectedMessage = String.format(DeletePremiumCommand.MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(expectedPerson));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getPolicyBook(), new UserPrefs(), model.getUserProfile(), storage);
        expectedModel.setPerson(personWithMultiplePremiums, expectedPerson);

        assertCommandSuccess(deletePremiumCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        PremiumList premiumList1 = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .build();

        PremiumList premiumList2 = new PremiumListBuilder()
                .withPremium("HealthCare $150")
                .build();

        DeletePremiumCommand deleteFirstCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumList1);
        DeletePremiumCommand deleteSecondCommand = new DeletePremiumCommand(INDEX_SECOND_PERSON, premiumList1);
        DeletePremiumCommand deleteFirstDifferentPremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON,
                premiumList2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePremiumCommand deleteFirstCommandCopy = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumList1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // same index but different premium list -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstDifferentPremiumCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PremiumList premiumList = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .build();

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(targetIndex, premiumList);
        String expected = DeletePremiumCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", premiumList=" + premiumList
                + "}";
        assertEquals(expected, deletePremiumCommand.toString());
    }
}
