
package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.menu.Menu;
import seedu.savenus.model.menu.ReadOnlyMenu;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.purchase.ReadOnlyPurchaseHistory;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.ReadOnlySavingsHistory;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.exceptions.InsufficientSavingsException;
import seedu.savenus.model.savings.exceptions.SavingsOutOfBoundException;
import seedu.savenus.model.sort.CustomSorter;
import seedu.savenus.model.userprefs.ReadOnlyUserPrefs;
import seedu.savenus.model.wallet.Wallet;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;
import seedu.savenus.testutil.FoodBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingfoodAdded modelStub = new ModelStubAcceptingfoodAdded();
        Food validFood = new FoodBuilder().build();

        CommandResult commandResult = new AddCommand(validFood).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFood), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFood), modelStub.foodAdded);
    }

    @Test
    public void execute_duplicatefood_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddCommand addCommand = new AddCommand(validFood);
        ModelStub modelStub = new ModelStubWithfood(validFood);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FOOD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food chickenRice = new FoodBuilder().withName("Chicken Rice").build();
        Food nasiLemak = new FoodBuilder().withName("Nasi Lemak").build();
        AddCommand addChickenRiceCommand = new AddCommand(chickenRice);
        AddCommand addNasiLemakCommand = new AddCommand(nasiLemak);

        // same object -> returns true
        assertTrue(addChickenRiceCommand.equals(addChickenRiceCommand));

        // same values -> returns true
        AddCommand addChickenRiceCommandCopy = new AddCommand(chickenRice);
        assertTrue(addChickenRiceCommand.equals(addChickenRiceCommandCopy));

        // different types -> returns false
        assertFalse(addChickenRiceCommand.equals(1));

        // null -> returns false
        assertFalse(addChickenRiceCommand.equals(null));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMenuFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuFilePath(Path menuFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPurchaseHistoryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPurchaseHistoryFilePath(Path purchaseHistoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getWalletFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWalletFilePath(Path walletFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWallet(Wallet newWallet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenu(ReadOnlyMenu newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMenu getMenu() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFood(Food target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFood(Food target, Food editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoods(List<Food> list) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFoods() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editFilteredFoodList(List<String> fieldList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PurchaseHistory getPurchaseHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPurchaseHistory(ReadOnlyPurchaseHistory purchaseHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPurchase(Purchase purchase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removePurchase(Purchase purchase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void buyFood(Food foodToBuy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Wallet getWallet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deductFromWallet(Price price) throws InsufficientFundsException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deductFromWallet(Savings savings) throws InsufficientFundsException {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Food> getFilteredFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Purchase> getPurchaseHistoryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public RecommendationSystem getRecommendationSystem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecommendationSystemInUse(boolean inUse) {
            // Empty stub
        }

        @Override
        public void setCustomSorter(List<String> fields) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CustomSorter getCustomSorter() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToHistory(Savings savings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySavingsHistory getSavingsHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSavingsHistory(ReadOnlySavingsHistory savingsHistory) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySavingsAccount getSavingsAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void depositInSavings(Savings savings) throws SavingsOutOfBoundException, InsufficientFundsException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void withdrawFromSavings(Savings savings) throws InsufficientSavingsException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<String> getCommandHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearLikes() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearDislikes() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean getAutoSortFlag() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAutoSortFlag(boolean autoSortFlag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AliasList getAliasList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAliasList(AliasList aliasList) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single food.
     */
    private class ModelStubWithfood extends ModelStub {
        private final Food food;

        ModelStubWithfood(Food food) {
            requireNonNull(food);
            this.food = food;
        }

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return this.food.isSameFood(food);
        }
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingfoodAdded extends ModelStub {
        final ArrayList<Food> foodAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodAdded.add(food);
        }

        @Override
        public ReadOnlyMenu getMenu() {
            return new Menu();
        }
    }
}
