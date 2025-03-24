package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyName(null));
    }

    @Test
    public void constructor_invalidPolicyName_throwsIllegalArgumentException() {
        // Test with an empty string
        assertThrows(IllegalArgumentException.class, () -> new PolicyName(""));
        // Test with a string starting with whitespace (e.g., " Life Insurance")
        assertThrows(IllegalArgumentException.class, () -> new PolicyName(" Life Insurance"));
        // Test with non-alphanumeric characters in the name
        assertThrows(IllegalArgumentException.class, () -> new PolicyName("Life@Insurance"));
    }

    @Test
    public void isValidPolicyName_validInputs_returnsTrue() {
        // Valid policy names: should start with an alphanumeric character and may contain spaces.
        assertTrue(PolicyName.isValidPolicyName("LifeInsurance"));
        assertTrue(PolicyName.isValidPolicyName("Life Insurance"));
        assertTrue(PolicyName.isValidPolicyName("Policy123"));
        assertTrue(PolicyName.isValidPolicyName("Health Policy"));
    }

    @Test
    public void isValidPolicyName_invalidInputs_returnsFalse() {
        // Empty string is invalid.
        assertFalse(PolicyName.isValidPolicyName(""));
        // A string with only a space is invalid.
        assertFalse(PolicyName.isValidPolicyName(" "));
        // Leading whitespace makes it invalid.
        assertFalse(PolicyName.isValidPolicyName(" Life Insurance"));
        // Contains invalid character '@'
        assertFalse(PolicyName.isValidPolicyName("Life@Insurance"));
    }

    @Test
    public void equalsAndHashCode() {
        PolicyName policyName1 = new PolicyName("Life Insurance");
        PolicyName policyName2 = new PolicyName("Life Insurance");
        PolicyName policyName3 = new PolicyName("Car Insurance");

        // Check equality and consistent hash codes.
        assertTrue(policyName1.equals(policyName2));
        assertEquals(policyName1.hashCode(), policyName2.hashCode());

        // Different names should not be equal.
        assertFalse(policyName1.equals(policyName3));
    }

    @Test
    public void toString_returnsCorrectValue() {
        PolicyName policyName = new PolicyName("Life Insurance");
        assertEquals("Life Insurance", policyName.toString());
    }
}
