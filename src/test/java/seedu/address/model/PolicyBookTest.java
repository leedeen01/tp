package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK;
import static seedu.address.testutil.TypicalPolicy.ETF_BONDS;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.exceptions.DuplicatePolicyException;
import seedu.address.testutil.PolicyBuilder;

public class PolicyBookTest {

    private final PolicyBook policyBook = new PolicyBook();

    @Test
    public void constructor_initializesEmptyPolicyList() {
        assertEquals(Collections.emptyList(), policyBook.getPolicyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> policyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPolicyBook_replacesData() {
        PolicyBook newData = new PolicyBook();
        newData.addPolicy(ETF_BONDS);
        policyBook.resetData(newData);
        assertEquals(newData, policyBook);
    }

    @Test
    public void resetData_withDuplicatePolicies_throwsDuplicatePolicyException() {
        // Create a duplicate: editedAlpha has the same identity as ETF_BONDS but with a different policy link.
        Policy editedAlpha = new PolicyBuilder(ETF_BONDS)
                .withPolicyLink(VALID_POLICY_LINK) // assume policyLink is non-identity field
                .build();
        List<Policy> newPolicies = Arrays.asList(ETF_BONDS, editedAlpha);
        PolicyBookStub newData = new PolicyBookStub(newPolicies);

        assertThrows(DuplicatePolicyException.class, () -> policyBook.resetData(newData));
    }

    @Test
    public void hasPolicy_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> policyBook.hasPolicy(null));
    }

    @Test
    public void hasPolicy_policyNotInPolicyBook_returnsFalse() {
        assertFalse(policyBook.hasPolicy(ETF_BONDS));
    }

    @Test
    public void hasPolicy_policyInPolicyBook_returnsTrue() {
        policyBook.addPolicy(ETF_BONDS);
        assertTrue(policyBook.hasPolicy(ETF_BONDS));
    }

    @Test
    public void hasPolicy_policyWithSameIdentityFieldsInPolicyBook_returnsTrue() {
        policyBook.addPolicy(ETF_BONDS);
        // Create a policy with same identity as ETF_BONDS but with a different policy link.
        Policy editedAlpha = new PolicyBuilder(ETF_BONDS)
                .withPolicyLink(VALID_POLICY_LINK)
                .build();
        assertTrue(policyBook.hasPolicy(editedAlpha));
    }

    @Test
    public void getPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> policyBook.getPolicyList().remove(0));
    }

    @Test
    public void toStringMethod_returnsExpectedFormat() {
        String expected = PolicyBook.class.getCanonicalName() + "{polycies=" + policyBook.getPolicyList() + "}";
        assertEquals(expected, policyBook.toString());
    }

    /**
     * A stub ReadOnlyPolicyBook whose policy list can violate interface constraints.
     */
    private static class PolicyBookStub implements ReadOnlyPolicyBook {
        private final ObservableList<Policy> policies = FXCollections.observableArrayList();

        PolicyBookStub(Collection<Policy> policies) {
            this.policies.setAll(policies);
        }

        @Override
        public ObservableList<Policy> getPolicyList() {
            return policies;
        }
    }
}
