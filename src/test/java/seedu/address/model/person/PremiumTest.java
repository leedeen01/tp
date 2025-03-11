package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PremiumTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Premium(null, null));
    }

    @Test
    public void constructor_invalidPremiumNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Premium("name", -100));
    }

    @Test
    public void constructor_invalidPremiumName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Premium("asd as d name", -100));
    }

    @Test
    public void isValidPremium() {
        // null premium
        assertThrows(NullPointerException.class, () -> PremiumList.isValidPremium(null));

        //invalid premium
        assertFalse(Premium.isValidPremium("asd", -100)); // negative number
    }

    @Test
    public void equals() {
        Premium premium = new Premium("asd", 100);

        // same values -> return true
        assertTrue(premium.equals(new Premium("asd", 100)));

        // same object -> return true
        assertTrue(premium.equals(premium));

        // null -> return false
        assertFalse(premium.equals(null));

        // different types -> return false
        assertFalse(premium.equals("111"));

        // different values -> return false
        assertFalse(premium.equals(new Premium("asdads", 222)));
    }

    @Test
    public void testToString() {
        Premium premium = new Premium("asd", 100);
        assertTrue(premium.toString().equals("[asd, 100]"));
    }

    @Test
    public void testHashCode() {
        Premium premium = new Premium("asd", 100);
        Integer premiumHash = new String("asd").hashCode() + new Integer(100).hashCode();
        assertTrue(premium.hashCode() == premiumHash);
    }
}
