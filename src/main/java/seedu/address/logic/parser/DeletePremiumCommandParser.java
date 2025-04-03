package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeletePremiumCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeletePremiumCommand object.
 * Accepts an index number representing a person and premium names to identify
 * which specific premiums will be deleted from that person.
 */
public class DeletePremiumCommandParser implements Parser<DeletePremiumCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePremiumCommand
     * and returns a DeletePremiumCommand object for execution.
     *
     * @param args the arguments to parse, which should include an index number followed by premium names
     *             with the premium prefix
     * @return the DeletePremiumCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format, such as missing
     *                        index, missing premium names, or duplicate premium prefixes
     */
    public DeletePremiumCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREMIUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PREMIUM);

        if (argMultimap.getValue(PREFIX_PREMIUM).isPresent() && !argMultimap.getValue(PREFIX_PREMIUM).get().isEmpty()) {
            return new DeletePremiumCommand(index, ParserUtil.parseDeletePremium(argMultimap.getValue(PREFIX_PREMIUM)
                    .get()));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

}
