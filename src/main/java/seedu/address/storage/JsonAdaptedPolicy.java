package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

/**
 * Jackson-friendly version of {@link Policy}.
 */
class JsonAdaptedPolicy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy's %s field is missing!";

    private final String policyLink;
    private final String policyNumber;
    private final String policyName;
    private final String providerCompany;

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("policyLink") String policyLink,
            @JsonProperty("policyNumber") String policyNumber,
            @JsonProperty("policyName") String policyName,
            @JsonProperty("providerCompany") String providerCompany) {
        this.policyLink = policyLink;
        this.policyNumber = policyNumber;
        this.policyName = policyName;
        this.providerCompany = providerCompany;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        policyLink = source.getPolicyLink().value;
        policyNumber = source.getPolicyNumber().value;
        policyName = source.getPolicyName().policyName;
        providerCompany = source.getProviderCompany().value;
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's
     * {@code Policy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted policy.
     */
    public Policy toModelType() throws IllegalValueException {
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

        if (policyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyName"));
        }
        if (!PolicyName.isValidPolicyName(policyName)) {
            throw new IllegalValueException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        final PolicyName modelPolicyName = new PolicyName(policyName);

        if (providerCompany == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ProviderCompany"));
        }
        if (!ProviderCompany.isValidProviderCompany(providerCompany)) {
            throw new IllegalValueException(ProviderCompany.MESSAGE_CONSTRAINTS);
        }
        final ProviderCompany modelProviderCompany = new ProviderCompany(providerCompany);

        return new Policy(modelPolicyName, modelPolicyNumber, modelProviderCompany, modelPolicyLink);
    }

    // Getters for the fields
    public String getPolicyLink() {
        return policyLink;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getProviderCompany() {
        return providerCompany;
    }
}
