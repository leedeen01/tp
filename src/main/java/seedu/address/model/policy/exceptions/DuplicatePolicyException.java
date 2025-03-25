package seedu.address.model.policy.exceptions;

/**
 * Signals that the operation will result in duplicate Policy
 * (Policy are considered duplicates if they have the same Policy identity).
 */
public class DuplicatePolicyException extends RuntimeException {
    public DuplicatePolicyException() {
        super("Operation would result in duplicate policies");
    }
}
