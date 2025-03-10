package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;

/**
 * Represents a person's premium membership or subscription level in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPremium(Integer)}
 */
public class PremiumList {

    /**
     * The premium value associated with a person.
     */
    public final ArrayList<Premium> premiumList =  new ArrayList<>();


    public PremiumList() {
    }


    public static boolean isValidPremium(String name, Integer amount) {
        return Premium.isValidPremium(name, amount);
    }


    public static boolean matches(String name, Integer amount) {
        return Premium.matches(name, amount);
    }

    public void addAll(PremiumList premiumList) {
        requireNonNull(premiumList);
        this.premiumList.addAll(premiumList.premiumList);
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

        // instanceof handles nulls
        if (!(other instanceof PremiumList)) {
            return false;
        }

        PremiumList otherPremiumList = (PremiumList) other;

        if(premiumList.size() != otherPremiumList.premiumList.size()) {
            return false;
        }

        for(int i = 0; i < premiumList.size(); i++) {
            if(!premiumList.get(i).equals(otherPremiumList.premiumList.get(i))) {
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
