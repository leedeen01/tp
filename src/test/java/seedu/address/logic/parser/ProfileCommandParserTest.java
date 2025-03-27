package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_PHONE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.commands.ProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class ProfileCommandParserTest {

    private final ProfileCommandParser parser = new ProfileCommandParser();

    @Test
    public void parse_validArgs_returnsProfileCommand() throws Exception {
        String userInput = " " + PREFIX_USER_NAME + " Alice " + PREFIX_USER_PHONE + " 91234567 "
                + PREFIX_USER_EMAIL + " alice@example.com";
        ProfileCommand expectedCommand = new ProfileCommand(new UserProfile(new Name("Alice"),
                new Email("alice@example.com"), new Phone("91234567")));
        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_missingAllFields_throwsParseException() {
        String userInput = "";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidEmailFormat_throwsParseException() {
        String userInput = " " + PREFIX_USER_EMAIL + " invalid-email";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidPhoneFormat_throwsParseException() {
        String userInput = " " + PREFIX_USER_PHONE + " abc123";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateFields_throwsParseException() {
        String userInput = " " + PREFIX_USER_NAME + " Alice " + PREFIX_USER_NAME + " Bob";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
