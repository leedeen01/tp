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
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public void createDefaultAddressBook(Path filePath) throws IOException {
        String defaultJson = """
            {
              "persons" : [ {
                "name" : "Alice Tan",
                "phone" : "91234567",
                "email" : "alice.tan@example.com",
                "address" : "12 Bedok South Rd, #05-12",
                "birthday" : "1998-05-10",
                "tags" : [ "colleague" ],
                "premiumList" : {
                  "premiumList" : [ {
                    "premiumName" : "LifeShield",
                    "premiumAmount" : 500
                  } ]
                }
              }, {
                "name" : "Bryan Lim",
                "phone" : "87654321",
                "email" : "bryan.lim@example.com",
                "address" : "21 Bukit Batok St 52, #10-33",
                "birthday" : "1995-09-08",
                "tags" : [ "friend", "classmate" ],
                "premiumList" : {
                  "premiumList" : [ {
                    "premiumName" : "AutoCare",
                    "premiumAmount" : 700
                  }, {
                    "premiumName" : "Vivo360",
                    "premiumAmount" : 3000
                  } ]
                }
              }, {
                "name" : "Catherine Wong",
                "phone" : "96543218",
                "email" : "cathyw@example.com",
                "address" : "55 Yishun Ave 6, #03-09",
                "birthday" : "2001-02-14",
                "tags" : [ "friend" ],
                "premiumList" : {
                  "premiumList" : [ {
                    "premiumName" : "LifeShield",
                    "premiumAmount" : 1000
                  }, {
                    "premiumName" : "HealthPlus",
                    "premiumAmount" : 500
                  }, {
                    "premiumName" : "HomeSafe",
                    "premiumAmount" : 1000
                  }, {
                    "premiumName" : "TravelPlus",
                    "premiumAmount" : 1500
                  } ]
                }
              }, {
                "name" : "David Koh",
                "phone" : "99887766",
                "email" : "dkoh@example.com",
                "address" : "89 Jurong West St 41, #12-07",
                "birthday" : "1993-07-20",
                "tags" : [ "friend" ],
                "premiumList" : {
                  "premiumList" : [ ]
                }
              }, {
                "name" : "Elaine Ng",
                "phone" : "93456789",
                "email" : "elaine.ng@example.com",
                "address" : "30 Ang Mo Kio Ave 2, #08-16",
                "birthday" : "2000-11-30",
                "tags" : [ ],
                "premiumList" : {
                  "premiumList" : [ {
                    "premiumName" : "Vivo360",
                    "premiumAmount" : 1000
                  }, {
                    "premiumName" : "HomeSafe",
                    "premiumAmount" : 2000
                  } ]
                }
              } ]
            }
            """;
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, defaultJson, StandardCharsets.UTF_8);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            try {
                createDefaultAddressBook(filePath);
            } catch (IOException e) {
                logger.warning("Failed to create default address book file: " + e.getMessage());
                throw new DataLoadingException(e);
            }
        }

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

}
