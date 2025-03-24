package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProviderCompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ProviderCompany(null));
    }

    @Test
    public void constructor_invalidProviderCompany_throwsIllegalArgumentException() {
        // Test with an empty string
        assertThrows(IllegalArgumentException.class, () -> new ProviderCompany(""));
        // Test with a string that is only whitespace or starts with whitespace
        assertThrows(IllegalArgumentException.class, () -> new ProviderCompany(" "));
        assertThrows(IllegalArgumentException.class, () -> new ProviderCompany("  LeadingSpace"));
    }

    @Test
    public void isValidProviderCompany_validInputs_returnsTrue() {
        // Valid provider company names should not be blank and should not start with whitespace.
        assertTrue(ProviderCompany.isValidProviderCompany("BestInsure"));
        assertTrue(ProviderCompany.isValidProviderCompany("InsureCo"));
        assertTrue(ProviderCompany.isValidProviderCompany("A")); // Minimal valid input
    }

    @Test
    public void isValidProviderCompany_invalidInputs_returnsFalse() {
        // Invalid if empty or only whitespace, or if the first character is whitespace.
        assertFalse(ProviderCompany.isValidProviderCompany(""));
        assertFalse(ProviderCompany.isValidProviderCompany(" "));
        assertFalse(ProviderCompany.isValidProviderCompany("  LeadingSpace"));
    }

    @Test
    public void equalsAndHashCode() {
        ProviderCompany company1 = new ProviderCompany("BestInsure");
        ProviderCompany company2 = new ProviderCompany("BestInsure");
        ProviderCompany company3 = new ProviderCompany("OtherInsure");

        // Two provider companies with the same value should be equal and have the same hash code.
        assertTrue(company1.equals(company2));
        assertEquals(company1.hashCode(), company2.hashCode());

        // Different provider companies should not be equal.
        assertNotEquals(company1, company3);
    }

    @Test
    public void toString_returnsCorrectValue() {
        ProviderCompany company = new ProviderCompany("BestInsure");
        assertEquals("BestInsure", company.toString());
    }
}
