package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;

//@@author jon-chua
/**
 * Adds the user's disliked recommendations to the $aveNUS recommendation system.
 */
public class DislikeCommand extends PreferenceCommand {

    public static final String COMMAND_WORD = "dislike";
    private boolean isList;

    public DislikeCommand(boolean isList) {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), isList);
    }

    /**
     * Creates an DislikeCommand to add the user's recommendations
     */
    public DislikeCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList, boolean isList) {
        super(categoryList, tagList, locationList, isList);
        this.isList = isList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return this.execute(model, false, isList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DislikeCommand // instanceof handles nulls
                && categoryList.equals(((DislikeCommand) other).categoryList))
                && tagList.equals(((DislikeCommand) other).tagList)
                && locationList.equals(((DislikeCommand) other).locationList)
                && isList == ((DislikeCommand) other).isList;
    }
}
