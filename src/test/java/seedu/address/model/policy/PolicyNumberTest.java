package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyNumber(null));
    }

    @Test
    public void constructor_invalidPolicyNumber_throwsIllegalArgumentException() {
        // Empty string should be rejected
        assertThrows(IllegalArgumentException.class, () -> new PolicyNumber(""));
        // Policy numbers should only be alphanumeric; symbols or spaces are not allowed.
        assertThrows(IllegalArgumentException.class, () -> new PolicyNumber("PN#123"));
        assertThrows(IllegalArgumentException.class, () -> new PolicyNumber("ABC 123"));
    }

    @Test
    public void isValidPolicyNumber_validInputs_returnsTrue() {
        // Valid inputs contain only alphanumeric characters and are at least 1 character long.
        assertTrue(PolicyNumber.isValidPolicyNumber("123"));
        assertTrue(PolicyNumber.isValidPolicyNumber("ABC123"));
        assertTrue(PolicyNumber.isValidPolicyNumber("policy1"));
    }

    @Test
    public void isValidPolicyNumber_invalidInputs_returnsFalse() {
        // Invalid if empty or contains non-alphanumeric characters.
        assertFalse(PolicyNumber.isValidPolicyNumber(""));
        assertFalse(PolicyNumber.isValidPolicyNumber("ABC 123")); // contains space
        assertFalse(PolicyNumber.isValidPolicyNumber("ABC-123")); // contains hyphen
        assertFalse(PolicyNumber.isValidPolicyNumber("ABC@123")); // contains special symbol
    }

    @Test
    public void equalsAndHashCode() {
        PolicyNumber num1 = new PolicyNumber("ABC123");
        PolicyNumber num2 = new PolicyNumber("ABC123");
        PolicyNumber num3 = new PolicyNumber("XYZ789");

        // Two policy numbers with the same value should be equal and have the same hash code.
        assertTrue(num1.equals(num2));
        assertEquals(num1.hashCode(), num2.hashCode());

        // Different policy numbers should not be equal.
        assertFalse(num1.equals(num3));
    }

    @Test
    public void toString_returnsValue() {
        PolicyNumber policyNumber = new PolicyNumber("ABC123");
        assertEquals("ABC123", policyNumber.toString());
    }
}
