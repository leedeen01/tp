package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of {@link Policy}.
 * Used for JSON serialization/deserialization of premium data.
 */
public class JsonAdaptedPremium {
    /**
     * The name of the premium.
     */
    final String premiumName;

    /**
     * The amount of the premium.
     */
    final Integer premiumAmount;

    /**
     * Constructs a {@code JsonAdaptedPremium} with the given premium details.
     *
     * @param premiumName The name of the premium.
     * @param premiumAmount The amount of the premium.
     */
    @JsonCreator
    public JsonAdaptedPremium(
            @JsonProperty("premiumName") String premiumName,
            @JsonProperty("premiumAmount") Integer premiumAmount
    ) {
        this.premiumName = premiumName;
        this.premiumAmount = premiumAmount;
    }
}

