package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;
import seedu.address.model.tag.Tag;


/**
 * Edits the premium details of an existing person in the address book.
 */
public class EditPremiumCommand extends Command {

    public static final String COMMAND_WORD = "editpr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Edits the premium details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Identifies premium policy by name and edits it if it exits. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PREMIUM
            + "PREMIUM NAME,PREMIUM AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_PREMIUM
            + "LifeShield $300";

    public static final String MESSAGE_EDIT_PREMIUM_SUCCESS = "Edited Premium for Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_INVALID_PPREMIUM = "Premium name given does not exist.";

    private final Index index;
    private final EditPremiumDescriptor editPremiumDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPremiumDescriptor details to edit the premium with
     */
    public EditPremiumCommand(Index index, EditPremiumDescriptor editPremiumDescriptor) {
        requireNonNull(index);
        requireNonNull(editPremiumDescriptor);

        this.index = index;
        this.editPremiumDescriptor = new EditPremiumDescriptor(editPremiumDescriptor);
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

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PREMIUM_SUCCESS, Messages.formatPremium(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPremiumDescriptor}.
     */
    public static Person createEditedPerson(Person personToEdit, EditPremiumDescriptor editPremiumDescriptor)
            throws CommandException {
        assert personToEdit != null;

        if(!validPremium(personToEdit, editPremiumDescriptor.getPremium().orElse(personToEdit.getPremiumList()))) {
            throw new CommandException(MESSAGE_INVALID_PPREMIUM);
        }

        PremiumList updatedPremiumList = editPremium(personToEdit, editPremiumDescriptor.getPremium()
                .orElse(personToEdit.getPremiumList()));

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(),
                updatedPremiumList, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPremiumCommand)) {
            return false;
        }

        EditPremiumCommand otherEditPremiumCommand = (EditPremiumCommand) other;
        return index.equals(otherEditPremiumCommand.index)
                && editPremiumDescriptor.equals(otherEditPremiumCommand.editPremiumDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPremiumDescriptor", editPremiumDescriptor)
                .toString();
    }

    /**
     * Replaces Premium by name
     *
     * @param premiumList The premiums to replace with
     */
    private static PremiumList editPremium(Person personToEdit, PremiumList premiumList) {
        for (Premium premium : premiumList.premiumList) {
                personToEdit.getPremiumList().replace(premium);
        }

        return personToEdit.getPremiumList();
    }

    private static boolean validPremium(Person personToEdit, PremiumList premiumList) {
        for (Premium premium : premiumList.premiumList) {
            if(!personToEdit.getPremiumList().contains(premium)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Stores the premium details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPremiumDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Birthday birthday;
        private Set<Tag> tags;
        private PremiumList premiumList;

        public EditPremiumDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditPremiumDescriptor(EditPremiumDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setBirthday(toCopy.birthday);
            setTags(toCopy.tags);
            setPremium(toCopy.premiumList);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(premiumList);
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

        public Optional<PremiumList> getPremium() {
            return Optional.ofNullable(premiumList);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPremiumCommand.EditPremiumDescriptor)) {
                return false;
            }

            EditPremiumCommand.EditPremiumDescriptor otherEditPremiumDescriptor =
                    (EditPremiumCommand.EditPremiumDescriptor) other;

            return Objects.equals(name, otherEditPremiumDescriptor.name)
                    && Objects.equals(phone, otherEditPremiumDescriptor.phone)
                    && Objects.equals(email, otherEditPremiumDescriptor.email)
                    && Objects.equals(address, otherEditPremiumDescriptor.address)
                    && Objects.equals(birthday, otherEditPremiumDescriptor.birthday)
                    && Objects.equals(tags, otherEditPremiumDescriptor.tags)
                    && Objects.equals(premiumList, otherEditPremiumDescriptor.premiumList);
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
