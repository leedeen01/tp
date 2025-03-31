package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.List;
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

    public static final String MESSAGE_ADD_PREMIUM_SUCCESS = "Added Premium for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one Premium to add must be provided.";
    public static final String MESSAGE_DUPLICATE_PREMIUM = "This premium already exists in the address book.";
    public static final String MESSAGE_INVALID_PPREMIUM = "Premium name given does not exist.";

    private final Index index;
    private final PremiumList premiumList;

    public AddPremiumCommand(Index index, PremiumList premiumList) {
        requireNonNull(index);
        requireNonNull(premiumList);

        this.index = index;
        this.premiumList = premiumList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Person editedPerson = createAddedPerson(personToEdit, premiumList);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_PREMIUM_SUCCESS, Messages.formatPremium(editedPerson)));
    }

    public Person createAddedPerson(Person personToEdit, PremiumList premiumList) {
        assert personToEdit != null;
        assert premiumList != null;
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Birthday birthday = personToEdit.getBirthday();
        Set<Tag> tagList = personToEdit.getTags();

        return new Person(name,phone,email,address,birthday,premiumList,tagList);
    }
}
