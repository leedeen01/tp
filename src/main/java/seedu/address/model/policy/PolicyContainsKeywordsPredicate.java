package seedu.address.model.policy;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Policy}'s {@code Name} matches any of the keywords given.
 */
public class PolicyContainsKeywordsPredicate implements Predicate<Policy> {
    private final List<String> keywords;

    public PolicyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Policy policy) {
        return keywords.stream()
                .anyMatch(keyword -> policy.getPolicyName().policyName
                        .toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyContainsKeywordsPredicate)) {
            return false;
        }

        PolicyContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (PolicyContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
