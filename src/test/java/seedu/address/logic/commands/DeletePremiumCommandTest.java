package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePremiumCommand}.
 */
public class DeletePremiumCommandTest {

    @Test
    public void equals() {
        PremiumList premiumList1 = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .build();

        PremiumList premiumList2 = new PremiumListBuilder()
                .withPremium("HealthCare $150")
                .build();

        DeletePremiumCommand deleteFirstCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumList1);
        DeletePremiumCommand deleteSecondCommand = new DeletePremiumCommand(INDEX_SECOND_PERSON, premiumList1);
        DeletePremiumCommand deleteFirstDifferentPremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON,
                premiumList2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePremiumCommand deleteFirstCommandCopy = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumList1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // same index but different premium list -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstDifferentPremiumCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PremiumList premiumList = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .build();

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(targetIndex, premiumList);
        String expected = DeletePremiumCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", premiumList=" + premiumList
                + "}";
        assertEquals(expected, deletePremiumCommand.toString());
    }
}
