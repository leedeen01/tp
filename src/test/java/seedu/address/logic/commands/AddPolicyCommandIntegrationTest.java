package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.storage.Storage;
import seedu.address.testutil.PolicyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddPolicyCommand}.
 */
public class AddPolicyCommandIntegrationTest {

    private Model model;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
                new UserProfile(), storage);
    }

    @Test
    public void execute_newPolicy_success() throws Exception {
        Policy validPolicy = new PolicyBuilder().withPolicyNumber("UNIQUE123").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getPolicyBook(), new UserPrefs(),
                model.getUserProfile(), storage);
        expectedModel.addPolicy(validPolicy);

        assertCommandSuccess(new AddPolicyCommand(validPolicy), model,
                String.format(AddPolicyCommand.MESSAGE_SUCCESS, Messages.formatPolicy(validPolicy)),
                expectedModel);
    }


    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        Policy policyInList = model.getPolicyBook().getPolicyList().get(0);
        assertCommandFailure(new AddPolicyCommand(policyInList), model,
                AddPolicyCommand.MESSAGE_DUPLICATE_PREMIUM);
    }
}
