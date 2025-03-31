package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREMIUM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREMIUM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREMIUM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PREMIUM_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPremiumCommand;
import seedu.address.logic.commands.EditPremiumCommand.EditPremiumDescriptor;
import seedu.address.model.person.Premium;
import seedu.address.testutil.EditPremiumDescriptorBuilder;

public class EditPremiumCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPremiumCommand.MESSAGE_USAGE);

    private EditPremiumCommandParser parser = new EditPremiumCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PREMIUM_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREMIUM_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREMIUM_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid premium
        assertParseFailure(parser, "1" + INVALID_PREMIUM_DESC, Premium.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_premiumSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PREMIUM_DESC_AMY;

        EditPremiumDescriptor descriptor = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_AMY).build();
        EditPremiumCommand expectedCommand = new EditPremiumCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // another valid premium value
        targetIndex = INDEX_SECOND_PERSON;
        userInput = targetIndex.getOneBased() + PREMIUM_DESC_BOB;

        descriptor = new EditPremiumDescriptorBuilder().withPremium(VALID_PREMIUM_BOB).build();
        expectedCommand = new EditPremiumCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by valid (duplicate premium)
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PREMIUM_DESC_AMY + PREMIUM_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PREMIUM));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_PREMIUM_DESC + PREMIUM_DESC_AMY;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PREMIUM));

        // valid followed by invalid
        userInput = targetIndex.getOneBased() + PREMIUM_DESC_AMY + INVALID_PREMIUM_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PREMIUM));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PREMIUM_DESC + INVALID_PREMIUM_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PREMIUM));
    }
}
