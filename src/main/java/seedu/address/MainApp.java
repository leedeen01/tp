package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PolicyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPolicyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonPolicyBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonUserProfileStorage;
import seedu.address.storage.PolicyBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.UserProfileStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 5, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        PolicyBookStorage policyBookStorage = new JsonPolicyBookStorage(userPrefs.getPolicyBookFilePath());
        UserProfileStorage userProfileStorage = new JsonUserProfileStorage(userPrefs.getUserProfileFilePath());
        storage = new StorageManager(addressBookStorage, policyBookStorage, userPrefsStorage, userProfileStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s
     * address book and premium book and {@code userPrefs}.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getAddressBookFilePath());
        logger.info("Using premium data file : " + storage.getPolicyBookFilePath());
        logger.info("Using profile data file : " + storage.getUserProfileFilePath());

        Optional<ReadOnlyAddressBook> addressBookOptional;
        Optional<ReadOnlyPolicyBook> policyBookOptional;
        Optional<UserProfile> userProfileOptional;
        ReadOnlyAddressBook initialAddressBookData;
        ReadOnlyPolicyBook initialPolicyBookData;
        UserProfile initialUserProfileData;

        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getAddressBookFilePath()
                        + " populated with a sample AddressBook.");
            }
            initialAddressBookData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getAddressBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            initialAddressBookData = new AddressBook();
        }

        try {
            policyBookOptional = storage.readPolicyBook();
            if (!policyBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getPolicyBookFilePath()
                        + " populated with a sample PolicyBook.");
            }
            initialPolicyBookData = policyBookOptional.orElseGet(SampleDataUtil::getSamplePolicyBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getPolicyBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty PolicyBook.");
            initialPolicyBookData = new PolicyBook();
        }

        try {
            userProfileOptional = storage.readUserProfile();
            if (!userProfileOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getUserProfileFilePath()
                        + " populated with a default user profile.");
            }
            initialUserProfileData = new UserProfile();
        } catch (Exception e) {
            logger.warning("Data file at " + storage.getPolicyBookFilePath() + " could not be loaded."
                    + " Will be starting with a default user profile.");
            initialUserProfileData = new UserProfile();
        }

        return new ModelManager(initialAddressBookData, initialPolicyBookData, userPrefs,
                initialUserProfileData, storage);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ClientNest" + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ClientNest ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.saveAddressBook(model.getAddressBook());
            storage.savePolicyBook(model.getPolicyBook());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
