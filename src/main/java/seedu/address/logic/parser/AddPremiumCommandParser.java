package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddPremiumCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PremiumList;

/**
 * Parses input arguments and creates a new AddPremiumCommand object.
 * This parser handles the command to add premium information to a person
 * in the address book application.
 */
public class AddPremiumCommandParser implements Parser<AddPremiumCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPremiumCommand
     * and returns an AddPremiumCommand object for execution.
     *
     * @param args the arguments to parse, should contain an index and premium information
     * @return the AddPremiumCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     *         or if required fields are missing or invalid
     */
    public AddPremiumCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREMIUM);

        if (!arePrefixesPresent(argMultimap, PREFIX_PREMIUM) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PREMIUM);

        PremiumList premiumList = null;

        if (!argMultimap.getValue(PREFIX_PREMIUM).isPresent()) {
            throw new ParseException(AddPremiumCommand.MESSAGE_INVALID_PPREMIUM);
        }

        if (argMultimap.getValue(PREFIX_PREMIUM).get().isBlank()) {
            throw new ParseException(AddPremiumCommand.MESSAGE_INVALID_PPREMIUM);
        }

        if (argMultimap.getValue(PREFIX_PREMIUM).isPresent()) {
            premiumList = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM).get());
        }

        if (premiumList == null) {
            throw new ParseException(AddPremiumCommand.MESSAGE_INVALID_PPREMIUM);
        }

        if (premiumList.isEmpty()) {
            throw new ParseException(AddPremiumCommand.MESSAGE_NOT_EDITED);
        }

        return new AddPremiumCommand(index, premiumList);
    }
}
