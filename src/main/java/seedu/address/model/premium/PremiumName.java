package seedu.address.model.premium;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Premium's name in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidPremiumName(String)}
 */
public class PremiumName {

    public static final String MESSAGE_CONSTRAINTS = 
        "Premium names should only contain alphanumeric characters and spaces,"
        + " and it should not be blank";

    /*
     * The first character of the premium name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String premiumName;

    /**
     * Constructs a {@code PremiumName}.
     *
     * @param premiumName A valid premium name.
     */
    public PremiumName(String premiumName) {
        requireNonNull(premiumName);
        checkArgument(isValidPremiumName(premiumName), MESSAGE_CONSTRAINTS);
        this.premiumName = premiumName;
    }

    /**
     * Returns true if a given string is a valid premium name.
     */
    public static boolean isValidPremiumName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return premiumName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PremiumName)) {
            return false;
        }

        PremiumName otherPremiumName = (PremiumName) other;
        return premiumName.equals(otherPremiumName.premiumName);
    }

    @Override
    public int hashCode() {
        return premiumName.hashCode();
    }
}