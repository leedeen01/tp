package seedu.address.model.premium.exceptions;

public class DuplicatePremiumException extends RuntimeException {
    public DuplicatePremiumException() {
        super("Operation would result in duplicate premiums");
    }
}

