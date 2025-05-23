package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "There is an error with your command! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_POLICY_DISPLAYED_INDEX = "The policy index provided is invalid";
    public static final String MESSAGE_POLICIES_LISTED_OVERVIEW = "%1$d policies listed!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(System.lineSeparator())
                .append("Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Birthday: ")
                .append(person.getBirthday())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user when editing premiums.
     */
    public static String formatPremium(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(System.lineSeparator())
                .append(person.getPremiumList().toString());

        return builder.toString();
    }

    /**
     * Formats the {@code policy} for display to the user.
     */
    public static String formatPolicy(Policy policy) {
        final StringBuilder builder = new StringBuilder();
        builder.append(policy.getPolicyNumber())
                .append(System.lineSeparator())
                .append("Policy Name: ")
                .append(policy.getPolicyName())
                .append("; Policy Number: ")
                .append(policy.getPolicyNumber())
                .append("; Provider Company: ")
                .append(policy.getProviderCompany())
                .append("; Policy Link: ")
                .append(policy.getPolicyLink());
        return builder.toString();
    }

}
