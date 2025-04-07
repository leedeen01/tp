package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;

/**
 * Deletes specific premiums identified by name from a person identified by the index number
 * in the displayed person list.
 */
public class DeletePremiumCommand extends Command {
    public static final String COMMAND_WORD = "deletepr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the premiums identified by name for the person identified by the index number used in the "
            + "displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PREMIUM
            + "PREMIUM NAME\n"
            + "Example: " + COMMAND_WORD + " 1" + PREFIX_PREMIUM
            + "LifeShield";

    public static final String MESSAGE_DELETE_PREMIUM_SUCCESS = "Deleted Premium: %1$s";
    public static final String MESSAGE_INVALID_PREMIUM_NAME = "Person at index must have given premium name";

    private final Index targetIndex;
    private final PremiumList premiumList;

    /**
     * Creates a DeletePremiumCommand to delete specific premiums from the specified {@code Person}.
     *
     * @param targetIndex the index of the person in the filtered person list
     * @param premiumList the list of premiums to be deleted, identified by their names
     */
    public DeletePremiumCommand(Index targetIndex, PremiumList premiumList) {
        this.targetIndex = targetIndex;
        this.premiumList = premiumList;
    }

    /**
     * Executes the command to delete specific premiums from the person at the specified index.
     * Only the premiums that match the names in the provided premium list will be removed.
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

        Person premiumDeletedPerson;
        try {
            premiumDeletedPerson = deletePremium(premiumToDelete);
        } catch (ParseException pe) {
            throw new CommandException(MESSAGE_INVALID_PREMIUM_NAME);
        }
        model.setPerson(premiumToDelete, premiumDeletedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_PREMIUM_SUCCESS,
                Messages.formatPremium(premiumDeletedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with all the same attributes as {@code premiumToDelete}
     * except with the specified premiums removed from their premium list.
     * Only premiums that match the names in the command's premium list will be removed.
     *
     * @param premiumToDelete the person whose specific premiums are to be deleted
     * @return a new Person with the specified premiums removed
     */
    private Person deletePremium(Person premiumToDelete) throws ParseException {
        PremiumList premiumListToChange = premiumToDelete.getPremiumList();

        for (Premium p : premiumList.premiumList) {
            if (!premiumListToChange.contains(p)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PREMIUM_NAME, MESSAGE_USAGE));
            }
            premiumListToChange.remove(p);
        }

        return new Person(premiumToDelete.getName(), premiumToDelete.getPhone(), premiumToDelete.getEmail(),
                premiumToDelete.getAddress(), premiumToDelete.getBirthday(), premiumListToChange,
                premiumToDelete.getTags());
    }

    /**
     * Compares this DeletePremiumCommand with another object for equality.
     * Two DeletePremiumCommand objects are equal if they have the same target index
     * and the same premium list to delete.
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
        return targetIndex.equals(otherDeletePremiumCommand.targetIndex) && premiumList.equals(
                otherDeletePremiumCommand.premiumList);
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
                .add("premiumList", premiumList)
                .toString();
    }
}
