package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.premium.Premium;
import seedu.address.model.premium.UniquePremiumList;

/**
 * Wraps all data at the premium-book level
 * Duplicates are not allowed (by .isSamePremium comparison)
 */
public class PremiumBook implements ReadOnlyPremiumBook {

    private final UniquePremiumList premiums;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        premiums = new UniquePremiumList();
    }

    public PremiumBook() {}

    /**
     * Creates a PremiumBook using the Premiums in the {@code toBeCopied}
     */
    public PremiumBook(ReadOnlyPremiumBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the premium list with {@code premiums}.
     * {@code premiums} must not contain duplicate premiums.
     */
    public void setPremiums(List<Premium> premiums) {
        this.premiums.setPremiums(premiums);
    }

    /**
     * Resets the existing data of this {@code PremiumBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPremiumBook newData) {
        requireNonNull(newData);

        setPremiums(newData.getPremiumList());
    }

    //// premium-level operations

    /**
     * Returns true if a premium with the same identity as {@code premium} exists in the premium book.
     */
    public boolean hasPremium(Premium premium) {
        requireNonNull(premium);
        return premiums.contains(premium);
    }

    /**
     * Adds a premium to the premium book.
     * The premium must not already exist in the premium book.
     */
    public void addPremium(Premium p) {
        premiums.add(p);
    }

    /**
     * Replaces the given premium {@code target} in the list with {@code editedPremium}.
     * {@code target} must exist in the premium book.
     * The premium identity of {@code editedPremium} must not be the same as another existing premium in the premium book.
     */
    public void setPremium(Premium target, Premium editedPremium) {
        requireNonNull(editedPremium);

        premiums.setPremium(target, editedPremium);
    }

    /**
     * Removes {@code key} from this {@code PremiumBook}.
     * {@code key} must exist in the premium book.
     */
    public void removePremium(Premium key) {
        premiums.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("premiums", premiums)
                .toString();
    }

    @Override
    public ObservableList<Premium> getPremiumList() {
        return premiums.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PremiumBook)) {
            return false;
        }

        PremiumBook otherPremiumBook = (PremiumBook) other;
        return premiums.equals(otherPremiumBook.premiums);
    }

    @Override
    public int hashCode() {
        return premiums.hashCode();
    }
}