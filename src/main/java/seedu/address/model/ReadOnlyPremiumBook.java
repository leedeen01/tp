package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.premium.Premium;

/**
 * A read-only interface for the PremiumBook
 */
public interface ReadOnlyPremiumBook {

    /**
     * Returns an unmodifiable view of the premiums list
     * This list will not contain any duplicate premiums
     */
    ObservableList<Premium> getPremiumList();
}