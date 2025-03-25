package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;


public class AddPolicyCommandParserTest {

    // A valid input string with all required prefixes and values.
    private static final String VALID_INPUT = " "
        + CliSyntax.PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
        + CliSyntax.PREFIX_POLICY_NUMBER + VALID_POLICY_NUMBER + " "
        + CliSyntax.PREFIX_PROVIDER_COMPANY + VALID_PROVIDER_COMPANY + " "
        + CliSyntax.PREFIX_POLICY_LINK + VALID_POLICY_LINK;

    private final AddPolicyCommandParser parser = new AddPolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Policy expectedPolicy = new Policy(
                new PolicyName(VALID_POLICY_NAME),
                new PolicyNumber(VALID_POLICY_NUMBER),
                new ProviderCompany(VALID_PROVIDER_COMPANY),
                new PolicyLink(VALID_POLICY_LINK));
        AddPolicyCommand expectedCommand = new AddPolicyCommand(expectedPolicy);

        AddPolicyCommand result = parser.parse(VALID_INPUT);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingPrefix_failure() {
        // Missing the policy link prefix.
        String inputMissingPolicyLink = " "
                + CliSyntax.PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + CliSyntax.PREFIX_POLICY_NUMBER + VALID_POLICY_NUMBER + " "
                + CliSyntax.PREFIX_PROVIDER_COMPANY + VALID_PROVIDER_COMPANY;
        assertThrows(ParseException.class, () -> parser.parse(inputMissingPolicyLink));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Any non-empty preamble should cause a failure.
        String inputInvalidPreamble = "unexpected preamble " + VALID_INPUT;
        assertThrows(ParseException.class, () -> parser.parse(inputInvalidPreamble));
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        // Duplicate the policy name prefix should result in a ParseException.
        String inputDuplicatePrefix = " "
                + CliSyntax.PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + CliSyntax.PREFIX_POLICY_NAME + VALID_POLICY_NAME + " "
                + CliSyntax.PREFIX_POLICY_NUMBER + VALID_POLICY_NUMBER + " "
                + CliSyntax.PREFIX_PROVIDER_COMPANY + VALID_PROVIDER_COMPANY + " "
                + CliSyntax.PREFIX_POLICY_LINK + VALID_POLICY_LINK;
        assertThrows(ParseException.class, () -> parser.parse(inputDuplicatePrefix));
    }
}
