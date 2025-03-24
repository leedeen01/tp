package seedu.address.testutil;

import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

/**
 * A utility class to help with building Policy objects.
 */
public class PolicyBuilder {

    public static final String DEFAULT_POLICY_NUMBER = "POL123";
    public static final String DEFAULT_PREMIUM_NAME = "LifeShield";
    public static final String DEFAULT_PROVIDER_COMPANY = "ShieldCorp";
    public static final String DEFAULT_POLICY_LINK = "https://www.shieldcorp.com/policy123";

    private PolicyNumber policyNumber;
    private PolicyName policyName;
    private ProviderCompany providerCompany;
    private PolicyLink policyLink;

    /**
     * Creates a {@code PolicyBuilder} with the default details.
     */
    public PolicyBuilder() {
        policyNumber = new PolicyNumber(DEFAULT_POLICY_NUMBER);
        policyName = new PolicyName(DEFAULT_PREMIUM_NAME);
        providerCompany = new ProviderCompany(DEFAULT_PROVIDER_COMPANY);
        policyLink = new PolicyLink(DEFAULT_POLICY_LINK);
    }

    /**
     * Initializes the PolicyBuilder with the data of {@code policyToCopy}.
     */
    public PolicyBuilder(Policy policyToCopy) {
        policyNumber = policyToCopy.getPolicyNumber();
        policyName = policyToCopy.getPolicyName();
        providerCompany = policyToCopy.getProviderCompany();
        policyLink = policyToCopy.getPolicyLink();
    }

    /**
     * Sets the {@code PolicyNumber} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withPolicyNumber(String policyNumber) {
        this.policyNumber = new PolicyNumber(policyNumber);
        return this;
    }

    /**
     * Sets the {@code PremiumName} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withPolicyName(String policyName) {
        this.policyName = new PolicyName(policyName);
        return this;
    }

    /**
     * Sets the {@code ProviderCompany} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withProviderCompany(String providerCompany) {
        this.providerCompany = new ProviderCompany(providerCompany);
        return this;
    }

    /**
     * Sets the {@code PolicyLink} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withPolicyLink(String policyLink) {
        this.policyLink = new PolicyLink(policyLink);
        return this;
    }

    public Policy build() {
        return new Policy(policyName, policyNumber, providerCompany, policyLink);
    }

}
