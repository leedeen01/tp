package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddPremiumCommand.
 */
public class AddPremiumCommandTest {

    @Test
    public void equals() throws ParseException {
        PremiumList premiumListAmy = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();
        PremiumList premiumListBob = new PremiumListBuilder().withPremium(VALID_PREMIUM_BOB).build();

        final AddPremiumCommand standardCommand = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListAmy);

        // same values -> returns true
        AddPremiumCommand commandWithSameValues = new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListAmy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPremiumCommand(INDEX_SECOND_PERSON, premiumListAmy)));

        // different premium -> returns false
        assertFalse(standardCommand.equals(new AddPremiumCommand(INDEX_FIRST_PERSON, premiumListBob)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        PremiumList premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();
        AddPremiumCommand addPremiumCommand = new AddPremiumCommand(index, premiumList);
        String expected = AddPremiumCommand.class.getCanonicalName() + "{premiumList="
                + premiumList + "}";
        assertEquals(expected, addPremiumCommand.toString());
    }
}
