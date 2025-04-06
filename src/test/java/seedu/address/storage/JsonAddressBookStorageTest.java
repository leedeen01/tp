package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void createDefaultAddressBook_fileDoesNotExist_createsFileWithDefaultData() throws Exception {
        Path filePath = testFolder.resolve("defaultAddressBook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(filePath);

        // Sanity check: File should not exist before
        assertTrue(Files.notExists(filePath));

        try {
            // Trigger creation of default address book
            storage.readAddressBook(filePath);

            // File should now exist
            assertTrue(Files.exists(filePath), "Default address book file should have been created");

            // Read content and verify some expected content
            String content = Files.readString(filePath);
            assertTrue(content.contains("Alice Tan"));
            assertTrue(content.contains("Bryan Lim"));
            assertTrue(content.contains("Catherine Wong"));
            assertTrue(content.contains("David Koh"));
            assertTrue(content.contains("Elaine Ng"));
        } catch (DataLoadingException e) {
            fail("DataLoadingException should not be thrown for default file creation");
        }
    }

    @Test
    public void createDefaultAddressBook_createsExpectedFileStructure() throws IOException {
        Path filePath = testFolder.resolve("createdByMethod.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(filePath);

        // Create the default address book
        storage.createDefaultAddressBook(filePath);

        // Confirm file is created
        assertTrue(Files.exists(filePath), "File should be created");

        // Check content includes required fields
        String content = Files.readString(filePath);
        assertTrue(content.contains("\"name\" : \"Alice Tan\""));
        assertTrue(content.contains("\"phone\" : \"91234567\""));
        assertTrue(content.contains("\"email\" : \"alice.tan@example.com\""));
    }

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_defaultResult() throws Exception {
        assertTrue(readAddressBook("nonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_illegalValueException_throwsDataLoadingException() {
        Path invalidFilePath = Paths.get("src", "test", "data", "InvalidAddressBook.json");

        try {
            Files.writeString(invalidFilePath, "{ \"invalidField\": \"invalidValue\" }");

            assertThrows(DataLoadingException.class, () -> {
                new JsonAddressBookStorage(invalidFilePath).readAddressBook(invalidFilePath);
            });

        } catch (IOException e) {
            fail("Failed to create a temporary file for testing: " + e.getMessage());
        } finally {
            try {
                Files.deleteIfExists(invalidFilePath);
            } catch (IOException e) {
                System.err.println("Failed to delete temporary test file: " + e.getMessage());
            }
        }
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
