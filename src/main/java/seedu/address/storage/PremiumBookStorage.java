package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPremiumBook;

/**
 * Represents a storage for {@link seedu.address.model.PremiumBook}.
 */
public interface PremiumBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPremiumBookFilePath();

    /**
     * Returns PremiumBook data as a {@link ReadOnlyPremiumBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPremiumBook> readPremiumBook() throws DataLoadingException;

    /**
     * @see #getPremiumBookFilePath()
     */
    Optional<ReadOnlyPremiumBook> readPremiumBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPremiumBook} to the storage.
     * @param premiumBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePremiumBook(ReadOnlyPremiumBook premiumBook) throws IOException;

    /**
     * @see #savePremiumBook(ReadOnlyPremiumBook)
     */
    void savePremiumBook(ReadOnlyPremiumBook premiumBook, Path filePath) throws IOException;
}
