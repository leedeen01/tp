package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;

/**
 * Parses input arguments and creates a new AddPolicyCommand object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddPolicyCommand
     * and returns an AddPolicyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_POLICY_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_POLICY_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_POLICY_NAME,
                PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY,
                PREFIX_POLICY_LINK);

        PolicyNumber policyNumber = ParserUtil.parsePolicyNumber(argMultimap.getValue(PREFIX_POLICY_NUMBER).get());
        PolicyName policyName = ParserUtil.parsePolicyName(argMultimap.getValue(PREFIX_POLICY_NAME).get());
        ProviderCompany providerCompany = ParserUtil
                .parseProviderCompany(argMultimap.getValue(PREFIX_PROVIDER_COMPANY).get());
        PolicyLink policyLink = ParserUtil.parsePolicyLink(argMultimap.getValue(PREFIX_POLICY_LINK).get());

        Policy policy = new Policy(policyName, policyNumber, providerCompany, policyLink);

        return new AddPolicyCommand(policy);
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
