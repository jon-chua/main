package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.AutoSortCommand;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.commands.BuyCommand;
import seedu.savenus.logic.commands.ClearCommand;
import seedu.savenus.logic.commands.CustomSortCommand;
import seedu.savenus.logic.commands.DefaultCommand;
import seedu.savenus.logic.commands.DeleteCommand;
import seedu.savenus.logic.commands.DislikeCommand;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.ExitCommand;
import seedu.savenus.logic.commands.FilterCommand;
import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.HelpCommand;
import seedu.savenus.logic.commands.HistoryCommand;
import seedu.savenus.logic.commands.LikeCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.MakeSortCommand;
import seedu.savenus.logic.commands.RecommendCommand;
import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.logic.commands.ViewSortCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;
import seedu.savenus.testutil.FoodBuilder;
import seedu.savenus.testutil.FoodUtil;

public class SaveNusParserTest {

    private final Model model = new ModelManager();
    private final SaveNusParser parser = new SaveNusParser();

    @Test
    public void parseCommand_add() throws Exception {
        Food food = new FoodBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(model.getAliasList(), FoodUtil.getAddCommand(food));
        assertEquals(new AddCommand(food), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(model.getAliasList(), ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        List<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_FOOD);
        DeleteCommand command = (DeleteCommand) parser.parseCommand(model.getAliasList(),
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_FOOD.getOneBased());
        assertEquals(new DeleteCommand(indexes), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(model.getAliasList(), ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Food food = new FoodBuilder().withTags("Food").build();
        EditCommand.EditFoodDescriptor descriptor = new EditFoodDescriptorBuilder(food).build();
        EditCommand command = (EditCommand) parser.parseCommand(model.getAliasList(), EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_FOOD.getOneBased() + " " + FoodUtil.getEditFoodDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_FOOD, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(model.getAliasList(),
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(model.getAliasList(), HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(model.getAliasList(), ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), SortCommand.COMMAND_WORD + " " + FIELD_NAME_NAME
            + " " + ASCENDING_DIRECTION) instanceof SortCommand);
    }

    @Test
    public void parseCommand_default() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), DefaultCommand.COMMAND_WORD) instanceof DefaultCommand);
        assertTrue(parser.parseCommand(model.getAliasList(), DefaultCommand.COMMAND_WORD + " 3")
                instanceof DefaultCommand);
    }

    @Test
    public void parseCommand_budget() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), BudgetCommand.COMMAND_WORD + " 100 25")
                instanceof BudgetCommand);
    }

    @Test
    public void parseCommand_buy() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), BuyCommand.COMMAND_WORD + " 1") instanceof BuyCommand);
    }

    @Test
    public void parseCommand_recommend() throws Exception {
        assertTrue(parser.parseCommand(model.getAliasList(), RecommendCommand.COMMAND_WORD)
                instanceof RecommendCommand);
    }

    @Test
    public void parseCommand_like() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), LikeCommand.COMMAND_WORD + " c/Chinese")
                        instanceof LikeCommand);
    }

    @Test
    public void parseCommand_dislike() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), DislikeCommand.COMMAND_WORD + " c/Chinese")
                     instanceof DislikeCommand);
    }

    @Test
    public void parseCommand_customSort() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), CustomSortCommand.COMMAND_WORD) instanceof CustomSortCommand);
    }

    @Test
    public void parseCommand_makeSort() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), MakeSortCommand.COMMAND_WORD + " " + FIELD_NAME_NAME
                        + " " + ASCENDING_DIRECTION) instanceof MakeSortCommand);
    }

    @Test
    public void parseCommand_history() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_save() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), SaveCommand.COMMAND_WORD + " 100.00")
                        instanceof SaveCommand
        );
    }

    @Test
    public void parseCommand_filter() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), FilterCommand.COMMAND_WORD + " " + FIELD_NAME_PRICE
                        + " " + QUANTIFY_EQUALS_TO + " 4.00") instanceof FilterCommand);
    }

    @Test
    public void parseCommand_autosort() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), AutoSortCommand.COMMAND_WORD + " ON")
                        instanceof AutoSortCommand);
    }

    @Test
    public void parseCommand_viewsort() throws ParseException {
        assertTrue(
                parser.parseCommand(model.getAliasList(), ViewSortCommand.COMMAND_WORD) instanceof ViewSortCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(model.getAliasList(), ""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(model.getAliasList(),
                "unknownCommand"));
    }
}
