package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeletePremiumCommand object.
 * Accepts an index number representing a person whose premiums will be deleted.
 */
public class DeletePremiumCommandParser implements Parser<DeletePremiumCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePremiumCommand
     * and returns a DeletePremiumCommand object for execution.
     *
     * @param args the arguments to parse, expected to be a trimmed index number
     * @return the DeletePremiumCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeletePremiumCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePremiumCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}