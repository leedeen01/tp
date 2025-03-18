package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyPremiumBook;

/**
 * A class to access PremiumBook data stored as a json file on the hard disk.
 */
public class JsonPremiumBookStorage implements PremiumBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPremiumBookStorage.class);

    private Path filePath;

    public JsonPremiumBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPremiumBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPremiumBook> readPremiumBook() throws DataLoadingException {
        return readPremiumBook(filePath);
    }

    /**
     * Similar to {@link #readPremiumBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPremiumBook> readPremiumBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializablePremiumBook> jsonPremiumBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePremiumBook.class);
        if (!jsonPremiumBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPremiumBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePremiumBook(ReadOnlyPremiumBook premiumBook) throws IOException {
        savePremiumBook(premiumBook, filePath);
    }

    /**
     * Similar to {@link #savePremiumBook(ReadOnlyPremiumBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePremiumBook(ReadOnlyPremiumBook premiumBook, Path filePath) throws IOException {
        requireNonNull(premiumBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePremiumBook(premiumBook), filePath);
    }
}