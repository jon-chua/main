package seedu.savenus.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.recommend.RecommendationSystem;

//@@author robytanama
/**
 * An UI component that displays information of a {@code Food}.
 */
public class FoodCard extends UiPart<Region> {

    private static final String FXML = "FoodListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Food food;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label description;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;
    @FXML
    private Label locations;
    @FXML
    private Label openingHours;
    @FXML
    private Label restriction;
    @FXML
    private Label recommendation;

    public FoodCard(Food food, int displayedIndex) {
        super(FXML);
        this.food = food;
        id.setText(displayedIndex + "");
        name.setText(food.getName().fullName);
        price.setText("$" + food.getPrice().value);

        // Description is an optional field
        if (food.getDescription().value.equals(Description.DEFAULT_VALUE)) {
            description.setText("No description");
        } else {
            description.setText(food.getDescription().value);
        }
        category.setText("Category: " + food.getCategory().category);

        food.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Label for location.
        String location = "Location: " + food.getLocation().location;

        locations.setText(location);

        // Label for opening hours.
        String hour = "";
        if (food.getOpeningHours().openingHours.equals(OpeningHours.DEFAULT_VALUE)) {
            hour += "No opening hours specified";
        } else {
            String[] hours = food.getOpeningHours().openingHours.split(" ");
            String open = hours[0];
            String close = hours[1];
            hour += "Opens: " + open + " - Closes: " + close;
        }

        openingHours.setText(hour);

        // Label for restrictions.
        String restrictions = "";

        if (food.getRestrictions().restrictions.equals(Restrictions.DEFAULT_VALUE)) {
            restrictions += "No restrictions";
        } else {
            restrictions += "Restrictions: " + food.getRestrictions().restrictions;
        }

        restriction.setText(restrictions);

        // Label for recommendation.
        String recommendations = "";
        double recommendationValue = RecommendationSystem.getInstance().calculateRecommendation(food);

        if (recommendationValue >= 0) {
            recommendations += "+" + String.format("%.2f", recommendationValue);
        } else {
            recommendations += String.format("%.2f", recommendationValue);
        }

        recommendation.setText(recommendations);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FoodCard)) {
            return false;
        }

        // state check
        FoodCard card = (FoodCard) other;
        return id.getText().equals(card.id.getText())
                && food.equals(card.food);
    }
}
