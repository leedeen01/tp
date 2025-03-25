package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPolicy.HEALTH_2040;
import static seedu.address.testutil.TypicalPolicy.LIFE_SHIELD;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PolicyBookBuilder;
import seedu.address.testutil.PolicyBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        PolicyBook policyBook = new PolicyBookBuilder().withPolicy(HEALTH_2040).withPolicy(LIFE_SHIELD).build();
        PolicyBook differentPolicyBook = new PolicyBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, policyBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, policyBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentPolicyBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, policyBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, policyBook, differentUserPrefs)));
    }

    // ======== Policy-related Tests ========

    @Test
    public void hasPolicy_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPolicy(null));
    }

    @Test
    public void hasPolicy_policyNotInPolicyBook_returnsFalse() {
        // HEALTH_2040 is not added yet.
        assertFalse(modelManager.hasPolicy(HEALTH_2040));
    }

    @Test
    public void hasPolicy_policyInPolicyBook_returnsTrue() {
        modelManager.addPolicy(HEALTH_2040);
        assertTrue(modelManager.hasPolicy(HEALTH_2040));
    }

    @Test
    public void addPolicy_policy_success() {
        modelManager.addPolicy(HEALTH_2040);
        assertTrue(modelManager.hasPolicy(HEALTH_2040));
    }

    @Test
    public void deletePolicy_policy_success() {
        modelManager.addPolicy(HEALTH_2040);
        modelManager.deletePolicy(HEALTH_2040);
        assertFalse(modelManager.hasPolicy(HEALTH_2040));
    }

    @Test
    public void updateFilteredPolicyList_validPredicate_filtersCorrectly() {
        modelManager.addPolicy(HEALTH_2040);
        modelManager.addPolicy(LIFE_SHIELD);
        // Use a predicate that matches only HEALTH_2040.
        modelManager.updateFilteredPolicyList(policy -> policy.equals(HEALTH_2040));
        ObservableList<Policy> filteredList = modelManager.getFilteredPolicyList();
        assertEquals(1, filteredList.size());
        assertEquals(HEALTH_2040, filteredList.get(0));
    }

    @Test
    public void getFilteredPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPolicyList().remove(0));
    }

    @Test
    public void setAndGetPolicyBookFilePath_validPath_success() {
        Path testPath = Paths.get("dummy", "policybook.json");
        modelManager.setPolicyBookFilePath(testPath);
        assertEquals(testPath, modelManager.getPolicyBookFilePath());
    }

    @Test
    public void setPolicyBook_validPolicyBook_success() {
        PolicyBook dummyPolicyBook = new PolicyBook();
        Policy samplePolicy = new Policy(
                new PolicyName("Test Policy"),
                new PolicyNumber("TP123"),
                new ProviderCompany("TestCompany"),
                new PolicyLink("http://testpolicy.com")
        );
        dummyPolicyBook.addPolicy(samplePolicy);
        modelManager.setPolicyBook(dummyPolicyBook);
        // Verify that the model's policy book matches the dummy policy book.
        assertEquals(dummyPolicyBook, modelManager.getPolicyBook());
    }

    @Test
    public void setPolicy_validTargetAndEditedPolicy_success() {
        Policy originalPolicy = new Policy(
                new PolicyName("Original Policy"),
                new PolicyNumber("OP001"),
                new ProviderCompany("CompanyA"),
                new PolicyLink("http://companyA.com/original")
        );
        modelManager.addPolicy(originalPolicy);

        Policy editedPolicy = new PolicyBuilder(originalPolicy)
                .withPolicyLink("http://companyA.com/edited")
                .build();
        modelManager.setPolicy(originalPolicy, editedPolicy);

        Policy updatedPolicy = modelManager.getPolicyBook().getPolicyList().get(0);

        assertEquals(editedPolicy, updatedPolicy);
    }

    @Test
    public void setPolicy_nullTarget_throwsNullPointerException() {
        Policy editedPolicy = new PolicyBuilder()
                .withPolicyName("Edited Policy")
                .withPolicyNumber("EP002")
                .withProviderCompany("CompanyB")
                .withPolicyLink("http://companyB.com/edited")
                .build();
        assertThrows(NullPointerException.class, () -> modelManager.setPolicy(null, editedPolicy));
    }

    @Test
    public void setPolicy_nullEditedPolicy_throwsNullPointerException() {
        Policy originalPolicy = new PolicyBuilder()
                .withPolicyName("Original Policy")
                .withPolicyNumber("OP003")
                .withProviderCompany("CompanyC")
                .withPolicyLink("http://companyC.com/original")
                .build();
        modelManager.addPolicy(originalPolicy);
        assertThrows(NullPointerException.class, () -> modelManager.setPolicy(originalPolicy, null));
    }
}
