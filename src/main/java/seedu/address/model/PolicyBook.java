package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.UniquePolicyList;

/**
 * Wraps all data at the policy-book level
 * Duplicates are not allowed (by .isSamePolicy comparison)
 */
public class PolicyBook implements ReadOnlyPolicyBook {

    private final UniquePolicyList polycies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        polycies = new UniquePolicyList();
    }

    public PolicyBook() {}

    /**
     * Creates a PolicyBook using the Policies in the {@code toBeCopied}
     */
    public PolicyBook(ReadOnlyPolicyBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the policy list with {@code polycies}.
     * {@code polycies} must not contain duplicate polycies.
     */
    public void setPolicys(List<Policy> polycies) {
        this.polycies.setPolicy(polycies);
    }

    /**
     * Resets the existing data of this {@code PolicyBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPolicyBook newData) {
        requireNonNull(newData);

        setPolicys(newData.getPolicyList());
    }

    //// policy-level operations

    /**
     * Returns true if a policy with the same identity as {@code policy} exists in the policy book.
     */
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return polycies.contains(policy);
    }

    /**
     * Adds a policy to the policy book.
     * The policy must not already exist in the policy book.
     */
    public void addPolicy(Policy p) {
        polycies.add(p);
    }

    /**
     * Replaces the given policy {@code target} in the list with {@code editedPolicy}.
     * {@code target} must exist in the policy book.
     * The policy identity of {@code editedPolicy} must not be the same as another
     * existing policy in the policy book.
     */
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireNonNull(editedPolicy);

        polycies.setPolicy(target, editedPolicy);
    }

    /**
     * Removes {@code key} from this {@code PolicyBook}.
     * {@code key} must exist in the policy book.
     */
    public void removePolicy(Policy key) {
        polycies.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("polycies", polycies)
                .toString();
    }

    @Override
    public ObservableList<Policy> getPolicyList() {
        return polycies.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyBook)) {
            return false;
        }

        PolicyBook otherPolicyBook = (PolicyBook) other;
        return polycies.equals(otherPolicyBook.polycies);
    }

    @Override
    public int hashCode() {
        return polycies.hashCode();
    }
}
