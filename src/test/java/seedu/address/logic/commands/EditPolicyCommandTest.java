package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_POLICY_POL101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_POLICY_POL456;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_POL456;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PolicyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.storage.Storage;
import seedu.address.testutil.EditPolicyDescriptorBuilder;
import seedu.address.testutil.PolicyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPolicyCommand.
 */
public class EditPolicyCommandTest {

    private Storage storage;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
            new UserProfile(), storage);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Policy editedPolicy = new PolicyBuilder().build();
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(editedPolicy).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(INDEX_FIRST_POLICY, descriptor);

        String expectedMessage =
            String.format(EditPolicyCommand.MESSAGE_EDIT_POLICY_SUCCESS, Messages.formatPolicy(editedPolicy));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PolicyBook(model.getPolicyBook()), new UserPrefs(), new UserProfile(), storage);
        expectedModel.setPolicy(model.getFilteredPolicyList().get(0), editedPolicy);

        assertCommandSuccess(editPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePolicyUnfilteredList_failure() {
        Policy firstPolicy = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(firstPolicy).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(INDEX_SECOND_POLICY, descriptor);

        assertCommandFailure(editPolicyCommand, model, EditPolicyCommand.MESSAGE_DUPLICATE_POLICY);
    }

    @Test
    public void execute_invalidPolicyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyName(VALID_POLICY_NAME_POL456).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditPolicyCommand standardCommand = new EditPolicyCommand(INDEX_FIRST_POLICY, DESC_POLICY_POL101);

        // same values -> returns true
        EditPolicyDescriptor copyDescriptor = new EditPolicyDescriptor(DESC_POLICY_POL101);
        EditPolicyCommand commandWithSameValues = new EditPolicyCommand(INDEX_FIRST_POLICY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPolicyCommand(INDEX_SECOND_POLICY, DESC_POLICY_POL101)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPolicyCommand(INDEX_FIRST_POLICY, DESC_POLICY_POL456)));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_POLICY;
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(index, editPolicyDescriptor);
        String expected = EditPolicyCommand.class.getCanonicalName() + "{index=" + index + ", editPolicyDescriptor="
                + editPolicyDescriptor + "}";
        assertEquals(expected, editPolicyCommand.toString());
    }
}
