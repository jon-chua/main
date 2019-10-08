package seedu.address.model.food;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Food in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    // Identity fields
    private final Name name;
    private final Price price;
    private final Description description;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Food(Name name, Price price, Description description, Set<Tag> tags) {
        requireAllNonNull(name, price, description, tags);
        this.name = name;
        this.price = price;
        this.description = description;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both foods of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two foods.
     */
    public boolean isSameFood(Food otherFood) {
        if (otherFood == this) {
            return true;
        }

        return otherFood != null
                && otherFood.getName().equals(getName())
                && (otherFood.getPrice().equals(getPrice()) || otherFood.getDescription().equals(getDescription()));
    }

    /**
     * Returns true if both foods have the same identity and data fields.
     * This defines a stronger notion of equality between two foods.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(getName())
                && otherFood.getPrice().equals(getPrice())
                && otherFood.getDescription().equals(getDescription())
                && otherFood.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, description, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: $")
                .append(getPrice())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
