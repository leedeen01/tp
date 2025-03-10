package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;

public class JsonAdaptedPremiumListTest {

    @Test
    public void constructor_empty_success() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        assertTrue(jsonAdaptedPremiumList.isEmpty());
    }

    @Test
    public void add_stringPremium_success() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        String premiumString = "test 100";

        jsonAdaptedPremiumList.add(premiumString);

        assertFalse(jsonAdaptedPremiumList.isEmpty());
        assertEquals(1, jsonAdaptedPremiumList.premiumList.size());
        assertEquals("test", jsonAdaptedPremiumList.premiumList.get(0).premiumName);
        assertEquals(100, jsonAdaptedPremiumList.premiumList.get(0).premiumAmount);
    }

    @Test
    public void add_invalidStringFormat_throwsArrayIndexOutOfBoundsException() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        String invalidPremiumString = "test"; // Missing amount

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> jsonAdaptedPremiumList.add(invalidPremiumString));
    }

    @Test
    public void add_nonNumericAmount_throwsNumberFormatException() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        String invalidPremiumString = "test abc"; // Non-numeric amount

        assertThrows(NumberFormatException.class, () -> jsonAdaptedPremiumList.add(invalidPremiumString));
    }

    @Test
    public void addAll_premiumList_success() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        PremiumList modelPremiumList = new PremiumList();
        modelPremiumList.add(new Premium("test1", 100));
        modelPremiumList.add(new Premium("test2", 200));

        jsonAdaptedPremiumList.addAll(modelPremiumList);

        assertFalse(jsonAdaptedPremiumList.isEmpty());
        assertEquals(2, jsonAdaptedPremiumList.premiumList.size());
        assertEquals("test1", jsonAdaptedPremiumList.premiumList.get(0).premiumName);
        assertEquals(100, jsonAdaptedPremiumList.premiumList.get(0).premiumAmount);
        assertEquals("test2", jsonAdaptedPremiumList.premiumList.get(1).premiumName);
        assertEquals(200, jsonAdaptedPremiumList.premiumList.get(1).premiumAmount);
    }


    @Test
    public void isEmpty_emptyList_returnsTrue() {
        JsonAdaptedPremiumList jsonAdaptedPremiumList = new JsonAdaptedPremiumList();
        assertTrue(jsonAdaptedPremiumList.isEmpty());
    }

    /**
     * Helper class to create JsonAdaptedPremium for testing.
     * This is needed because we don't have the actual implementation in the test class.
     */
    private static class JsonAdaptedPremium {
        final String premiumName;
        final int premiumAmount;

        JsonAdaptedPremium(String premiumName, int premiumAmount) {
            this.premiumName = premiumName;
            this.premiumAmount = premiumAmount;
        }
    }
}
