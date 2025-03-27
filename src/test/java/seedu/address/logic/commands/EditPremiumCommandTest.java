package seedu.address.logic.commands.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.EditPremiumCommand.EditPremiumDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PolicyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPremiumDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPremiumCommand.
 */
public class EditPremiumCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs());

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() throws ParseException {
        // Create a copy to avoid modifying the original
        Person firstPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .build();

        // Get existing premium of second person
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Create descriptor with second person's premium
        EditPremiumDescriptor descriptor = new EditPremiumDescriptorBuilder()
                .withPremium(secondPerson.getPremiumList()).build();

        // Only run this test if editing premium would create a duplicate
        Person tempEditedPerson = EditPremiumCommand.createEditedPerson(firstPerson, descriptor);
        // Create a new model for this check to avoid modifying the test model
        Model tempModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PolicyBook(model.getPolicyBook()), new UserPrefs());
        if (!firstPerson.isSamePerson(tempEditedPerson) && tempModel.hasPerson(tempEditedPerson)) {
            EditPremiumCommand editPremiumCommand = new EditPremiumCommand(INDEX_FIRST_PERSON, descriptor);
            assertCommandFailure(editPremiumCommand, model, EditPremiumCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPremiumDescriptor descriptor = new EditPremiumDescriptorBuilder()
                .withPremium(VALID_PREMIUM_AMY).build();
        EditPremiumCommand editPremiumCommand = new EditPremiumCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditPremiumCommand editPremiumCommand = new EditPremiumCommand(outOfBoundIndex,
                new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_AMY).build());

        assertCommandFailure(editPremiumCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws ParseException {
        EditPremiumDescriptor descriptorAmy = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_AMY).build();
        EditPremiumDescriptor descriptorBob = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_BOB).build();

        final EditPremiumCommand standardCommand = new EditPremiumCommand(INDEX_FIRST_PERSON, descriptorAmy);

        // same values -> returns true
        EditPremiumDescriptor copyDescriptor = new EditPremiumDescriptor(descriptorAmy);
        EditPremiumCommand commandWithSameValues = new EditPremiumCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new seedu.address.logic.commands.ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPremiumCommand(INDEX_SECOND_PERSON, descriptorAmy)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPremiumCommand(INDEX_FIRST_PERSON, descriptorBob)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPremiumDescriptor editPremiumDescriptor = new EditPremiumDescriptor();
        EditPremiumCommand editPremiumCommand = new EditPremiumCommand(index, editPremiumDescriptor);
        String expected = EditPremiumCommand.class.getCanonicalName() + "{index=" + index + ", editPremiumDescriptor="
                + editPremiumDescriptor + "}";
        assertEquals(expected, editPremiumCommand.toString());
    }
}
