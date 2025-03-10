package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Represents an insurance premium with a name and an amount.
 * Guarantees: immutable; name and amount are valid as declared in {@link #isValidPremium(String, Integer)}
 */
public class Premium {

    public static final String MESSAGE_CONSTRAINTS = "Premium should be a valid name followed by a positive integer";

    /**
     * Predicate to validate that a premium value is a positive integer.
     */
    public static final Predicate<Integer> VALIDATION_PREDICATE = premium -> premium != null && premium >= 0;

    /**
     * Regular expression to validate the premium name.
     * Ensures the name is not empty and does not start with whitespace.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String premiumName;
    private Integer premiumAmount;

    /**
     * Constructs a Premium with the given name and amount.
     *
     * @param premiumName The name of the premium.
     * @param premiumAmount The amount of the premium.
     * @throws IllegalArgumentException if the given premium name or amount is invalid
     */
    public Premium(String premiumName, Integer premiumAmount) {
        if (isValidPremium(premiumName, premiumAmount)) {
            this.premiumName = premiumName;
            this.premiumAmount = premiumAmount;
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns the name of this premium.
     *
     * @return The premium name.
     */
    public String getPremiumName() {
        return premiumName;
    }

    /**
     * Returns the amount of this premium.
     *
     * @return The premium amount.
     */
    public Integer getPremiumAmount() {
        return premiumAmount;
    }

    /**
     * Returns true if a given string and integer are valid as a premium name and amount.
     *
     * @param testName The name to test.
     * @param testAmount The amount to test.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPremium(String testName, Integer testAmount) {
        return matches(testName, testAmount);
    }

    /**
     * Returns true if a given Premium has valid name and amount.
     *
     * @param test The Premium to test.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPremium(Premium test) {
        return matches(test.getPremiumName(), test.getPremiumAmount());
    }

    /**
     * Checks if the given premium name and amount match the validation criteria.
     *
     * @param testName The name to test.
     * @param testAmount The amount to test.
     * @return true if both name and amount are valid, false otherwise.
     * @throws NullPointerException if either the name or amount is null.
     */
    public static boolean matches(String testName, Integer testAmount) {
        if (testName == null || testAmount == null) {
            throw new NullPointerException(MESSAGE_CONSTRAINTS);
        }

        return VALIDATION_PREDICATE.test(testAmount) && testName.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a string representation of this premium in the format [name, amount].
     *
     * @return A string representation of this premium.
     */
    @Override
    public String toString() {
        return "[" + premiumName + ", " + premiumAmount + "]";
    }

    /**
     * Compares this premium with another object for equality.
     * Two premiums are equal if they have the same name and amount.
     *
     * @param other The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
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

        return (premiumName.equals(otherPremium.getPremiumName())
                && premiumAmount.equals(otherPremium.getPremiumAmount()));
    }

    /**
     * Returns the hash code of this premium.
     * The hash code is computed based on the hash codes of the name and amount.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return premiumName.hashCode() + premiumAmount.hashCode();
    }
}
