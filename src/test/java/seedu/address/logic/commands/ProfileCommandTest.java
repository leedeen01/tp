package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonPolicyBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonUserProfileStorage;
import seedu.address.storage.StorageManager;

class ProfileCommandTest {

    @TempDir
    public Path testFolder;

    private Model model;
    private UserProfile updatedProfile;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        updatedProfile = new UserProfile(new Name("Bob"), new Email("bob@example.com"), new Phone("98765432"));
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonPolicyBookStorage policyBookStorage = new JsonPolicyBookStorage(getTempFilePath("pb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonUserProfileStorage userProfileStorage = new JsonUserProfileStorage(getTempFilePath("up"));
        storageManager = new StorageManager(addressBookStorage, policyBookStorage, userPrefsStorage,
                userProfileStorage);

        model = new ModelManager(getTypicalAddressBook(), getTypicalPolicyBook(), new UserPrefs(),
                new UserProfile(), storageManager);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    void execute_validProfile_updatesProfileSuccessfully() throws CommandException, IOException {
        ProfileCommand profileCommand = new ProfileCommand(updatedProfile);

        CommandResult result = profileCommand.execute(model);

        assertEquals("Profile successfully updated!\nYour current profile information:\n"
                        + "Name: Bob\nPhone: 98765432\nEmail: bob@example.com",
                result.getFeedbackToUser());
    }
}
