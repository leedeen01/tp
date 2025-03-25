package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

/**
 * Edits the details of an existing policy in the address book.
 */
public class EditPolicyCommand extends Command {

    public static final String COMMAND_WORD = "editpolicy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the policy identified "
            + "by the index number used in the displayed policy list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_POLICY_NAME + " POLICY_NAME] "
            + "[" + PREFIX_POLICY_NUMBER + " POLICY_NUMBER] "
            + "[" + PREFIX_PROVIDER_COMPANY + " PROVIDER_COMPANY] "
            + "[" + PREFIX_POLICY_LINK + " POLICY_LINK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_NAME + " New Policy Name "
            + PREFIX_PROVIDER_COMPANY + " ABC Insurance";

    public static final String MESSAGE_EDIT_POLICY_SUCCESS = "Edited Policy: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided for editing.";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists in the system.";

    private final Index index;
    private final EditPolicyDescriptor editPolicyDescriptor;

    /**
     * @param index of the policy in the filtered policy list to edit
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(Index index, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(index);
        requireNonNull(editPolicyDescriptor);

        this.index = index;
        this.editPolicyDescriptor = new EditPolicyDescriptor(editPolicyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
        }

        Policy policyToEdit = lastShownList.get(index.getZeroBased());
        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);

        if (!policyToEdit.isSamePolicy(editedPolicy) && model.hasPolicy(editedPolicy)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }

        model.setPolicy(policyToEdit, editedPolicy);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
        return new CommandResult(String.format(MESSAGE_EDIT_POLICY_SUCCESS, Messages.formatPolicy(editedPolicy)));
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policyToEdit}
     * edited with {@code editPolicyDescriptor}.
     */
    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        PolicyName updatedPolicyName = editPolicyDescriptor.getPolicyName().orElse(policyToEdit.getPolicyName());
        PolicyNumber updatedPolicyNumber =
            editPolicyDescriptor.getPolicyNumber().orElse(policyToEdit.getPolicyNumber());
        ProviderCompany updatedProviderCompany =
            editPolicyDescriptor.getProviderCompany().orElse(policyToEdit.getProviderCompany());
        PolicyLink updatedPolicyLink = editPolicyDescriptor.getPolicyLink().orElse(policyToEdit.getPolicyLink());

        return new Policy(updatedPolicyName, updatedPolicyNumber, updatedProviderCompany, updatedPolicyLink);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EditPolicyCommand
                && index.equals(((EditPolicyCommand) other).index)
                && editPolicyDescriptor.equals(((EditPolicyCommand) other).editPolicyDescriptor));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPolicyDescriptor", editPolicyDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private PolicyName policyName;
        private PolicyNumber policyNumber;
        private ProviderCompany providerCompany;
        private PolicyLink policyLink;

        public EditPolicyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
            setPolicyName(toCopy.policyName);
            setPolicyNumber(toCopy.policyNumber);
            setProviderCompany(toCopy.providerCompany);
            setPolicyLink(toCopy.policyLink);
        }

        public boolean isAnyFieldEdited() {
            return policyName != null || policyNumber != null || providerCompany != null || policyLink != null;
        }

        public void setPolicyName(PolicyName policyName) {
            this.policyName = policyName;
        }

        public Optional<PolicyName> getPolicyName() {
            return Optional.ofNullable(policyName);
        }

        public void setPolicyNumber(PolicyNumber policyNumber) {
            this.policyNumber = policyNumber;
        }

        public Optional<PolicyNumber> getPolicyNumber() {
            return Optional.ofNullable(policyNumber);
        }

        public void setProviderCompany(ProviderCompany providerCompany) {
            this.providerCompany = providerCompany;
        }

        public Optional<ProviderCompany> getProviderCompany() {
            return Optional.ofNullable(providerCompany);
        }

        public void setPolicyLink(PolicyLink policyLink) {
            this.policyLink = policyLink;
        }

        public Optional<PolicyLink> getPolicyLink() {
            return Optional.ofNullable(policyLink);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }
            EditPolicyDescriptor otherDescriptor = (EditPolicyDescriptor) other;
            return getPolicyName().equals(otherDescriptor.getPolicyName())
                    && getPolicyNumber().equals(otherDescriptor.getPolicyNumber())
                    && getProviderCompany().equals(otherDescriptor.getProviderCompany())
                    && getPolicyLink().equals(otherDescriptor.getPolicyLink());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("policyName", policyName)
                    .add("policyNumber", policyNumber)
                    .add("providerCompany", providerCompany)
                    .add("policyLink", policyLink)
                    .toString();
        }
    }
}
