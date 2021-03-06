package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "@yam";
    private static final String INVALID_PRICE = "+6500q9";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_LOCATION = "37@+";
    private static final String INVALID_CATEGORY = "+++";

    private static final String VALID_NAME = "Ayam Penyet";
    private static final String VALID_PRICE = "123";
    private static final String VALID_DESCRIPTION = "A valid description.";
    private static final String VALID_TAG_1 = "Chicken";
    private static final String VALID_TAG_2 = "Rice";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(
            ParseException.class,
            MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1))
        );
    }

    @Test
    public void parseIndexes_outOfRangeInput_throwsParseException() {
        assertThrows(
                ParseException.class,
                MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndexes(Long.toString(Integer.MAX_VALUE + 1))
        );
    }

    @Test
    public void parseValidIndexes_test() throws ParseException {
        List<Index> indexes = new ArrayList<Index>();
        indexes.add(Index.fromOneBased(3));
        indexes.add(Index.fromOneBased(4));
        assertEquals(ParserUtil.parseIndexes("3 4"), indexes);
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_FOOD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_FOOD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseLocations_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocations(null));
    }

    @Test
    public void parseLocations_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseLocations(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseLocations_collectionWithInvalidLocations_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(INVALID_LOCATION)));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseCategories_collectionWithInvalidLocations_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(Arrays.asList(INVALID_CATEGORY)));
    }

    @Test
    public void parse_collectionWithValidTags_returnsTagSet() throws ParseException {
        String validOpeningHours = "0000 0001";
        String invalidOpeningHours = "abcd efgh";
        assertTrue(ParserUtil.parseOpeningHours(validOpeningHours).equals(new OpeningHours(validOpeningHours)));
        assertThrows(ParseException.class, () -> ParserUtil.parseOpeningHours(invalidOpeningHours));
        assertTrue(ParserUtil.parseOpeningHours(OpeningHours.DEFAULT_VALUE)
                   .equals(new OpeningHours(OpeningHours.DEFAULT_VALUE)));
    }
}
