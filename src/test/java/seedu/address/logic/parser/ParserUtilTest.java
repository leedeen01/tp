package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;
import seedu.address.model.policy.PolicyLink;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.PolicyNumber;
import seedu.address.model.policy.ProviderCompany;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    //Constants for Premium
    private static final String VALID_PREMIUM_1 = "P001";
    private static final String VALID_PREMIUM_2 = "P002";
    private static final String INVALID_PREMIUM = "!@#$";

    // Constants for policy fields
    private static final String VALID_POLICY_NUMBER = "PN12345678";
    private static final String VALID_POLICY_NAME = "Life Insurance";
    private static final String VALID_PROVIDER_COMPANY = "BestInsure";
    private static final String VALID_POLICY_LINK = "http://www.bestinsure.com/policy";

    private static final String INVALID_POLICY_NUMBER = "!!!"; // Assumed to be invalid
    private static final String INVALID_POLICY_NAME = ""; // Empty string is invalid
    private static final String INVALID_PROVIDER_COMPANY = ""; // Empty string is invalid
    private static final String INVALID_POLICY_LINK = "invalidLink"; // Assumed to be invalid

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    // ================= Policy Parser Tests =================

    @Test
    public void parsePolicyNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicyNumber(null));
    }

    @Test
    public void parsePolicyNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyNumber(INVALID_POLICY_NUMBER));
    }

    @Test
    public void parsePolicyNumber_validValueWithoutWhitespace_returnsPolicyNumber() throws Exception {
        PolicyNumber expectedPolicyNumber = new PolicyNumber(VALID_POLICY_NUMBER);
        assertEquals(expectedPolicyNumber, ParserUtil.parsePolicyNumber(VALID_POLICY_NUMBER));
    }

    @Test
    public void parsePolicyNumber_validValueWithWhitespace_returnsTrimmedPolicyNumber() throws Exception {
        String policyNumberWithWhitespace = WHITESPACE + VALID_POLICY_NUMBER + WHITESPACE;
        PolicyNumber expectedPolicyNumber = new PolicyNumber(VALID_POLICY_NUMBER);
        assertEquals(expectedPolicyNumber, ParserUtil.parsePolicyNumber(policyNumberWithWhitespace));
    }

    @Test
    public void parsePolicyName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicyName(null));
    }

    @Test
    public void parsePolicyName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyName(INVALID_POLICY_NAME));
    }

    @Test
    public void parsePolicyName_validValueWithoutWhitespace_returnsPolicyName() throws Exception {
        PolicyName expectedPolicyName = new PolicyName(VALID_POLICY_NAME);
        assertEquals(expectedPolicyName, ParserUtil.parsePolicyName(VALID_POLICY_NAME));
    }

    @Test
    public void parsePolicyName_validValueWithWhitespace_returnsTrimmedPolicyName() throws Exception {
        String policyNameWithWhitespace = WHITESPACE + VALID_POLICY_NAME + WHITESPACE;
        PolicyName expectedPolicyName = new PolicyName(VALID_POLICY_NAME);
        assertEquals(expectedPolicyName, ParserUtil.parsePolicyName(policyNameWithWhitespace));
    }

    @Test
    public void parseProviderCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProviderCompany(null));
    }

    @Test
    public void parseProviderCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProviderCompany(INVALID_PROVIDER_COMPANY));
    }

    @Test
    public void parseProviderCompany_validValueWithoutWhitespace_returnsProviderCompany() throws Exception {
        ProviderCompany expectedProviderCompany = new ProviderCompany(VALID_PROVIDER_COMPANY);
        assertEquals(expectedProviderCompany, ParserUtil.parseProviderCompany(VALID_PROVIDER_COMPANY));
    }

    @Test
    public void parseProviderCompany_validValueWithWhitespace_returnsTrimmedProviderCompany() throws Exception {
        String providerCompanyWithWhitespace = WHITESPACE + VALID_PROVIDER_COMPANY + WHITESPACE;
        ProviderCompany expectedProviderCompany = new ProviderCompany(VALID_PROVIDER_COMPANY);
        assertEquals(expectedProviderCompany, ParserUtil.parseProviderCompany(providerCompanyWithWhitespace));
    }

    @Test
    public void parsePolicyLink_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePolicyLink(null));
    }

    @Test
    public void parsePolicyLink_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePolicyLink(INVALID_POLICY_LINK));
    }

    @Test
    public void parsePolicyLink_validValueWithoutWhitespace_returnsPolicyLink() throws Exception {
        PolicyLink expectedPolicyLink = new PolicyLink(VALID_POLICY_LINK);
        assertEquals(expectedPolicyLink, ParserUtil.parsePolicyLink(VALID_POLICY_LINK));
    }

    @Test
    public void parsePolicyLink_validValueWithWhitespace_returnsTrimmedPolicyLink() throws Exception {
        String policyLinkWithWhitespace = WHITESPACE + VALID_POLICY_LINK + WHITESPACE;
        PolicyLink expectedPolicyLink = new PolicyLink(VALID_POLICY_LINK);
        assertEquals(expectedPolicyLink, ParserUtil.parsePolicyLink(policyLinkWithWhitespace));
    }

    @Test
    public void parseDeletePremium_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeletePremium(null));
    }

    @Test
    public void parseDeletePremium_emptyString_returnsEmptyPremiumList() throws Exception {
        PremiumList expectedList = new PremiumList();
        assertEquals(expectedList, ParserUtil.parseDeletePremium(""));
    }

    @Test
    public void parseDeletePremium_singleValidValue_returnsPremiumList() throws Exception {
        PremiumList expectedList = new PremiumList();
        expectedList.add(new Premium(VALID_PREMIUM_1, 0));

        assertEquals(expectedList, ParserUtil.parseDeletePremium(VALID_PREMIUM_1));
    }

    @Test
    public void parseDeletePremium_multipleValidValues_returnsPremiumList() throws Exception {
        PremiumList expectedList = new PremiumList();
        expectedList.add(new Premium(VALID_PREMIUM_1, 0));
        expectedList.add(new Premium(VALID_PREMIUM_2, 0));

        assertEquals(expectedList,
                ParserUtil.parseDeletePremium(VALID_PREMIUM_1 + " " + VALID_PREMIUM_2));
    }

    @Test
    public void parseDeletePremium_validValueWithWhitespace_returnsTrimmedPremiumList() throws Exception {
        String premiumsWithWhitespace = WHITESPACE + VALID_PREMIUM_1 + " "
                + VALID_PREMIUM_2 + WHITESPACE;

        PremiumList expectedList = new PremiumList();
        expectedList.add(new Premium(VALID_PREMIUM_1, 0));
        expectedList.add(new Premium(VALID_PREMIUM_2, 0));

        assertEquals(expectedList, ParserUtil.parseDeletePremium(premiumsWithWhitespace));
    }
}
