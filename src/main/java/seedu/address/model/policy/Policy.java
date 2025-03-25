package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Policy in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Policy {

    // Identity fields
    private final PolicyLink policyLink;
    private final PolicyNumber policyNumber;
    private final PolicyName policyName;
    private final ProviderCompany providerCompany;

    /**
     * Every field must be present and not null.
     */
    public Policy(PolicyName policyName, PolicyNumber policyNumber,
            ProviderCompany providerCompany, PolicyLink policyLink) {
        requireAllNonNull(policyLink, policyNumber, policyName, providerCompany);
        this.policyLink = policyLink;
        this.policyNumber = policyNumber;
        this.policyName = policyName;
        this.providerCompany = providerCompany;
    }

    public PolicyLink getPolicyLink() {
        return policyLink;
    }

    public PolicyNumber getPolicyNumber() {
        return policyNumber;
    }

    public PolicyName getPolicyName() {
        return policyName;
    }

    public ProviderCompany getProviderCompany() {
        return providerCompany;
    }

    /**
     * Returns true if both policies have the same policy number.
     * This defines a weaker notion of equality between two policies.
     */
    public boolean isSamePolicy(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy != null
                && otherPolicy.getPolicyNumber().equals(getPolicyNumber());
    }

    /**
     * Returns true if both policies have the same identity and data fields.
     * This defines a stronger notion of equality between two policies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return policyLink.equals(otherPolicy.policyLink)
                && policyNumber.equals(otherPolicy.policyNumber)
                && policyName.equals(otherPolicy.policyName)
                && providerCompany.equals(otherPolicy.providerCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyLink, policyNumber, policyName, providerCompany);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("policyName", policyName)
                .add("policyNumber", policyNumber)
                .add("provider", providerCompany)
                .add("planType", policyLink)
                .toString();
    }
}
