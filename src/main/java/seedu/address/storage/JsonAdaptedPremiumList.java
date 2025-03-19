package seedu.address.storage;

import java.util.ArrayList;

import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;

/**
 * Jackson-friendly version of {@link PremiumList}.
 * This class is used for JSON serialization/deserialization of the premium list.
 */
public class JsonAdaptedPremiumList {

    /**
     * List of JSON-adapted premiums.
     */
    final ArrayList<JsonAdaptedPremium> premiumList = new ArrayList<>();

    /**
     * Constructs an empty JsonAdaptedPremiumList.
     */
    public JsonAdaptedPremiumList() {}

    /**
     * Adds a JsonAdaptedPremium to this premium list.
     *
     * @param premium The JsonAdaptedPremium to add.
     */
    public void add(JsonAdaptedPremium premium) {
        premiumList.add(premium);
    }

    /**
     * Adds a premium to this premium list from a string representation.
     * The string format should be "[name] [amount]".
     *
     * @param premium String representation of a premium.
     */
    public void add(String premium) {
        String[] input = premium.split(" ");
        premiumList.add(new JsonAdaptedPremium(input[0], Integer.parseInt(input[1])));
    }

    /**
     * Adds all premiums from a model PremiumList to this premium list.
     *
     * @param premiumList The model PremiumList to add from.
     */
    public void addAll(PremiumList premiumList) {
        premiumList.premiumList.stream().forEach(premium -> {
            this.premiumList.add(new JsonAdaptedPremium(premium.getPremiumName(), premium.getPremiumAmount()));
        });
    }

    /**
     * Converts this Jackson-friendly adapted premium list into the model's {@code PremiumList} object.
     *
     * @return The model PremiumList object.
     */
    public PremiumList toModelType() {
        PremiumList modelPremiumList = new PremiumList();
        for (JsonAdaptedPremium premium : premiumList) {
            modelPremiumList.add(new Premium(premium.premiumName, premium.premiumAmount));
        }
        return modelPremiumList;
    }

    /**
     * Checks if this premium list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return premiumList.isEmpty();
    }
}

