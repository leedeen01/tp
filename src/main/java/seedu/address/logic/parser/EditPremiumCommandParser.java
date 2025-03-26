package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.EditPremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPremiumCommand object
 */
public class EditPremiumCommandParser implements Parser<EditPremiumCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPremiumCommand
     * and returns an EditPremiumCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPremiumCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREMIUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PREMIUM);

        EditPremiumCommand.EditPremiumDescriptor editPremiumDescriptor = new EditPremiumCommand.EditPremiumDescriptor();

        if (argMultimap.getValue(PREFIX_PREMIUM).isPresent()) {
            editPremiumDescriptor.addPremium(ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM).get()));
        }

        if (!editPremiumDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPremiumCommand(index, editPremiumDescriptor);
    }


}
