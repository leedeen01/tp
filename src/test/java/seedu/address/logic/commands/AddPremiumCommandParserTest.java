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
import seedu.address.logic.commands.AddPremiumCommand;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PremiumListBuilder;

/**
 * Tests for the AddPremiumCommandParser
 */
public class AddPremiumCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPremiumCommand.MESSAGE_USAGE);

    private AddPremiumCommandParser parser = new AddPremiumCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PREMIUM_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddPremiumCommand.MESSAGE_NOT_EDITED);

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

        PremiumList premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_AMY).build();
        AddPremiumCommand expectedCommand = new AddPremiumCommand(targetIndex, premiumList);

        assertParseSuccess(parser, userInput, expectedCommand);

        // another valid premium value
        targetIndex = INDEX_SECOND_PERSON;
        userInput = targetIndex.getOneBased() + PREMIUM_DESC_BOB;

        premiumList = new PremiumListBuilder().withPremium(VALID_PREMIUM_BOB).build();
        expectedCommand = new AddPremiumCommand(targetIndex, premiumList);

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

    @Test
    public void parse_invalidPremium_failure() {
        // No premium value (i.e., just the prefix)
        assertParseFailure(parser, "1 pr/", AddPremiumCommand.MESSAGE_INVALID_PPREMIUM);

        // Empty premium list
        assertParseFailure(parser, "1 pr/ ", AddPremiumCommand.MESSAGE_NOT_EDITED);
    }
}