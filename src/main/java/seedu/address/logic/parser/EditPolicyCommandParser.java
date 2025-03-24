package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROVIDER_COMPANY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditPolicyCommand object.
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_POLICY_NAME, PREFIX_POLICY_NUMBER, PREFIX_PROVIDER_COMPANY, PREFIX_POLICY_LINK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_NAME, PREFIX_POLICY_NUMBER,
                PREFIX_PROVIDER_COMPANY, PREFIX_POLICY_LINK);

        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();

        if (argMultimap.getValue(PREFIX_POLICY_NAME).isPresent()) {
            editPolicyDescriptor.setPolicyName(ParserUtil.parsePolicyName(
                    argMultimap.getValue(PREFIX_POLICY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_NUMBER).isPresent()) {
            editPolicyDescriptor.setPolicyNumber(ParserUtil.parsePolicyNumber(
                    argMultimap.getValue(PREFIX_POLICY_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_PROVIDER_COMPANY).isPresent()) {
            editPolicyDescriptor.setProviderCompany(ParserUtil.parseProviderCompany(
                    argMultimap.getValue(PREFIX_PROVIDER_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_LINK).isPresent()) {
            editPolicyDescriptor.setPolicyLink(ParserUtil.parsePolicyLink(
                    argMultimap.getValue(PREFIX_POLICY_LINK).get()));
        }

        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }
}
