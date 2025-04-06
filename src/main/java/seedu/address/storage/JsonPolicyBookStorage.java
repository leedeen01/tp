package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyPolicyBook;

/**
 * A class to access PolicyBook data stored as a json file on the hard disk.
 */
public class JsonPolicyBookStorage implements PolicyBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPolicyBookStorage.class);

    private Path filePath;

    public JsonPolicyBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPolicyBookFilePath() {
        return filePath;
    }

    @Override
    public void createDefaultPolicyBook(Path filePath) throws IOException {
        String defaultJson = """
            {
              "policies" : [ {
                "policyLink" : "https://www.shieldcorp.com/policy123",
                "policyNumber" : "POL123",
                "policyName" : "LifeShield",
                "providerCompany" : "ShieldCorp"
              }, {
                "policyLink" : "https://www.healthcorp.com/policy456",
                "policyNumber" : "POL456",
                "policyName" : "HealthPlus",
                "providerCompany" : "HealthCorp"
              }, {
                "policyLink" : "https://www.safeinsure.com/policy789",
                "policyNumber" : "POL789",
                "policyName" : "SecureFuture",
                "providerCompany" : "SafeInsure"
              }, {
                "policyLink" : "https://www.homeguard.com/policy101",
                "policyNumber" : "POL101",
                "policyName" : "HomeSafe",
                "providerCompany" : "HomeGuard"
              }, {
                "policyLink" : "https://www.carprotect.com/policy202",
                "policyNumber" : "POL202",
                "policyName" : "AutoCare",
                "providerCompany" : "CarProtect"
              }, {
                "policyLink" : "https://www.travelsafe.com/policy303",
                "policyNumber" : "POL303",
                "policyName" : "TravelAssure",
                "providerCompany" : "TravelSafe"
              } ]
            }
            """;
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, defaultJson, StandardCharsets.UTF_8);
    }

    @Override
    public Optional<ReadOnlyPolicyBook> readPolicyBook() throws DataLoadingException {
        return readPolicyBook(filePath);
    }

    /**
     * Similar to {@link #readPolicyBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPolicyBook> readPolicyBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            try {
                createDefaultPolicyBook(filePath);
            } catch (IOException e) {
                logger.warning("Failed to create default policy book file: " + e.getMessage());
                throw new DataLoadingException(e);
            }
        }

        Optional<JsonSerializablePolicyBook> jsonPolicyBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePolicyBook.class);
        if (!jsonPolicyBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPolicyBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePolicyBook(ReadOnlyPolicyBook policyBook) throws IOException {
        savePolicyBook(policyBook, filePath);
    }

    /**
     * Similar to {@link #savePolicyBook(ReadOnlyPolicyBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePolicyBook(ReadOnlyPolicyBook policyBook, Path filePath) throws IOException {
        requireNonNull(policyBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePolicyBook(policyBook), filePath);
    }
}
