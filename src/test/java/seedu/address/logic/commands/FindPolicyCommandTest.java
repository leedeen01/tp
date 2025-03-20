package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_POLICIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.PolicyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FindPolicyCommand}.
 */
public class FindPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs());

    @Test
    public void equals() {
        PolicyContainsKeywordsPredicate firstPredicate =
                new PolicyContainsKeywordsPredicate(Collections.singletonList("first"));
        PolicyContainsKeywordsPredicate secondPredicate =
                new PolicyContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPolicyCommand findFirstCommand = new FindPolicyCommand(firstPredicate);
        FindPolicyCommand findSecondCommand = new FindPolicyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPolicyCommand findFirstCommandCopy = new FindPolicyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different policy -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPolicyFound() {
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 0);
        PolicyContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPolicyCommand command = new FindPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPolicyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePoliciesFound() {
        // Adjust the keywords based on your typical policy test data.
        // For example, if getTypicalPolicyBook() contains policies with names "LifeShield" and "HealthPlus",
        // a search for "Life Health" should match both.
        String expectedMessage = String.format(MESSAGE_POLICIES_LISTED_OVERVIEW, 2);
        PolicyContainsKeywordsPredicate predicate = preparePredicate("Life Health");
        FindPolicyCommand command = new FindPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Optionally, verify that the filtered list matches the expected policies:
        // assertEquals(Arrays.asList(POLICY_A, POLICY_B), model.getFilteredPolicyList());
    }

    @Test
    public void toStringMethod() {
        PolicyContainsKeywordsPredicate predicate =
                new PolicyContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindPolicyCommand findPolicyCommand = new FindPolicyCommand(predicate);
        String expected = FindPolicyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPolicyCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PolicyContainsKeywordsPredicate}.
     */
    private PolicyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PolicyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
