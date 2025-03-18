package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.premium.PolicyLink;
import seedu.address.model.premium.PolicyNumber;
import seedu.address.model.premium.Premium;
import seedu.address.model.premium.PremiumName;
import seedu.address.model.premium.ProviderCompany;

/**
 * Jackson-friendly version of {@link Premium}.
 */
class JsonAdaptedPremiumObject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Premium's %s field is missing!";

    private final String policyLink;
    private final String policyNumber;
    private final String premiumName;
    private final String providerCompany;

    /**
     * Constructs a {@code JsonAdaptedPremium} with the given premium details.
     */
    @JsonCreator
    public JsonAdaptedPremiumObject(@JsonProperty("policyLink") String policyLink,
            @JsonProperty("policyNumber") String policyNumber,
            @JsonProperty("premiumName") String premiumName,
            @JsonProperty("providerCompany") String providerCompany) {
        this.policyLink = policyLink;
        this.policyNumber = policyNumber;
        this.premiumName = premiumName;
        this.providerCompany = providerCompany;
    }

    /**
     * Converts a given {@code Premium} into this class for Jackson use.
     */
    public JsonAdaptedPremiumObject(Premium source) {
        policyLink = source.getPolicyLink().value;
        policyNumber = source.getPolicyNumber().value;
        premiumName = source.getPremiumName().premiumName;
        providerCompany = source.getProviderCompany().value;
    }

    /**
     * Converts this Jackson-friendly adapted premium object into the model's
     * {@code Premium} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted premium.
     */
    public Premium toModelType() throws IllegalValueException {
        if (policyLink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyLink"));
        }
        if (!PolicyLink.isValidPolicyLink(policyLink)) {
            throw new IllegalValueException(PolicyLink.MESSAGE_CONSTRAINTS);
        }
        final PolicyLink modelPolicyLink = new PolicyLink(policyLink);

        if (policyNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyNumber"));
        }
        if (!PolicyNumber.isValidPolicyNumber(policyNumber)) {
            throw new IllegalValueException(PolicyNumber.MESSAGE_CONSTRAINTS);
        }
        final PolicyNumber modelPolicyNumber = new PolicyNumber(policyNumber);

        if (premiumName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PremiumName"));
        }
        if (!PremiumName.isValidPremiumName(premiumName)) {
            throw new IllegalValueException(PremiumName.MESSAGE_CONSTRAINTS);
        }
        final PremiumName modelPremiumName = new PremiumName(premiumName);

        if (providerCompany == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ProviderCompany"));
        }
        if (!ProviderCompany.isValidProviderCompany(providerCompany)) {
            throw new IllegalValueException(ProviderCompany.MESSAGE_CONSTRAINTS);
        }
        final ProviderCompany modelProviderCompany = new ProviderCompany(providerCompany);

        return new Premium(modelPremiumName, modelPolicyNumber, modelProviderCompany, modelPolicyLink);
    }

    // Getters for the fields
    public String getPolicyLink() {
        return policyLink;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPremiumName() {
        return premiumName;
    }

    public String getProviderCompany() {
        return providerCompany;
    }
}