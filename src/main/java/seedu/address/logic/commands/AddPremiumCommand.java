package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class AddPremiumCommand extends Command{
    public static final String COMMAND_WORD = "addpr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Adds the premiums to the person identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PREMIUM
            + "PREMIUM NAME,PREMIUM AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PREMIUM
            + "LifeShield $300";

    public static final String MESSAGE_EDIT_PREMIUM_SUCCESS = "Added Premium for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one Premium to add must be provided.";
    public static final String MESSAGE_DUPLICATE_PREMIUM = "This premium already exists in the address book.";

    private final Index index;
    private final AddPremiumDescriptor addPremiumDescriptor;

    public AddPremiumCommand(Index index, AddPremiumDescriptor addPremiumDescriptor) {
        requireNonNull(index);
        requireNonNull(addPremiumDescriptor);

        this.index = index;
        this.addPremiumDescriptor = addPremiumDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Person editedPerson = createEditedPerson(personToEdit, addPremiumDescriptor);

        if (!personToEdit.hasPremium(editedPerson.getPremiumList())) {
            throw new CommandException(MESSAGE_DUPLICATE_PREMIUM);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PREMIUM_SUCCESS, Messages.formatPremium(editedPerson)));
    }
}
