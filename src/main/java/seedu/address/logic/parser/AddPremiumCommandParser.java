package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPremiumCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.premium.PolicyLink;
import seedu.address.model.premium.PolicyNumber;
import seedu.address.model.premium.Premium;
import seedu.address.model.premium.PremiumName;
import seedu.address.model.premium.ProviderCompany;

/**
 * Parses input arguments and creates a new AddPremiumCommand object
 */
public class AddPremiumCommandParser implements Parser<AddPremiumCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPremiumCommand
     * and returns an AddPremiumCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPremiumCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PREMIUM_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_PREMIUM_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPremiumCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PREMIUM_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK);

        PolicyNumber policyNumber = ParserUtil.parsePolicyNumber(argMultimap.getValue(PREFIX_POLICY_NUMBER).get());
        PremiumName premiumName = ParserUtil.parsePremiumName(argMultimap.getValue(PREFIX_PREMIUM_NAME).get());
        ProviderCompany providerCompany = ParserUtil
                .parseProviderCompany(argMultimap.getValue(PREFIX_PROVIDER_COMPANY).get());
        PolicyLink policyLink = ParserUtil.parsePolicyLink(argMultimap.getValue(PREFIX_POLICY_LINK).get());

        Premium premium = new Premium(premiumName, policyNumber, providerCompany, policyLink);

        return new AddPremiumCommand(premium);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}