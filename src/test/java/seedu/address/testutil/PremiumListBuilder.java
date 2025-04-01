package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;

/**
 * A utility class to help with building PremiumList objects.
 */
public class PremiumListBuilder {

    public static final String DEFAULT_PREMIUM = "LifeShield $200";

    private PremiumList premiumList;

    /**
     * Creates a {@code PremiumListBuilder} with an empty premium list.
     */
    public PremiumListBuilder() {
        premiumList = new PremiumList();
    }

    /**
     * Creates a {@code PremiumListBuilder} with the data of {@code premiumListToCopy}.
     */
    public PremiumListBuilder(PremiumList premiumListToCopy) {
        premiumList = premiumListToCopy;
    }

    /**
     * Adds a premium to the {@code PremiumList} that we are building.
     * Parses the premium string and adds it to the premium list.
     *
     * @param premiumString The premium string in the format "NAME $AMOUNT"
     * @return The updated PremiumListBuilder
     */
    public PremiumListBuilder withPremium(String premiumString) {
        try {
            PremiumList newPremium = ParserUtil.parsePremium(premiumString);
            // Assuming PremiumList has a method to add all premiums from another PremiumList
            for (Premium premium : newPremium.premiumList) {
                premiumList.add(premium);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid premium format: " + premiumString, e);
        }
        return this;
    }

    /**
     * Adds a premium directly to the {@code PremiumList} that we are building.
     *
     * @param premium The Premium object to add
     * @return The updated PremiumListBuilder
     */
    public PremiumListBuilder withPremium(Premium premium) {
        premiumList.add(premium);
        return this;
    }

    /**
     * Builds and returns the {@code PremiumList}.
     */
    public PremiumList build() {
        return premiumList;
    }
}