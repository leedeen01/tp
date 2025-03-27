package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPolicyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;
import seedu.address.testutil.PolicyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddPolicyCommand}.
 */
public class AddPolicyCommandTest {

    @Test
    public void execute_policyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPolicyAdded modelStub = new ModelStubAcceptingPolicyAdded();
        Policy validPolicy = new PolicyBuilder().build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(validPolicy);

        CommandResult commandResult = addPolicyCommand.execute(modelStub);
        String expectedMessage = String.format(AddPolicyCommand.MESSAGE_SUCCESS,
                seedu.address.logic.Messages.formatPolicy(validPolicy));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(List.of(validPolicy), modelStub.policiesAdded);
    }

    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        Policy validPolicy = new PolicyBuilder().build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(validPolicy);
        ModelStubAcceptingPolicyAdded modelStub = new ModelStubAcceptingPolicyAdded();
        // add the policy once
        modelStub.addPolicy(validPolicy);

        CommandException exception = assertThrows(CommandException.class, () -> addPolicyCommand.execute(modelStub));
        assertEquals(AddPolicyCommand.MESSAGE_DUPLICATE_PREMIUM, exception.getMessage());
    }

    @Test
    public void equals() {
        Policy policyA = new PolicyBuilder().withPolicyNumber("POL123").build();
        Policy policyB = new PolicyBuilder().withPolicyNumber("POL456").build();
        AddPolicyCommand addPolicyACommand = new AddPolicyCommand(policyA);
        AddPolicyCommand addPolicyACopyCommand = new AddPolicyCommand(policyA);
        AddPolicyCommand addPolicyBCommand = new AddPolicyCommand(policyB);

        // same object -> returns true
        assertEquals(addPolicyACommand, addPolicyACommand);

        // same values -> returns true
        assertEquals(addPolicyACommand, addPolicyACopyCommand);

        // different types -> returns false
        assertNotEquals(1, addPolicyACommand);

        // null -> returns false
        assertNotEquals(null, addPolicyACommand);

        // different policy -> returns false
        assertNotEquals(addPolicyACommand, addPolicyBCommand);
    }

    /**
     * A Model stub that throws an AssertionError for any method not overridden.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Policy> getFilteredPolicyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPolicyList(Predicate<Policy> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePolicy(Policy target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPolicy(Policy policy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPolicyBook getPolicyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicyBook(ReadOnlyPolicyBook policyBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicyBookFilePath(Path policyBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPolicyBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Path getUserProfileFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserProfileFilePath(Path userProfileFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UserProfile getUserProfile() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserProfile(UserProfile currentProfile, UserProfile userProfile) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserProfile(UserProfile userProfile) throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        @Test
        public void toString_returnsCorrectFormat() {
            Policy policy = new Policy(
                    new PolicyName(VALID_POLICY_NAME),
                    new PolicyNumber(VALID_POLICY_NUMBER),
                    new ProviderCompany(VALID_PROVIDER_COMPANY),
                    new PolicyLink(VALID_POLICY_LINK));
            AddPolicyCommand command = new AddPolicyCommand(policy);
            String expected = new ToStringBuilder(command)
                    .add("toAdd", policy)
                    .toString();
            assertEquals(expected, command.toString());
        }

        @Override
        public void updateUpcomingBirthdays() {
            // Not required for this test
        }

        @Override
        public ObservableList<Person> getUpcomingBirthdays() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that accepts policies being added.
     */
    private class ModelStubAcceptingPolicyAdded extends ModelStub {
        final ArrayList<Policy> policiesAdded = new ArrayList<>();

        @Override
        public boolean hasPolicy(Policy policy) {
            requireNonNull(policy);
            return policiesAdded.stream().anyMatch(policy::equals);
        }

        @Override
        public void addPolicy(Policy policy) {
            requireNonNull(policy);
            policiesAdded.add(policy);
        }
    }
}
