package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_nameWithSpacesOnly_throwsIllegalArgumentException() {
        String invalidName = "                      ";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("prata*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("cheese prata")); // alphabets only
        //assertTrue(Name.isValidName("12345")); // numbers only
        //assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Cheese Prata")); // with capital letters
        assertTrue(Name.isValidName("Beauty in a Pot with Kambing Soup")); // long names
    }

    @Test
    public void isEmptyName() {
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("           ")); // tons of spaces
    }

    @Test
    public void get_field_test() {
        String sampleString = "Chinese";
        assertEquals(new Name(sampleString).getField(), sampleString);
        assertNotEquals(new Name(sampleString).getField(), "");
    }

    @Test
    public void compareTests() {
        Name normalName = new Name("Malay");
        assertEquals(normalName.compareTo(null), 1);
        assertEquals(normalName.compareTo(normalName), 0);
    }
}
