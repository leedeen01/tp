package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyPolicyBook;

/**
 * Represents a storage for {@link seedu.address.model.PolicyBook}.
 */
public interface PolicyBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPolicyBookFilePath();

    /**
     * Returns PolicyBook data as a {@link ReadOnlyPolicyBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPolicyBook> readPolicyBook() throws DataLoadingException;

    /**
     * @see #getPolicyBookFilePath()
     */
    Optional<ReadOnlyPolicyBook> readPolicyBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPolicyBook} to the storage.
     * @param policyBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePolicyBook(ReadOnlyPolicyBook policyBook) throws IOException;

    /**
     * @see #savePolicyBook(ReadOnlyPolicyBook)
     */
    void savePolicyBook(ReadOnlyPolicyBook policyBook, Path filePath) throws IOException;
}
