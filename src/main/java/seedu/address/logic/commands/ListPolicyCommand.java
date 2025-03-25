package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import seedu.address.model.Model;

/**
 * Lists all policies in the policy book to the user.
 */
public class ListPolicyCommand extends Command {

    public static final String COMMAND_WORD = "listpolicy";

    public static final String MESSAGE_SUCCESS = "Listed all policies";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
