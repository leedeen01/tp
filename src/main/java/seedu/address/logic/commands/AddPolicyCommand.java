package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Adds a policy to the policy book.
 */
public class AddPolicyCommand extends Command {

    public static final String COMMAND_WORD = "addpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy to the policy book. "
            + "Parameters: "
            + PREFIX_POLICY_NUMBER + "POLICY_NUMBER "
            + PREFIX_POLICY_NAME + "PREMIUM_NAME "
            + PREFIX_PROVIDER_COMPANY + "PROVIDER_COMPANY "
            + PREFIX_POLICY_LINK + "POLICY_LINK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY_NUMBER + "POL123 "
            + PREFIX_POLICY_NAME + "LifeShield "
            + PREFIX_PROVIDER_COMPANY + "ShieldCorp "
            + PREFIX_POLICY_LINK + "https://www.shieldcorp.com/policy123";

    public static final String MESSAGE_SUCCESS = "New policy added: %1$s";
    public static final String MESSAGE_DUPLICATE_PREMIUM = "This policy already exists in the policy book";

    private final Policy toAdd;

    /**
     * Creates an AddPolicyCommand to add the specified {@code Policy}
     */
    public AddPolicyCommand(Policy policy) {
        requireNonNull(policy);
        toAdd = policy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPolicy(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PREMIUM);
        }

        model.addPolicy(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatPolicy(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand otherAddPolicyCommand = (AddPolicyCommand) other;
        return toAdd.equals(otherAddPolicyCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
