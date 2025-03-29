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

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Adds the premium details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Identifies premium policy by name and edits it if it exits. "
            + "Else it will add a new premium policy.\n"
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
        Person editedPerson = createEditedPerson(personToEdit, editPremiumDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PREMIUM);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PREMIUM_SUCCESS, Messages.formatPremium(editedPerson)));
    }

    public static class AddPremiumDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Birthday birthday;
        private Set<Tag> tags;
        private PremiumList premiumList;

        public AddPremiumDescriptor() {}

        public AddPremiumDescriptor(AddPremiumDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setBirthday(toCopy.birthday);
            setTags(toCopy.tags);
            setPremium(toCopy.premiumList);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public void setPremium(PremiumList premiumList) {
            this.premiumList = premiumList;
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddPremiumCommand.AddPremiumDescriptor)) {
                return false;
            }

            AddPremiumCommand.AddPremiumDescriptor otherAddPremiumDescriptor =
                    (AddPremiumCommand.AddPremiumDescriptor) other;

            return Objects.equals(name, otherAddPremiumDescriptor.name)
                    && Objects.equals(phone, otherAddPremiumDescriptor.phone)
                    && Objects.equals(email, otherAddPremiumDescriptor.email)
                    && Objects.equals(address, otherAddPremiumDescriptor.address)
                    && Objects.equals(birthday, otherAddPremiumDescriptor.birthday)
                    && Objects.equals(tags, otherAddPremiumDescriptor.tags)
                    && Objects.equals(premiumList, otherAddPremiumDescriptor.premiumList);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("birthday", birthday)
                    .add("tags", tags)
                    .add("premiumList", premiumList)
                    .toString();
        }
    }
}
