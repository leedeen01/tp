package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PolicyBook;
import seedu.address.model.policy.Policy;

/**
 * A utility class containing a list of {@code Premium} objects to be used in tests.
 */
public class TypicalPolicy {

    public static final Policy LIFE_SHIELD = new PolicyBuilder()
            .withPolicyNumber("POL123")
            .withPolicyName("LifeShield")
            .withProviderCompany("ShieldCorp")
            .withPolicyLink("https://www.shieldcorp.com/policy123")
            .build();

    public static final Policy HEALTH_2040 = new PolicyBuilder()
            .withPolicyNumber("POL456")
            .withPolicyName("Health2040")
            .withProviderCompany("HealthPlus")
            .withPolicyLink("https://www.healthplus.com/policy456")
            .build();

    public static final Policy ELDER_SHIELD = new PolicyBuilder()
            .withPolicyNumber("POL789")
            .withPolicyName("ElderShield")
            .withProviderCompany("ShieldCorp")
            .withPolicyLink("https://www.shieldcorp.com/policy789")
            .build();

    public static final Policy GREAT_INVESTMENT = new PolicyBuilder()
            .withPolicyNumber("POL101")
            .withPolicyName("GreatInvestment")
            .withProviderCompany("InvestCorp")
            .withPolicyLink("https://www.investcorp.com/policy101")
            .build();

    public static final Policy ETF_BONDS = new PolicyBuilder()
            .withPolicyNumber("POL112")
            .withPolicyName("ETFBonds")
            .withProviderCompany("BondPlus")
            .withPolicyLink("https://www.bondplus.com/policy112")
            .build();

    // Manually added - Premium's details found in {@code CommandTestUtil}
    public static final Policy VALID_PREMIUM = new PolicyBuilder()
            .withPolicyNumber(VALID_POLICY_NUMBER)
            .withPolicyName(VALID_POLICY_NAME)
            .withProviderCompany(VALID_PROVIDER_COMPANY)
            .withPolicyLink(VALID_POLICY_LINK)
            .build();

    private TypicalPolicy() {} // prevents instantiation

    /**
     * Returns a list of typical premiums.
     */
    public static List<Policy> getTypicalPolicy() {
        return new ArrayList<>(Arrays.asList(LIFE_SHIELD, HEALTH_2040, ELDER_SHIELD, GREAT_INVESTMENT, ETF_BONDS));
    }

    /**
     * Returns a {@code PolicyBook} with all the typical premiums.
     */
    public static PolicyBook getTypicalPolicyBook() {
        PolicyBook pb = new PolicyBook();
        for (Policy premium : getTypicalPolicy()) {
            pb.addPolicy(premium);
        }
        return pb;
    }
}
