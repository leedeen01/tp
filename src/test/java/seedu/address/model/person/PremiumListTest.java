package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class PremiumListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Null PremiumList
        assertThrows(NullPointerException.class, () -> new PremiumList((PremiumList) null));

        // Null ArrayList
        assertThrows(NullPointerException.class, () -> new PremiumList((ArrayList<Premium>) null));

        // Null Premium
        assertThrows(NullPointerException.class, () -> new PremiumList((Premium) null));
    }

    @Test
    public void isValidPremium() {
        Premium validPremium = new Premium("test", 100);
        PremiumList validList = new PremiumList(validPremium);

        // Valid PremiumList
        assertTrue(PremiumList.isValidPremium(validList));

        // Empty PremiumList is also valid
        assertTrue(PremiumList.isValidPremium(new PremiumList()));

        // Null PremiumList
        assertThrows(NullPointerException.class, () -> PremiumList.isValidPremium(null));
    }

    @Test
    public void add_validPremium_success() {
        PremiumList premiumList = new PremiumList();
        Premium premium = new Premium("test", 100);

        premiumList.add(premium);

        assertTrue(premiumList.contains(premium));
        assertFalse(premiumList.isEmpty());
        assertEquals(1, premiumList.premiumList.size());
    }

    @Test
    public void add_nullPremium_throwsNullPointerException() {
        PremiumList premiumList = new PremiumList();
        assertThrows(NullPointerException.class, () -> premiumList.add((Premium) null));
    }

    @Test
    public void add_invalidStringPremium_throwsIllegalArgumentException() {
        PremiumList premiumList = new PremiumList();
        String invalidPremiumString = "this is not a valid premium string";

        assertThrows(IllegalArgumentException.class, () -> premiumList.add(invalidPremiumString));
    }

    @Test
    public void remove_existingPremium_success() {
        Premium premium = new Premium("test", 100);
        PremiumList premiumList = new PremiumList(premium);

        premiumList.remove(premium);

        assertTrue(premiumList.isEmpty());
        assertFalse(premiumList.contains(premium));
    }

    @Test
    public void remove_nonExistingPremium_noChange() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);
        PremiumList premiumList = new PremiumList(premium1);

        premiumList.remove(premium2);

        assertFalse(premiumList.isEmpty());
        assertTrue(premiumList.contains(premium1));
        assertEquals(1, premiumList.premiumList.size());
    }

    @Test
    public void contains_existingPremium_returnsTrue() {
        Premium premium = new Premium("test", 100);
        PremiumList premiumList = new PremiumList(premium);

        assertTrue(premiumList.contains(premium));
    }

    @Test
    public void contains_nonExistingPremium_returnsFalse() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);
        PremiumList premiumList = new PremiumList(premium1);

        assertFalse(premiumList.contains(premium2));
    }

    @Test
    public void addAll_validPremiumList_success() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);

        PremiumList list1 = new PremiumList(premium1);
        PremiumList list2 = new PremiumList(premium2);

        list1.addAll(list2);

        assertTrue(list1.contains(premium1));
        assertTrue(list1.contains(premium2));
        assertEquals(2, list1.premiumList.size());
    }

    @Test
    public void addAll_nullPremiumList_throwsNullPointerException() {
        PremiumList premiumList = new PremiumList();
        assertThrows(NullPointerException.class, () -> premiumList.addAll(null));
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        PremiumList premiumList = new PremiumList();
        assertTrue(premiumList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        Premium premium = new Premium("test", 100);
        PremiumList premiumList = new PremiumList(premium);

        assertFalse(premiumList.isEmpty());
    }

    @Test
    public void toString_nonEmptyList_returnsCorrectFormat() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);

        PremiumList premiumList = new PremiumList();
        premiumList.add(premium1);
        premiumList.add(premium2);

        String expected = premium1.toString() + "\n" + premium2.toString() + "\n";
        assertEquals(expected, premiumList.toString());
    }

    @Test
    public void equals_samePremiumList_returnsTrue() {
        Premium premium = new Premium("test", 100);
        PremiumList list1 = new PremiumList(premium);

        assertTrue(list1.equals(list1));
    }

    @Test
    public void equals_equalPremiumLists_returnsTrue() {
        Premium premium1 = new Premium("test", 100);
        Premium premium2 = new Premium("test", 100);

        PremiumList list1 = new PremiumList(premium1);
        PremiumList list2 = new PremiumList(premium2);

        assertTrue(list1.equals(list2));
    }

    @Test
    public void equals_differentPremiumLists_returnsFalse() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);

        PremiumList list1 = new PremiumList(premium1);
        PremiumList list2 = new PremiumList(premium2);

        assertFalse(list1.equals(list2));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        Premium premium = new Premium("test", 100);
        PremiumList premiumList = new PremiumList(premium);

        assertFalse(premiumList.equals("Not a PremiumList"));
    }

    @Test
    public void equals_null_returnsFalse() {
        Premium premium = new Premium("test", 100);
        PremiumList premiumList = new PremiumList(premium);

        assertFalse(premiumList.equals(null));
    }

    @Test
    public void equals_differentSizes_returnsFalse() {
        Premium premium1 = new Premium("test1", 100);
        Premium premium2 = new Premium("test2", 200);

        PremiumList list1 = new PremiumList(premium1);

        PremiumList list2 = new PremiumList();
        list2.add(premium1);
        list2.add(premium2);

        assertFalse(list1.equals(list2));
    }

    @Test
    public void hashCode_equalPremiumLists_equalHashCode() {
        Premium premium1 = new Premium("test", 100);
        Premium premium2 = new Premium("test", 100);

        PremiumList list1 = new PremiumList(premium1);
        PremiumList list2 = new PremiumList(premium2);

        assertEquals(list1.hashCode(), list2.hashCode());
    }
}