package seedu.address.model.premium;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a provider company in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProviderCompany(String)}
 */
public class ProviderCompany {

    public static final String MESSAGE_CONSTRAINTS = 
        "Provider company names should not be blank";

    /*
     * The first character of the provider company name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code ProviderCompany}.
     *
     * @param providerCompany A valid provider company name.
     */
    public ProviderCompany(String providerCompany) {
        requireNonNull(providerCompany);
        checkArgument(isValidProviderCompany(providerCompany), MESSAGE_CONSTRAINTS);
        value = providerCompany;
    }

    /**
     * Returns true if a given string is a valid provider company name.
     */
    public static boolean isValidProviderCompany(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProviderCompany)) {
            return false;
        }

        ProviderCompany otherProviderCompany = (ProviderCompany) other;
        return value.equals(otherProviderCompany.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}