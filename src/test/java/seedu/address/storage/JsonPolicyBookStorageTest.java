package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPolicy.getTypicalPolicyBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PolicyBook;
import seedu.address.model.ReadOnlyPolicyBook;

public class JsonPolicyBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPolicyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void createDefaultAddressBook_fileDoesNotExist_createsFileWithDefaultData() throws Exception {
        Path filePath = testFolder.resolve("defaultPolicyBook.json");
        JsonPolicyBookStorage storage = new JsonPolicyBookStorage(filePath);

        // Sanity check: File should not exist before
        assertTrue(Files.notExists(filePath));

        try {
            // Trigger creation of default address book
            storage.readPolicyBook(filePath);

            // File should now exist
            assertTrue(Files.exists(filePath), "Default policy book file should have been created");

            // Read content and verify some expected content
            String content = Files.readString(filePath);
            assertTrue(content.contains("LifeShield"));
            assertTrue(content.contains("HealthPlus"));
            assertTrue(content.contains("SecureFuture"));
            assertTrue(content.contains("HomeSafe"));
            assertTrue(content.contains("AutoCare"));
            assertTrue(content.contains("TravelAssure"));
        } catch (DataLoadingException e) {
            fail("DataLoadingException should not be thrown for default file creation");
        }
    }

    @Test
    public void createDefaultAddressBook_createsExpectedFileStructure() throws IOException {
        Path filePath = testFolder.resolve("createdByMethod.json");
        JsonPolicyBookStorage storage = new JsonPolicyBookStorage(filePath);

        // Create the default address book
        storage.createDefaultPolicyBook(filePath);

        // Confirm file is created
        assertTrue(Files.exists(filePath), "File should be created");

        // Check content includes required fields
        String content = Files.readString(filePath);
        assertTrue(content.contains("\"policyLink\" : \"https://www.shieldcorp.com/policy123\""));
        assertTrue(content.contains("\"policyNumber\" : \"POL123\""));
        assertTrue(content.contains("\"policyName\" : \"LifeShield\""));
        assertTrue(content.contains("\"providerCompany\" : \"ShieldCorp\""));
    }

    @Test
    public void readPolicyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPolicyBook(null));
    }

    private Optional<ReadOnlyPolicyBook> readPolicyBook(String filePath) throws Exception {
        return new JsonPolicyBookStorage(Paths.get(filePath))
                .readPolicyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String fileInTestDataFolder) {
        return fileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(fileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertTrue(readPolicyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPolicyBook("notJsonFormatPolicyBook.json"));
    }

    @Test
    public void readPolicyBook_invalidPolicyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPolicyBook("invalidPolicyBook.json"));
    }

    @Test
    public void readPolicyBook_invalidAndValidPolicyBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPolicyBook("invalidAndValidPolicyBook.json"));
    }

    @Test
    public void readPolicyBook_illegalValueException_throwsDataLoadingException() {
        Path invalidFilePath = Paths.get("src", "test", "data", "InvalidPolicyBook.json");

        try {
            Files.writeString(invalidFilePath, "{ \"invalidField\": \"invalidValue\" }");

            assertThrows(DataLoadingException.class, () -> {
                new JsonPolicyBookStorage(invalidFilePath).readPolicyBook(invalidFilePath);
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
    public void readAndSavePolicyBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPolicyBook.json");
        PolicyBook original = getTypicalPolicyBook();
        JsonPolicyBookStorage jsonPolicyBookStorage = new JsonPolicyBookStorage(filePath);

        jsonPolicyBookStorage.savePolicyBook(original, filePath);
        ReadOnlyPolicyBook readBack = jsonPolicyBookStorage.readPolicyBook(filePath).get();
        assertEquals(original, new PolicyBook(readBack));


        PolicyBook modified = new PolicyBook(original);

        jsonPolicyBookStorage.savePolicyBook(modified, filePath);
        readBack = jsonPolicyBookStorage.readPolicyBook(filePath).get();
        assertEquals(modified, new PolicyBook(readBack));

        jsonPolicyBookStorage.savePolicyBook(modified);
        readBack = jsonPolicyBookStorage.readPolicyBook().get();
        assertEquals(modified, new PolicyBook(readBack));
    }

    @Test
    public void savePolicyBook_nullPolicyBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePolicyBook(null, "SomeFile.json"));
    }

    /**
     * Saves the given {@code policyBook} at the specified {@code filePath}.
     */
    private void savePolicyBook(ReadOnlyPolicyBook policyBook, String filePath) {
        try {
            new JsonPolicyBookStorage(Paths.get(filePath))
                    .savePolicyBook(policyBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePolicyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePolicyBook(getTypicalPolicyBook(), null));
    }
}
