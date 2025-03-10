package seedu.address.model.person;

import java.util.function.Predicate;

public class Premium {

    public static final String MESSAGE_CONSTRAINTS = "Premium should be a valid name followed by a positive integer";

    /**
     * Predicate to validate that a premium value is a positive integer.
     */
    public static final Predicate<Integer> VALIDATION_PREDICATE = premium -> premium != null && premium >= 0;

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public String premiumName;
    public Integer premiumAmount;

    public Premium(String premiumName, Integer premiumAmount) {
        this.premiumName = premiumName;
        this.premiumAmount = premiumAmount;
    }

    public String getPremiumName() {
        return premiumName;
    }

    public Integer getPremiumAmount() {
        return premiumAmount;
    }

    public static boolean isValidPremium(String testName, Integer testAmount) {
        return matches(testName, testAmount);
    }

    public static boolean matches(String testName, Integer testAmount) {
        if(testName == null || testAmount == null) {
            throw new NullPointerException(MESSAGE_CONSTRAINTS);
        }

        return VALIDATION_PREDICATE.test(testAmount) && testName.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "[" + premiumName + ", " + premiumAmount + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Premium)) {
            return false;
        }

        Premium otherPremium = (Premium) other;

        return (premiumName.equals(otherPremium.getPremiumName()) && premiumAmount.equals(otherPremium.getPremiumAmount()));
    }

    @Override
    public int hashCode() {
        return premiumName.hashCode() + premiumAmount.hashCode();
    }



}
