package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.policy.PolicyContainsKeywordsPredicate;

/**
 * Finds and lists all policies in the address book whose name contains any of
 * the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindPolicyCommand extends Command {
    public static final String COMMAND_WORD = "findpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all policies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " health insurance";

    private final PolicyContainsKeywordsPredicate predicate;

    public FindPolicyCommand(PolicyContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPolicyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_POLICIES_LISTED_OVERVIEW, model.getFilteredPolicyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPolicyCommand)) {
            return false;
        }

        FindPolicyCommand otherFindPolicyCommand = (FindPolicyCommand) other;
        return predicate.equals(otherFindPolicyCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
