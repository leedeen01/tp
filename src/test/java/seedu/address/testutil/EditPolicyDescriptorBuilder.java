package seedu.address.testutil;

import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

/**
 * A utility class to help with building EditPolicyDescriptor objects.
 */
public class EditPolicyDescriptorBuilder {

    private EditPolicyDescriptor descriptor;

    /**
     * Creates a new EditPolicyDescriptorBuilder with an empty descriptor.
     */
    public EditPolicyDescriptorBuilder() {
        descriptor = new EditPolicyDescriptor();
    }

    /**
     * Creates a new EditPolicyDescriptorBuilder with a copy of the given descriptor.
     *
     * @param descriptor The EditPolicyDescriptor to copy.
     */
    public EditPolicyDescriptorBuilder(EditPolicyDescriptor descriptor) {
        this.descriptor = new EditPolicyDescriptor(descriptor);
    }

    /**
     * Creates a new EditPolicyDescriptorBuilder with fields containing the given policy's details.
     *
     * @param policy The Policy whose details will be used to populate the descriptor.
     */
    public EditPolicyDescriptorBuilder(Policy policy) {
        descriptor = new EditPolicyDescriptor();
        descriptor.setPolicyName(policy.getPolicyName());
        descriptor.setPolicyNumber(policy.getPolicyNumber());
        descriptor.setProviderCompany(policy.getProviderCompany());
        descriptor.setPolicyLink(policy.getPolicyLink());
    }

    /**
     * Sets the {@code PolicyName} of the {@code EditPolicyDescriptor} that we are building.
     *
     * @param policyName The policy name to set.
     * @return This builder.
     */
    public EditPolicyDescriptorBuilder withPolicyName(String policyName) {
        descriptor.setPolicyName(new PolicyName(policyName));
        return this;
    }

    /**
     * Sets the {@code PolicyNumber} of the {@code EditPolicyDescriptor} that we are building.
     *
     * @param policyNumber The policy number to set.
     * @return This builder.
     */
    public EditPolicyDescriptorBuilder withPolicyNumber(String policyNumber) {
        descriptor.setPolicyNumber(new PolicyNumber(policyNumber));
        return this;
    }

    /**
     * Sets the {@code ProviderCompany} of the {@code EditPolicyDescriptor} that we are building.
     *
     * @param providerCompany The provider company to set.
     * @return This builder.
     */
    public EditPolicyDescriptorBuilder withProviderCompany(String providerCompany) {
        descriptor.setProviderCompany(new ProviderCompany(providerCompany));
        return this;
    }

    /**
     * Sets the {@code PolicyLink} of the {@code EditPolicyDescriptor} that we are building.
     *
     * @param policyLink The policy link to set.
     * @return This builder.
     */
    public EditPolicyDescriptorBuilder withPolicyLink(String policyLink) {
        descriptor.setPolicyLink(new PolicyLink(policyLink));
        return this;
    }

    /**
     * Builds and returns the configured EditPolicyDescriptor.
     *
     * @return The configured EditPolicyDescriptor.
     */
    public EditPolicyDescriptor build() {
        return descriptor;
    }
}
