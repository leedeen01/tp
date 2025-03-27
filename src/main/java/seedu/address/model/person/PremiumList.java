package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a collection of insurance premiums that a person has purchased.
 * Guarantees: immutable; is valid as declared in {@link #isValidPremium(PremiumList)}
 */
public class PremiumList {

    public static final String MESSAGE_CONSTRAINTS =
            "Premium list has no valid premiums in the format [premium name, premium amount]";

    /**
     * The list of insurance premiums associated with a person.
     */
    public final ArrayList<Premium> premiumList = new ArrayList<>();

    /**
     * Constructs an empty PremiumList.
     */
    public PremiumList() {
    }

    /**
     * Creates a copy of the given PremiumList.
     *
     * @param premiumList The PremiumList to copy.
     */
    public PremiumList(PremiumList premiumList) {
        requireNonNull(premiumList);
        if (PremiumList.isValidPremium(premiumList)) {
            this.premiumList.addAll(premiumList.premiumList);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Creates a PremiumList with the given list of Premiums.
     *
     * @param premiumList The list of Premiums to add to the PremiumList.
     */
    public PremiumList(ArrayList<Premium> premiumList) {
        requireNonNull(premiumList);
        this.premiumList.addAll(premiumList);
    }

    /**
     * Creates a PremiumList with the given Premium.
     *
     * @param premium The Premium to add to the PremiumList.
     */
    public PremiumList(Premium premium) {
        requireNonNull(premium);
        if (Premium.isValidPremium(premium)) {
            premiumList.add(premium);
        } else {
            throw new IllegalArgumentException(Premium.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks whether the PremiumList contains only valid Premium objects.
     *
     * @param test The PremiumList to test.
     * @return true if all Premiums in the list are valid, false otherwise.
     */
    public static boolean isValidPremium(PremiumList test) {
        for (Premium p : test.premiumList) {
            if (!Premium.isValidPremium(p.getPremiumName(), p.getPremiumAmount())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given Premium name and amount match any in the list.
     *
     * @param name The name of the Premium.
     * @param amount The amount of the Premium.
     * @return true if a match is found, false otherwise.
     */
    public static boolean matches(String name, Integer amount) {
        return Premium.matches(name, amount);
    }

    /**
     * Adds the given Premium to the PremiumList.
     *
     * @param premium The Premium to add.
     */
    public void add(Premium premium) {
        requireNonNull(premium);
        if (Premium.isValidPremium(premium)) {
            premiumList.add(premium);
        } else {
            throw new IllegalArgumentException(Premium.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Adds a Premium object from a string representation of a Premium.
     *
     * @param premium The string representation of the Premium.
     */
    public void add(String premium) {
        requireNonNull(premium);
        try {
            addAll(ParserUtil.parsePremium(premium));
        } catch (ParseException e) {
            throw new IllegalArgumentException(Premium.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Removes the given Premium from the PremiumList.
     *
     * @param premium The Premium to remove.
     */
    public void remove(Premium premium) {
        if (contains(premium)) {
            premiumList.removeIf(p -> p.getPremiumName().equals(premium.getPremiumName()));
        }
    }

    /**
     * Checks if the PremiumList contains the given Premium.
     *
     * @param premium The Premium to check.
     * @return true if the Premium is in the list, false otherwise.
     */
    public boolean contains(Premium premium) {
        requireNonNull(premium.getPremiumName());
        for (Premium p : premiumList) {
            if (p.getPremiumName().equals(premium.getPremiumName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds all the Premiums from another PremiumList.
     *
     * @param premiumList The PremiumList to add.
     */
    public void addAll(PremiumList premiumList) {
        requireNonNull(premiumList);
        if (PremiumList.isValidPremium(premiumList)) {
            this.premiumList.addAll(premiumList.premiumList);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Replaces Premium by name
     *
     * @param premium The premium to replace with
     */
    public void replace(Premium premium) {
        for (Premium p : premiumList) {
            if (p.getPremiumName().equals(premium.getPremiumName())) {
                premiumList.remove(p);
                premiumList.add(premium);
                break;
            }
        }
    }


    /**
     * Checks if the PremiumList is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return premiumList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Premium p : premiumList) {
            builder.append(p.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PremiumList)) {
            return false;
        }

        PremiumList otherPremiumList = (PremiumList) other;

        if (premiumList.size() != otherPremiumList.premiumList.size()) {
            return false;
        }

        for (int i = 0; i < premiumList.size(); i++) {
            if (!premiumList.get(i).equals(otherPremiumList.premiumList.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return premiumList.hashCode();
    }
}
