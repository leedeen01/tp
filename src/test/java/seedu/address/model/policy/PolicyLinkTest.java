package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyLinkTest {

    private static final String VALID_URL_HTTP = "http://www.google.com";
    private static final String VALID_URL_HTTPS = "https://example.com";
    private static final String VALID_URL_FTP = "ftp://files.server.com";
    private static final String INVALID_URL_MALFORMED = "http//invalid.com";
    private static final String INVALID_URL_EMPTY = "http://";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyLink(null));
    }

    @Test
    public void constructor_invalidPolicyLink_throwsIllegalArgumentException() {
        // Pass an invalid URL that does not match the regex.
        assertThrows(IllegalArgumentException.class, () -> new PolicyLink(INVALID_URL_MALFORMED));
        assertThrows(IllegalArgumentException.class, () -> new PolicyLink(INVALID_URL_EMPTY));
    }

    @Test
    public void isValidPolicyLink_validInputs_returnsTrue() {
        assertTrue(PolicyLink.isValidPolicyLink(VALID_URL_HTTP));
        assertTrue(PolicyLink.isValidPolicyLink(VALID_URL_HTTPS));
        assertTrue(PolicyLink.isValidPolicyLink(VALID_URL_FTP));
    }

    @Test
    public void isValidPolicyLink_invalidInputs_returnsFalse() {
        assertFalse(PolicyLink.isValidPolicyLink(INVALID_URL_MALFORMED));
        assertFalse(PolicyLink.isValidPolicyLink(INVALID_URL_EMPTY));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        PolicyLink link1 = new PolicyLink(VALID_URL_HTTP);
        PolicyLink link2 = new PolicyLink(VALID_URL_HTTP);
        assertEquals(link1, link2);
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        PolicyLink link1 = new PolicyLink(VALID_URL_HTTPS);
        PolicyLink link2 = new PolicyLink(VALID_URL_HTTPS);
        assertEquals(link1.hashCode(), link2.hashCode());
    }

    @Test
    public void toString_returnsCorrectValue() {
        PolicyLink link = new PolicyLink(VALID_URL_FTP);
        assertEquals(VALID_URL_FTP, link.toString());
    }
}
