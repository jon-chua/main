package seedu.savenus.logic.commands;

import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.recommend.UserRecommendations;

//@@author jon-chua
/**
 * Creates a PreferenceCommand that either adds likes or dislikes to the $aveNUS recommendation system.
 */
public class PreferenceCommand extends Command {
    public static final String COMMAND_WORD = "like/dislike";
    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Likes or dislikes a particular category, tag "
            + "or location in our menu. Parameters: [" + PREFIX_CATEGORY + "CATEGORY]... [" + PREFIX_TAG + "TAG]... ["
            + PREFIX_LOCATION + "...]\n" + "Example: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "Chinese "
            + PREFIX_CATEGORY + "Western " + PREFIX_LOCATION + "University Town " + PREFIX_LOCATION + "The Deck "
            + PREFIX_TAG + "Spicy " + PREFIX_TAG + "Healthy";

    public static final String DUPLICATE_FOUND_IN_OPPOSITE_LIST = "Duplicate entry found in opposing list!\n"
            + "Entries cannot exist in both liked and disliked sets at the same time!";

    public final Set<Category> categoryList;
    public final Set<Tag> tagList;
    public final Set<Location> locationList;

    private final boolean isList;

    /**
     * Creates an PreferenceCommand to add the user's recommendations
     */
    public PreferenceCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList, boolean isList) {
        requireAllNonNull(categoryList, tagList, locationList, isList);

        // Convert all to lowercase
        this.categoryList = categoryList.stream().parallel()
                .map(c -> new Category(c.category.toLowerCase())).collect(Collectors.toSet());
        this.tagList = tagList.stream().parallel()
                .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet());
        this.locationList = locationList.stream().parallel()
                .map(l -> new Location(l.location.toLowerCase())).collect(Collectors.toSet());

        this.isList = isList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Executes the preference command.
     *
     * @param model  The current model
     * @param isLike True if adding likes or false if adding dislikes
     * @return A success message including the list of likes and dislikes
     */
    public CommandResult execute(Model model, boolean isLike, boolean isList) throws CommandException {
        StringBuilder result = new StringBuilder();

        RecommendationSystem recommendationSystem = model.getRecommendationSystem();
        UserRecommendations userRecommendations = recommendationSystem.getUserRecommendations();

        if (isLike && !isList) {
            // Throws a command exception if any of the likes are in dislikes or vice versa
            if (userRecommendations.getDislikedCategories().stream().anyMatch(categoryList::contains)
                    || userRecommendations.getDislikedLocations().stream().anyMatch(locationList::contains)
                    || userRecommendations.getDislikedTags().stream().anyMatch(tagList::contains)) {
                throw new CommandException(DUPLICATE_FOUND_IN_OPPOSITE_LIST);
            }

            model.addLikes(categoryList, tagList, locationList);
            result.append("Liked: ");
        } else if (!isLike && !isList) {
            // Throws a command exception if any of the likes are in dislikes or vice versa
            if (userRecommendations.getLikedCategories().stream().anyMatch(categoryList::contains)
                    || userRecommendations.getLikedLocations().stream().anyMatch(locationList::contains)
                    || userRecommendations.getLikedTags().stream().anyMatch(tagList::contains)) {
                throw new CommandException(DUPLICATE_FOUND_IN_OPPOSITE_LIST);
            }

            model.addDislikes(categoryList, tagList, locationList);
            result.append("Disliked: ");
        }

        if (!isList) {
            String addedItems = "Categories: " + categoryList.stream()
                    .map(c -> c.category).collect(Collectors.joining(", "))
                    + " | Tags: " + tagList.stream()
                    .map(t -> t.tagName).collect(Collectors.joining(", "))
                    + " | Locations: " + locationList.stream()
                    .map(l -> l.location).collect(Collectors.joining(", ")) + "\n";

            result.append(addedItems);
        }

        result.append(userRecommendations.toString());

        if (!isList) {
            return new CommandResult(MESSAGE_SUCCESS + " " + result);
        } else {
            return new CommandResult(result.toString());
        }
    }
}
