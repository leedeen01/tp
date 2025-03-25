package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_LINK_DESC_POL101;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NAME_DESC_POL101;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NUMBER_DESC_POL101;
import static seedu.address.logic.commands.CommandTestUtil.PROVIDER_COMPANY_DESC_POL101;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_POLICY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.testutil.EditPolicyDescriptorBuilder;

/**
 * Contains tests for parsing edit policy commands.
 */
public class EditPolicyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE);

    private EditPolicyCommandParser parser = new EditPolicyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, POLICY_NAME_DESC_POL101, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPolicyCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + POLICY_NAME_DESC_POL101, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + POLICY_NAME_DESC_POL101, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 x/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_POLICY;
        String userInput = targetIndex.getOneBased() + POLICY_NAME_DESC_POL101
                + POLICY_NUMBER_DESC_POL101 + PROVIDER_COMPANY_DESC_POL101 + POLICY_LINK_DESC_POL101;

        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyName(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_POL101)
                .withPolicyNumber(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER_POL101)
                .withProviderCompany(seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY_POL101)
                .withPolicyLink(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK_POL101)
                .build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_POLICY;
        String userInput = targetIndex.getOneBased() + POLICY_NAME_DESC_POL101 + POLICY_LINK_DESC_POL101;

        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyName(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_POL101)
                .withPolicyLink(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK_POL101)
                .build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_POLICY;

        // policy name only
        String userInput = targetIndex.getOneBased() + POLICY_NAME_DESC_POL101;
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyName(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_POL101)
                .build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // policy number only
        userInput = targetIndex.getOneBased() + POLICY_NUMBER_DESC_POL101;
        descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyNumber(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER_POL101)
                .build();
        expectedCommand = new EditPolicyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // provider company only
        userInput = targetIndex.getOneBased() + PROVIDER_COMPANY_DESC_POL101;
        descriptor = new EditPolicyDescriptorBuilder()
                .withProviderCompany(seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY_POL101)
                .build();
        expectedCommand = new EditPolicyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // policy link only
        userInput = targetIndex.getOneBased() + POLICY_LINK_DESC_POL101;
        descriptor = new EditPolicyDescriptorBuilder()
                .withPolicyLink(seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK_POL101)
                .build();
        expectedCommand = new EditPolicyCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // Duplicate policy name prefix
        Index targetIndex = INDEX_FIRST_POLICY;
        String userInput = targetIndex.getOneBased() + POLICY_NAME_DESC_POL101 + POLICY_NAME_DESC_POL101;

        assertParseFailure(parser, userInput,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_POLICY_NAME));
    }
}
