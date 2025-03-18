package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PremiumBook;
import seedu.address.model.ReadOnlyPremiumBook;
import seedu.address.model.premium.Premium;

/**
 * An Immutable PremiumBook that is serializable to JSON format.
 */
@JsonRootName(value = "premiumbook")
class JsonSerializablePremiumBook {

    public static final String MESSAGE_DUPLICATE_PREMIUM = "Premiums list contains duplicate premium(s).";

    private final List<JsonAdaptedPremiumObject> premiums = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePremiumBook} with the given premiums.
     */
    @JsonCreator
    public JsonSerializablePremiumBook(@JsonProperty("premiums") List<JsonAdaptedPremiumObject> premiums) {
        this.premiums.addAll(premiums);
    }

    /**
     * Converts a given {@code ReadOnlyPremiumBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePremiumBook}.
     */
    public JsonSerializablePremiumBook(ReadOnlyPremiumBook source) {
        premiums.addAll(source.getPremiumList().stream().map(JsonAdaptedPremiumObject::new).collect(Collectors.toList()));
    }

    /**
     * Converts this premium book into the model's {@code PremiumBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PremiumBook toModelType() throws IllegalValueException {
        PremiumBook premiumBook = new PremiumBook();
        for (JsonAdaptedPremiumObject jsonAdaptedPremium : premiums) {
            Premium premium = jsonAdaptedPremium.toModelType();
            if (premiumBook.hasPremium(premium)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PREMIUM);
            }
            premiumBook.addPremium(premium);
        }
        return premiumBook;
    }
}