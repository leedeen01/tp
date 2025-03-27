package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER_PHONE;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.logic.commands.ProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new ProfileCommand object
 */
public class ProfileCommandParser implements Parser<ProfileCommand> {

    @Override
    public ProfileCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProfileCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USER_NAME, PREFIX_USER_PHONE, PREFIX_USER_EMAIL);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProfileCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_USER_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_USER_PHONE).isEmpty()
                && argMultimap.getValue(PREFIX_USER_EMAIL).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProfileCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_USER_NAME, PREFIX_USER_PHONE, PREFIX_USER_EMAIL);

        Name updatedName = null;
        Email updatedEmail = null;
        Phone updatedPhone = null;
        if (argMultimap.getValue(PREFIX_USER_NAME).isPresent()) {
            updatedName = ParserUtil.parseName(argMultimap.getValue(PREFIX_USER_NAME).get());
        }
        if (argMultimap.getValue(PREFIX_USER_EMAIL).isPresent()) {
            updatedEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_USER_EMAIL).get());
        }
        if (argMultimap.getValue(PREFIX_USER_PHONE).isPresent()) {
            updatedPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_USER_PHONE).get());
        }

        UserProfile userProfile = new UserProfile(updatedName, updatedEmail, updatedPhone);
        return new ProfileCommand(userProfile);
    }
}
