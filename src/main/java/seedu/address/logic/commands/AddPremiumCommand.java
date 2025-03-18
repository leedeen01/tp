package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.premium.Premium;

/**
 * Adds a premium to the premium book.
 */
public class AddPremiumCommand extends Command {

    public static final String COMMAND_WORD = "addpremium";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a premium to the premium book. "
            + "Parameters: "
            + PREFIX_POLICY_NUMBER + "POLICY_NUMBER "
            + PREFIX_PREMIUM_NAME + "PREMIUM_NAME "
            + PREFIX_PROVIDER_COMPANY + "PROVIDER_COMPANY "
            + PREFIX_POLICY_LINK + "POLICY_LINK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POLICY_NUMBER + "POL123 "
            + PREFIX_PREMIUM_NAME + "LifeShield "
            + PREFIX_PROVIDER_COMPANY + "ShieldCorp "
            + PREFIX_POLICY_LINK + "https://www.shieldcorp.com/policy123";

    public static final String MESSAGE_SUCCESS = "New premium added: %1$s";
    public static final String MESSAGE_DUPLICATE_PREMIUM = "This premium already exists in the premium book";

    private final Premium toAdd;

    /**
     * Creates an AddPremiumCommand to add the specified {@code Premium}
     */
    public AddPremiumCommand(Premium premium) {
        requireNonNull(premium);
        toAdd = premium;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPremium(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PREMIUM);
        }

        model.addPremium(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatPremium(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPremiumCommand)) {
            return false;
        }

        AddPremiumCommand otherAddPremiumCommand = (AddPremiumCommand) other;
        return toAdd.equals(otherAddPremiumCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}