package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeletePremiumCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletePremiumCommand;
import seedu.address.model.person.PremiumList;
import seedu.address.testutil.PremiumListBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePremiumCommand code. For example, inputs "1 pr/LifeShield" and "1 pr/LifeShield abc" take the
 * same path through the DeletePremiumCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePremiumCommandParserTest {

    private DeletePremiumCommandParser parser = new DeletePremiumCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePremiumCommand() {
        // Create a premium list with one premium for testing
        PremiumList premiumList = new PremiumListBuilder()
                .withPremium("LifeShield 0")
                .build();

        // Test with a single premium
        assertParseSuccess(
                parser,
                "1 " + PREFIX_PREMIUM + "LifeShield",
                new DeletePremiumCommand(INDEX_FIRST_PERSON, premiumList)
        );
    }

    @Test
    public void parse_missingPremium_throwsParseException() {
        assertParseFailure(
                parser,
                "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(
                parser,
                "a " + PREFIX_PREMIUM + "LifeShield $200",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(
                parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(
                parser,
                "-1 " + PREFIX_PREMIUM + "LifeShield $200",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(
                parser,
                "0 " + PREFIX_PREMIUM + "LifeShield $200",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(
                parser,
                PREFIX_PREMIUM + "LifeShield $200",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_emptyPremiumName_throwsParseException() {
        assertParseFailure(
                parser,
                "1 " + PREFIX_PREMIUM,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE)
        );
    }
}
