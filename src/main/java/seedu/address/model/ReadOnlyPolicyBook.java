package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.policy.Policy;

/**
 * A read-only interface for the PolicyBook
 */
public interface ReadOnlyPolicyBook {

    /**
     * Returns an unmodifiable view of the policy list
     * This list will not contain any duplicate policy
     */
    ObservableList<Policy> getPolicyList();
}
