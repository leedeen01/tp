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

/**
 * Deletes all premiums from a person identified by the index number in the displayed person list.
 * This command completely removes any premium information associated with the selected person.
 */
public class DeletePremiumCommand extends Command {
    public static final String COMMAND_WORD = "deletepr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the premiums for the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PREMIUM_SUCCESS = "Deleted Premium: %1$s";

    private final Index targetIndex;

    /**
     * Creates a DeletePremiumCommand to delete premiums from the specified {@code Person}.
     *
     * @param targetIndex the index of the person in the filtered person list to delete premiums from
     */
    public DeletePremiumCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete all premiums from the person at the specified index.
     *
     * @param model the model containing the person data
     * @return a CommandResult indicating the outcome of the premium deletion operation
     * @throws CommandException if the index is invalid or the person cannot be found
     */
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

    /**
     * Creates and returns a {@code Person} with all the same attributes as {@code premiumToDelete}
     * except with an empty premium list.
     *
     * @param premiumToDelete the person whose premiums are to be deleted
     * @return a new Person with an empty premium list
     */
    private static Person deletePremium(Person premiumToDelete) {
        return new Person(premiumToDelete.getName(), premiumToDelete.getPhone(), premiumToDelete.getEmail(),
                premiumToDelete.getAddress(), premiumToDelete.getBirthday(), new PremiumList(),
                premiumToDelete.getTags());
    }

    /**
     * Compares this DeletePremiumCommand with another object for equality.
     *
     * @param other the object to compare with
     * @return true if the objects are equal, false otherwise
     */
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

    /**
     * Returns a string representation of this DeletePremiumCommand.
     *
     * @return a string representation of this command
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}