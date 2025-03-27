package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.storage.Storage;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePolicyCommand}.
 */
public class DeletePolicyCommandTest {

    private Storage storage;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(),
                                             new UserPrefs(), new UserProfile(), storage);

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Policy policyToDelete = model.getFilteredPolicyList().get(INDEX_FIRST_POLICY.getZeroBased());
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_DELETE_POLICY_SUCCESS,
                Messages.formatPolicy(policyToDelete));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getPolicyBook(),
                                                new UserPrefs(), model.getUserProfile(), storage);
        expectedModel.deletePolicy(policyToDelete);

        assertCommandSuccess(deletePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPolicyList().size() + 1);
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(outOfBoundIndex);

        assertCommandFailure(deletePolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePolicyCommand deleteFirstCommand = new DeletePolicyCommand(INDEX_FIRST_POLICY);
        DeletePolicyCommand deleteSecondCommand = new DeletePolicyCommand(INDEX_SECOND_POLICY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePolicyCommand deleteFirstCommandCopy = new DeletePolicyCommand(INDEX_FIRST_POLICY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different policy -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(targetIndex);
        String expected = DeletePolicyCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deletePolicyCommand.toString());
    }
}
