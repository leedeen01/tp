package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePremiumCommand}.
 */
public class DeletePremiumCommandTest {

    @Test
    public void execute_invalidPremiumName_throwsCommandException() {
        // Setup a person with specific premiums
        Person personWithPremiums = new PersonBuilder()
                .withPremiumList("HealthCare $150")
                .build();
        Model model = new ModelManager();
        model.addPerson(personWithPremiums);

        // Try to delete a premium that doesn't exist for this person
        PremiumList premiumToDelete = new PremiumListBuilder()
                .withPremium("LifeShield $200")
                .build();

        DeletePremiumCommand deletePremiumCommand = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumToDelete);

        // Command should throw exception with the INVALID_PREMIUM_NAME message
        assertThrows(CommandException.class,
                DeletePremiumCommand.MESSAGE_INVALID_PREMIUM_NAME, () -> deletePremiumCommand.execute(model));
    }

    @Test
    public void deletePremium_premiumDoesNotExist_throwsParseException() {
        // Create a person with specific premiums
        PremiumList existingPremiums = new PremiumListBuilder()
                .withPremium("HealthCare $150")
                .build();
        Person person = new PersonBuilder()
                .withPremiumList(existingPremiums)
                .build();

        // Create premium that doesn't exist in the person's premium list
        Premium nonExistentPremium = new Premium("LifeShield", 200);
        PremiumList premiumsToDelete = new PremiumList();
        premiumsToDelete.add(nonExistentPremium);

        DeletePremiumCommand command = new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumsToDelete);

        // The deletePremium method is private, so we need to test it through execute
        Model model = new ModelManager();
        model.addPerson(person);

        assertThrows(CommandException.class,
                DeletePremiumCommand.MESSAGE_INVALID_PREMIUM_NAME, () -> command.execute(model));
    }

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
