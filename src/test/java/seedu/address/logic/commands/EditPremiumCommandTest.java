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
import seedu.address.logic.commands.EditPremiumCommand.EditPremiumDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditPremiumDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPremiumCommand.
 */
public class EditPremiumCommandTest {

    @Test
    public void equals() throws ParseException {
        EditPremiumDescriptor descriptorAmy = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_AMY).build();
        EditPremiumDescriptor descriptorBob = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_BOB).build();

        final EditPremiumCommand standardCommand = new EditPremiumCommand(INDEX_FIRST_PERSON, descriptorAmy);

        // same values -> returns true
        EditPremiumDescriptor copyDescriptor = new EditPremiumDescriptor(descriptorAmy);
        EditPremiumCommand commandWithSameValues = new EditPremiumCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new seedu.address.logic.commands.ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPremiumCommand(INDEX_SECOND_PERSON, descriptorAmy)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPremiumCommand(INDEX_FIRST_PERSON, descriptorBob)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPremiumDescriptor editPremiumDescriptor = new EditPremiumDescriptor();
        EditPremiumCommand editPremiumCommand = new EditPremiumCommand(index, editPremiumDescriptor);
        String expected = EditPremiumCommand.class.getCanonicalName() + "{index=" + index + ", editPremiumDescriptor="
                + editPremiumDescriptor + "}";
        assertEquals(expected, editPremiumCommand.toString());
    }
}
