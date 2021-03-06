package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.savenus.commons.core.Messages;
import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.wallet.exceptions.InsufficientFundsException;

//@@author raikonen
/**
 * Buy a food using it's displayed index from the menu.
 */
public class BuyCommand extends Command {

    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Buy food item identified by the index number used in the displayed food list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BUY_FOOD_SUCCESS = "Bought Food: %1$s";

    private final Index targetIndex;

    public BuyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToBuy = lastShownList.get(targetIndex.getZeroBased());
        Purchase purchaseToAdd = new Purchase(foodToBuy);
        try {
            model.buyFood(foodToBuy); // Throws insufficient funds command exception
        } catch (InsufficientFundsException e) {
            throw new CommandException(e.getMessage() + " to buy selected food!");
        }
        return new CommandResult(String.format(MESSAGE_BUY_FOOD_SUCCESS, purchaseToAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BuyCommand // instanceof handles nulls
                && targetIndex.equals(((BuyCommand) other).targetIndex)); // state check
    }
}
