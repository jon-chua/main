package seedu.savenus.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.food.Tag;

/**
 * A utility class to help with building EditFoodDescriptor objects.
 */
public class EditFoodDescriptorBuilder {

    private EditFoodDescriptor descriptor;

    public EditFoodDescriptorBuilder() {
        descriptor = new EditFoodDescriptor();
    }

    public EditFoodDescriptorBuilder(EditFoodDescriptor descriptor) {
        this.descriptor = new EditFoodDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFoodDescriptor} with fields containing {@code food}'s details
     */
    public EditFoodDescriptorBuilder(Food food) {
        descriptor = new EditFoodDescriptor();
        descriptor.setName(food.getName());
        descriptor.setPrice(food.getPrice());
        descriptor.setDescription(food.getDescription());
        descriptor.setCategory(food.getCategory());
        descriptor.setTags(food.getTags());
        descriptor.setLocation(food.getLocation());
        descriptor.setOpeningHours(food.getOpeningHours());
        descriptor.setRestrictions(food.getRestrictions());
    }

    /**
     * Sets the {@code Name} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFoodDescriptor}
     * that we are building.
     */
    public EditFoodDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code OpeningHours} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withOpeningHours(String openingHours) {
        descriptor.setOpeningHours(new OpeningHours(openingHours));
        return this;
    }

    /**
     * Sets the {@code Restrictions} of the {@code EditFoodDescriptor} that we are building.
     */
    public EditFoodDescriptorBuilder withRestrictions(String restrictions) {
        descriptor.setRestrictions(new Restrictions(restrictions));
        return this;
    }

    public EditFoodDescriptor build() {
        return descriptor;
    }
}
