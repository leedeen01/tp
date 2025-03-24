package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPolicy.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

public class JsonAdaptedPolicyTest {
    private static final String INVALID_POLICY_LINK = "invalid_link"; // assume invalid format
    private static final String INVALID_POLICY_NUMBER = "123"; // e.g., policy number should start with "POL"
    private static final String INVALID_POLICY_NAME = ""; // empty name not allowed
    private static final String INVALID_PROVIDER_COMPANY = ""; // empty provider not allowed

    private static final String VALID_POLICY_LINK = "https://www.shieldcorp.com/policy123";
    private static final String VALID_POLICY_NUMBER = "POL123";
    private static final String VALID_POLICY_NAME = "LifeShield";
    private static final String VALID_PROVIDER_COMPANY = "ShieldCorp";

    @Test
    public void toModelType_validPolicyDetails_returnsPolicy() throws Exception {
        Policy policy = new Policy(new PolicyName(VALID_POLICY_NAME),
                new PolicyNumber(VALID_POLICY_NUMBER),
                new ProviderCompany(VALID_PROVIDER_COMPANY),
                new PolicyLink(VALID_POLICY_LINK));
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(policy);
        assertEquals(policy, adaptedPolicy.toModelType());
    }

    @Test
    public void toModelType_invalidPolicyLink_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(INVALID_POLICY_LINK, VALID_POLICY_NUMBER,
                VALID_POLICY_NAME, VALID_PROVIDER_COMPANY);
        String expectedMessage = PolicyLink.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyLink_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(null, VALID_POLICY_NUMBER,
                VALID_POLICY_NAME, VALID_PROVIDER_COMPANY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyLink");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyNumber_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, null,
                VALID_POLICY_NAME, VALID_PROVIDER_COMPANY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyNumber");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_invalidPolicyName_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, VALID_POLICY_NUMBER,
                INVALID_POLICY_NAME, VALID_PROVIDER_COMPANY);
        String expectedMessage = PolicyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyName_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, VALID_POLICY_NUMBER,
                null, VALID_PROVIDER_COMPANY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "PolicyName");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_invalidProviderCompany_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, VALID_POLICY_NUMBER,
                VALID_POLICY_NAME, INVALID_PROVIDER_COMPANY);
        String expectedMessage = ProviderCompany.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void toModelType_nullProviderCompany_throwsIllegalValueException() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, VALID_POLICY_NUMBER,
                VALID_POLICY_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "ProviderCompany");
        assertThrows(IllegalValueException.class, expectedMessage, adaptedPolicy::toModelType);
    }

    @Test
    public void getMethods_validInputs_returnCorrectValues() {
        JsonAdaptedPolicy adaptedPolicy = new JsonAdaptedPolicy(VALID_POLICY_LINK, VALID_POLICY_NUMBER,
                VALID_POLICY_NAME, VALID_PROVIDER_COMPANY);
        assertEquals(VALID_POLICY_LINK, adaptedPolicy.getPolicyLink());
        assertEquals(VALID_POLICY_NUMBER, adaptedPolicy.getPolicyNumber());
        assertEquals(VALID_POLICY_NAME, adaptedPolicy.getPolicyName());
        assertEquals(VALID_PROVIDER_COMPANY, adaptedPolicy.getProviderCompany());
    }
}
