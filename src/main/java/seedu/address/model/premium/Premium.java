package seedu.address.model.premium;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Premium in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Premium {

    // Identity fields
    private final PolicyLink policyLink;
    private final PolicyNumber policyNumber;
    private final PremiumName premiumName;
    private final ProviderCompany providerCompany;

    /**
     * Every field must be present and not null.
     */
    public Premium(PremiumName premiumName, PolicyNumber policyNumber,
            ProviderCompany providerCompany, PolicyLink policyLink) {
        requireAllNonNull(policyLink, policyNumber, premiumName, providerCompany);
        this.policyLink = policyLink;
        this.policyNumber = policyNumber;
        this.premiumName = premiumName;
        this.providerCompany = providerCompany;
    }

    public PolicyLink getPolicyLink() {
        return policyLink;
    }

    public PolicyNumber getPolicyNumber() {
        return policyNumber;
    }

    public PremiumName getPremiumName() {
        return premiumName;
    }

    public ProviderCompany getProviderCompany() {
        return providerCompany;
    }

    /**
     * Returns true if both premiums have the same policy number.
     * This defines a weaker notion of equality between two premiums.
     */
    public boolean isSamePremium(Premium otherPremium) {
        if (otherPremium == this) {
            return true;
        }

        return otherPremium != null
                && otherPremium.getPolicyNumber().equals(getPolicyNumber());
    }

    /**
     * Returns true if both premiums have the same identity and data fields.
     * This defines a stronger notion of equality between two premiums.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Premium)) {
            return false;
        }

        Premium otherPremium = (Premium) other;
        return policyLink.equals(otherPremium.policyLink)
                && policyNumber.equals(otherPremium.policyNumber)
                && premiumName.equals(otherPremium.premiumName)
                && providerCompany.equals(otherPremium.providerCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyLink, policyNumber, premiumName, providerCompany);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("premiumName", premiumName)
                .add("policyNumber", policyNumber)
                .add("provider", providerCompany)
                .add("planType", policyLink)
                .toString();
    }
}
