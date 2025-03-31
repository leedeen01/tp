package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddPremiumCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;


import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PremiumList;

public class AddPremiumCommandParser implements Parser<AddPremiumCommand> {
    public AddPremiumCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PREMIUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PREMIUM);

        PremiumList premiumList = null;

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
