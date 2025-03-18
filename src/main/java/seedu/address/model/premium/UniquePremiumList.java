package seedu.address.model.premium;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.premium.exceptions.DuplicatePremiumException;
import seedu.address.model.premium.exceptions.PremiumNotFoundException;

/**
 * A list of premiums that enforces uniqueness between its elements and does not allow nulls.
 * A premium is considered unique by comparing using {@code Premium#isSamePremium(Premium)}. As such, adding and updating of
 * premiums uses Premium#isSamePremium(Premium) for equality so as to ensure that the premium being added or updated is
 * unique in terms of identity in the UniquePremiumList. However, the removal of a premium uses Premium#equals(Object) so
 * as to ensure that the premium with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Premium#isSamePremium(Premium)
 */
public class UniquePremiumList implements Iterable<Premium> {

    private final ObservableList<Premium> internalList = FXCollections.observableArrayList();
    private final ObservableList<Premium> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent premium as the given argument.
     */
    public boolean contains(Premium toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePremium);
    }

    /**
     * Adds a premium to the list.
     * The premium must not already exist in the list.
     */
    public void add(Premium toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePremiumException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the premium {@code target} in the list with {@code editedPremium}.
     * {@code target} must exist in the list.
     * The premium identity of {@code editedPremium} must not be the same as another existing premium in the list.
     */
    public void setPremium(Premium target, Premium editedPremium) {
        requireAllNonNull(target, editedPremium);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PremiumNotFoundException();
        }

        if (!target.isSamePremium(editedPremium) && contains(editedPremium)) {
            throw new DuplicatePremiumException();
        }

        internalList.set(index, editedPremium);
    }

    /**
     * Removes the equivalent premium from the list.
     * The premium must exist in the list.
     */
    public void remove(Premium toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PremiumNotFoundException();
        }
    }

    public void setPremiums(UniquePremiumList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code premiums}.
     * {@code premiums} must not contain duplicate premiums.
     */
    public void setPremiums(List<Premium> premiums) {
        requireAllNonNull(premiums);
        if (!premiumsAreUnique(premiums)) {
            throw new DuplicatePremiumException();
        }

        internalList.setAll(premiums);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Premium> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Premium> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePremiumList)) {
            return false;
        }

        UniquePremiumList otherUniquePremiumList = (UniquePremiumList) other;
        return internalList.equals(otherUniquePremiumList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code premiums} contains only unique premiums.
     */
    private boolean premiumsAreUnique(List<Premium> premiums) {
        for (int i = 0; i < premiums.size() - 1; i++) {
            for (int j = i + 1; j < premiums.size(); j++) {
                if (premiums.get(i).isSamePremium(premiums.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
