package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for person */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PREMIUM = new Prefix("pr/");

    /* Prefix definitions for premium */
    public static final Prefix PREFIX_POLICY_NUMBER = new Prefix("pn/");
    public static final Prefix PREFIX_POLICY_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PROVIDER_COMPANY = new Prefix("pc/");
    public static final Prefix PREFIX_POLICY_LINK = new Prefix("pl/");
}
