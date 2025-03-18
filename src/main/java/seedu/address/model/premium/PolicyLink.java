package seedu.address.model.premium;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a policy website link in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicyLink(String)}
 */
public class PolicyLink {

    public static final String MESSAGE_CONSTRAINTS = "Policy links should be valid URLs";
    public static final String VALIDATION_REGEX = 
        "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|$!:,.;]*[-a-zA-Z0-9+&@#/%=~_|$]";
    
    public final String value;

    /**
     * Constructs a {@code PolicyLink}.
     *
     * @param policyLink A valid policy website link.
     */
    public PolicyLink(String policyLink) {
        requireNonNull(policyLink);
        checkArgument(isValidPolicyLink(policyLink), MESSAGE_CONSTRAINTS);
        value = policyLink;
    }

    /**
     * Returns true if a given string is a valid policy website link.
     */
    public static boolean isValidPolicyLink(String test) {
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
        if (!(other instanceof PolicyLink)) {
            return false;
        }

        PolicyLink otherPolicyLink = (PolicyLink) other;
        return value.equals(otherPolicyLink.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}