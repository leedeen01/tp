package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPolicyCommand;
import seedu.address.model.policy.PolicyContainsKeywordsPredicate;

public class FindPolicyCommandParserTest {

    private FindPolicyCommandParser parser = new FindPolicyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPolicyCommand() {
        // no leading and trailing whitespaces
        FindPolicyCommand expectedFindPolicyCommand =
                new FindPolicyCommand(new PolicyContainsKeywordsPredicate(Arrays.asList("LifeShield", "Health")));
        assertParseSuccess(parser, "LifeShield Health", expectedFindPolicyCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n LifeShield \n \t Health  \t", expectedFindPolicyCommand);
    }
}
