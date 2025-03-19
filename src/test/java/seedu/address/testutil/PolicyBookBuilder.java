package seedu.address.testutil;

import seedu.address.model.PolicyBook;
import seedu.address.model.policy.Policy;

/**
 * A utility class to help with building PolicyBook objects.
 * Example usage: <br>
 * {@code PolicyBook pb = new PolicyBookBuilder().withPolicy("LifeShield", "100").build();}
 */
public class PolicyBookBuilder {

    private PolicyBook policyBook;

    public PolicyBookBuilder() {
        policyBook = new PolicyBook();
    }

    public PolicyBookBuilder(PolicyBook policyBook) {
        this.policyBook = policyBook;
    }

    /**
     * Adds a new {@code Policy} to the {@code PolicyBook} that we are building.
     */
    public PolicyBookBuilder withPolicy(Policy policy) {
        policyBook.addPolicy(policy);
        return this;
    }

    /**
     * Adds a new {@code Policy} with the given details to the {@code PolicyBook}
     * that we are building.
     */
    public PolicyBookBuilder withPolicy(String policyNumber,
            String policyName, String providerCompany, String policyLink) {
        Policy policy = new PolicyBuilder()
                .withPolicyNumber(policyNumber)
                .withPolicyName(policyName)
                .withProviderCompany(providerCompany)
                .withPolicyLink(policyLink)
                .build();
        policyBook.addPolicy(policy);
        return this;
    }

    public PolicyBook build() {
        return policyBook;
    }
}
