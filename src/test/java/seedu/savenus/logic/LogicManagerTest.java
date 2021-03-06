package seedu.savenus.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_DESC_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_DESC_CHICKEN_RICE;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CHICKEN_RICE;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.DeleteCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.ReadOnlySavingsHistory;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.savings.SavingsHistory;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.UserPrefs;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.storage.StorageManager;
import seedu.savenus.storage.alias.JsonAliasListStorage;
import seedu.savenus.storage.menu.JsonMenuStorage;
import seedu.savenus.storage.purchase.JsonPurchaseHistoryStorage;
import seedu.savenus.storage.recommend.JsonRecsStorage;
import seedu.savenus.storage.savings.JsonSavingsAccountStorage;
import seedu.savenus.storage.savings.JsonSavingsStorage;
import seedu.savenus.storage.sort.JsonCustomSortStorage;
import seedu.savenus.storage.userprefs.JsonUserPrefsStorage;
import seedu.savenus.storage.wallet.JsonWalletStorage;
import seedu.savenus.storage.wallet.WalletStorage;
import seedu.savenus.testutil.FoodBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonMenuStorage menuStorage =
                new JsonMenuStorage(temporaryFolder.resolve("savenus-menu.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonSavingsStorage savingsHistoryStorage = new JsonSavingsStorage(temporaryFolder.resolve("savings.json"));
        JsonSavingsAccountStorage savingsAccountStorage = new JsonSavingsAccountStorage(
                    temporaryFolder.resolve("userPrefs-savings-account.json"));
        JsonRecsStorage userRecsStorage = new JsonRecsStorage(temporaryFolder.resolve("userPrefs-recs.json"));
        JsonPurchaseHistoryStorage purchaseHistoryStorage = new JsonPurchaseHistoryStorage(temporaryFolder
                .resolve("userPrefs-purchases.json"));
        JsonWalletStorage walletStorage = new JsonWalletStorage(temporaryFolder
                .resolve("userPrefs-wallet.json"));
        JsonCustomSortStorage customSortStorage = new JsonCustomSortStorage(
                temporaryFolder.resolve("userPrefs-sort.json")
        );
        JsonAliasListStorage aliasListStorage = new JsonAliasListStorage(
                temporaryFolder.resolve("userPrefs-alias.json")
        );
        StorageManager storage = new StorageManager(menuStorage, userPrefsStorage, userRecsStorage,
                purchaseHistoryStorage, walletStorage, customSortStorage, savingsHistoryStorage, savingsAccountStorage,
                aliasListStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, DeleteCommand.NO_FOOD_TO_DELETE_ERROR);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonMenuIoExceptionThrowingStub
        JsonMenuStorage menuStorage =
                new JsonMenuIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMenu.json"));
        JsonSavingsStorage savingsHistoryStorage =
                new JsonSavingsIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionSavings.json"));
        JsonSavingsAccountStorage savingsAccountStorage = new JsonSavingsAccountIoExceptionThrowingStub(
                temporaryFolder.resolve("ioExceptionSavingsAccount.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonRecsStorage userRecsStorage = new JsonRecsStorage(temporaryFolder.resolve("ioExceptionUserRecs.json"));
        JsonPurchaseHistoryStorage purchaseHistoryStorage = new JsonPurchaseHistoryStorage(temporaryFolder
                .resolve("ioExceptionPurchaseHistory.json"));
        WalletStorage walletStorage = new JsonWalletStorage(temporaryFolder
                .resolve("ioExceptionWallet.json"));
        JsonCustomSortStorage customSortStorage = new JsonCustomSortStorage(
                temporaryFolder.resolve("ioExceptionUserRecs.json")
        );
        JsonAliasListStorage aliasListStorage = new JsonAliasListStorage(
                temporaryFolder.resolve("ioExceptionAliasList.json")
        );
        StorageManager storage = new StorageManager(menuStorage, userPrefsStorage, userRecsStorage,
                purchaseHistoryStorage, walletStorage, customSortStorage, savingsHistoryStorage, savingsAccountStorage,
                aliasListStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_CHICKEN_RICE
                + PRICE_DESC_CHICKEN_RICE + DESCRIPTION_DESC_CHICKEN_RICE
                + CATEGORY_DESC_CHICKEN_RICE + LOCATION_DESC_CHICKEN_RICE
                + OPENING_HOURS_DESC_CHICKEN_RICE + RESTRICTIONS_DESC_CHICKEN_RICE;
        Food expectedFood = new FoodBuilder(CHICKEN_RICE).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addFood(expectedFood);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredfoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFoodList().remove(0));
    }

    @Test
    public void getMenu_succesfullGet() {
        assertTrue(logic.getMenu() instanceof ReadOnlyMenu);
    }

    @Test
    public void getPurchaseHistoryList_successfulGet() {
        assertTrue(logic.getPurchaseHistoryList() instanceof ObservableList);
    }

    @Test
    public void getMenuFilePath_successfulGet() {
        assertTrue(logic.getMenuFilePath() instanceof Path);
    }

    @Test
    public void getGuiSettings_successfulGet() {
        assertTrue(logic.getGuiSettings() instanceof GuiSettings);
    }

    @Test
    public void setGuiSettings_success() {
        Logic newLogic = logic;
        logic.setGuiSettings(logic.getGuiSettings());
        assertEquals(logic, newLogic);
    }

    @Test
    public void autoSortFlag_tests() {
        Logic newLogic = logic;
        assertEquals(newLogic.getAutoSortFlag(), false);
    }

    @Test
    public void customSorter_tests() {
        CustomSorter sorter = new CustomSorter();
        assertEquals(logic.getCustomSorter(), sorter);
    }

    @Test
    public void setFoods_success() {
        logic.setFoods(new ArrayList<Food>());
        ObservableList<Food> foodList = new SortedList<Food>(logic.getFilteredFoodList());
        assertEquals(foodList, logic.getFilteredFoodList());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getMenu(), new UserPrefs(), new UserRecommendations(),
                new PurchaseHistory(), new Wallet(), new CustomSorter(), new SavingsHistory(), new SavingsAccount(),
                new AliasList());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonMenuIoExceptionThrowingStub extends JsonMenuStorage {
        private JsonMenuIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the deposit method is called.
     */
    private static class JsonSavingsIoExceptionThrowingStub extends JsonSavingsStorage {
        private JsonSavingsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSavingsHistory(ReadOnlySavingsHistory savingsHistory, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the withdraw method is called.
     */
    private static class JsonSavingsAccountIoExceptionThrowingStub extends JsonSavingsAccountStorage {
        private JsonSavingsAccountIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSavingsAccount(ReadOnlySavingsAccount savingsAccount, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
