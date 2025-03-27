package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class BirthdayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "9210ds";
        assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday
        assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("1998/12/10")); // wrong format
        assertFalse(Birthday.isValidBirthday("2040-01-01")); // future date

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("2001-08-12"));
        assertTrue(Birthday.isValidBirthday("2010-12-12"));
        assertTrue(Birthday.isValidBirthday("2023-08-27"));
    }

    @Test
    public void equals() {
        Birthday birthday = new Birthday("2001-01-01");

        // same values -> returns true
        assertTrue(birthday.equals(new Birthday("2001-01-01")));

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different types -> returns false
        assertFalse(birthday.equals(5.0f));

        // different values -> returns false
        assertFalse(birthday.equals(new Birthday("2001-02-02")));
    }

    @Test
    public void isWithinNext30Days_withUpcomingBirthday_returnsTrue() {
        LocalDate in5Days = LocalDate.now().plusDays(5);
        Birthday birthday = new Birthday(in5Days.withYear(2000).toString());
        assertTrue(birthday.isWithinNext30Days());
    }

    @Test
    public void isWithinNext30Days_withBirthdayToday_returnsTrue() {
        Birthday today = new Birthday(LocalDate.now().withYear(1999).toString());
        assertTrue(today.isWithinNext30Days());
    }

    @Test
    public void isWithinNext30Days_withBirthdayInPastThisYear_returnsFalse() {
        LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
        Birthday birthday = new Birthday(tenDaysAgo.withYear(1995).toString());
        assertFalse(birthday.isWithinNext30Days());
    }

    @Test
    public void isWithinNext30Days_withFarFutureBirthday_returnsFalse() {
        LocalDate in60Days = LocalDate.now().plusDays(60);
        Birthday birthday = new Birthday(in60Days.withYear(2002).toString());
        assertFalse(birthday.isWithinNext30Days());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        String date = "2001-07-21";
        Birthday birthday = new Birthday(date);
        assertTrue(birthday.toString().equals(date));
    }

    @Test
    public void hashCode_consistency() {
        Birthday a = new Birthday("1999-12-25");
        Birthday b = new Birthday("1999-12-25");
        assertTrue(a.hashCode() == b.hashCode());
    }

    @Test
    public void isWithinNext30Days_withBirthdayExactlyIn30Days_returnsTrue() {
        LocalDate in30Days = LocalDate.now().plusDays(30);
        Birthday birthday = new Birthday(in30Days.withYear(1990).toString());
        assertTrue(birthday.isWithinNext30Days());
    }

}
