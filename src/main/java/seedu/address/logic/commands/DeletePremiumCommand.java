package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PremiumList;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeletePremiumCommand extends Command {
    public static final String COMMAND_WORD = "deletepr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the premiums for the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PREMIUM_SUCCESS = "Deleted Premium: %1$s";

    private final Index targetIndex;

    public DeletePremiumCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person premiumToDelete = lastShownList.get(targetIndex.getZeroBased());

        Person premiumDeletedPerson = deletePremium(premiumToDelete);

        model.setPerson(premiumToDelete, premiumDeletedPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_PREMIUM_SUCCESS, Messages.formatPremium(premiumDeletedPerson)));
    }

    private static Person deletePremium(Person premiumToDelete) {
        return new Person(premiumToDelete.getName(), premiumToDelete.getPhone(), premiumToDelete.getEmail(), premiumToDelete.getAddress(), premiumToDelete.getBirthday(), new PremiumList(), premiumToDelete.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePremiumCommand)) {
            return false;
        }

        DeletePremiumCommand otherDeletePremiumCommand = (DeletePremiumCommand) other;
        return targetIndex.equals(otherDeletePremiumCommand.targetIndex);
    }
        
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
