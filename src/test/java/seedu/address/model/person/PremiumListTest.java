package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PremiumListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PremiumList(null));
    }

    @Test
    public void constructor_invalidPremiumNegative_throwsIllegalArgumentException() {
        int invalidPremium = -1;
        assertThrows(IllegalArgumentException.class, () -> new PremiumList(invalidPremium));
    }

    @Test
    public void isValidPremium() {
        // null premium
        assertThrows(NullPointerException.class, () -> PremiumList.isValidPremium(null));

        //invalid premium
        assertFalse(PremiumList.isValidPremium(-1)); // negative number
    }

    @Test
    public void equals() {
        PremiumList premiumList = new PremiumList(111);

        // same values -> return true
        assertTrue(premiumList.equals(new PremiumList(111)));

        // same object -> return true
        assertTrue(premiumList.equals(premiumList));

        // null -> return false
        assertFalse(premiumList.equals(null));

        // different types -> return false
        assertFalse(premiumList.equals("111"));

        // different values -> return false
        assertFalse(premiumList.equals(new PremiumList(222)));
    }

    @Test
    public void testToString() {
        PremiumList premiumList = new PremiumList(111);
        assertTrue(premiumList.toString().equals("111"));
    }

    @Test
    public void testHashCode() {
        PremiumList premiumList = new PremiumList(111);
        Integer correctPremium = 111;
        Integer premiumHash = correctPremium.hashCode();
        assertTrue(premiumList.hashCode() == premiumHash);
    }
}
