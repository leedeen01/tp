package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's name in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidPolicyName(String)}
 */
public class PolicyName {

    public static final String MESSAGE_CONSTRAINTS =
        "Policy Name should only contain alphanumeric characters and spaces,"
        + " not be longer than 50 characters,"
        + " and it should not be blank";

    /*
     * The first character of the policy name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]{0,49}";

    public final String policyName;

    /**
     * Constructs a {@code PolicyName}.
     *
     * @param policyName A valid policy name.
     */
    public PolicyName(String policyName) {
        requireNonNull(policyName);
        checkArgument(isValidPolicyName(policyName), MESSAGE_CONSTRAINTS);
        this.policyName = policyName;
    }

    /**
     * Returns true if a given string is a valid policy name.
     */
    public static boolean isValidPolicyName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return policyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyName)) {
            return false;
        }

        PolicyName otherPolicyName = (PolicyName) other;
        return policyName.equals(otherPolicyName.policyName);
    }

    @Override
    public int hashCode() {
        return policyName.hashCode();
    }
}
