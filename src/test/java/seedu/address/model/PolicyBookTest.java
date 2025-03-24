package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        // Create a duplicate: editedEtf has the same identity as ETF_BONDS but with a different policy link.
        Policy editedEtf = new PolicyBuilder(ETF_BONDS)
                .withPolicyLink(VALID_POLICY_LINK) // assume policyLink is a non-identity field
                .build();
        List<Policy> newPolicies = Arrays.asList(ETF_BONDS, editedEtf);
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
        // Create a policy with the same identity as ETF_BONDS but with a different policy link.
        Policy editedEtf = new PolicyBuilder(ETF_BONDS)
                .withPolicyLink(VALID_POLICY_LINK)
                .build();
        assertTrue(policyBook.hasPolicy(editedEtf));
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

    // =================== New Tests for setPolicy, equals, hashCode ===================

    @Test
    public void setPolicy_validTargetAndEditedPolicy_success() {
        // Add an initial policy to the policyBook.
        Policy originalPolicy = ETF_BONDS;
        policyBook.addPolicy(originalPolicy);

        // Create an edited version of the policy (e.g., with a changed policy link).
        Policy editedPolicy = new PolicyBuilder(originalPolicy)
                .withPolicyLink(VALID_POLICY_LINK)
                .build();
        policyBook.setPolicy(originalPolicy, editedPolicy);

        // Retrieve the updated policy from the list.
        Policy updatedPolicy = policyBook.getPolicyList().get(0);

        // Verify that the updated policy equals the edited policy.
        assertEquals(editedPolicy, updatedPolicy);

        // Since isSamePolicy ignores policyLink, both originalPolicy and editedPolicy have the same identity.
        // Therefore, hasPolicy for originalPolicy will still return true.
        assertTrue(policyBook.hasPolicy(originalPolicy));
    }

    @Test
    public void setPolicy_targetNotInList_throwsPolicyNotFoundException() {
        Policy nonExistentPolicy = new PolicyBuilder(ETF_BONDS)
                .withPolicyNumber("NONEXISTENT")
                .build();
        Policy editedPolicy = new PolicyBuilder(ETF_BONDS)
                .withPolicyLink(VALID_POLICY_LINK)
                .build();
        assertThrows(seedu.address.model.policy.exceptions.PolicyNotFoundException.class, () ->
            policyBook.setPolicy(nonExistentPolicy, editedPolicy));
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        assertTrue(policyBook.equals(policyBook));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(policyBook.equals("NotAPolicyBook"));
    }

    @Test
    public void equals_samePolicies_returnsTrue() {
        PolicyBook pb1 = new PolicyBook();
        PolicyBook pb2 = new PolicyBook();
        pb1.addPolicy(ETF_BONDS);
        pb2.addPolicy(ETF_BONDS);
        assertTrue(pb1.equals(pb2));
        // Ensure hashCode is the same for equal objects.
        assertEquals(pb1.hashCode(), pb2.hashCode());
    }

    @Test
    public void equals_differentPolicies_returnsFalse() {
        PolicyBook pb1 = new PolicyBook();
        PolicyBook pb2 = new PolicyBook();
        pb1.addPolicy(ETF_BONDS);
        Policy differentPolicy = new PolicyBuilder(ETF_BONDS)
                .withPolicyName("Different Policy")
                .build();
        pb2.addPolicy(differentPolicy);
        assertFalse(pb1.equals(pb2));
        // Their hash codes should not be equal.
        assertNotEquals(pb1.hashCode(), pb2.hashCode());
    }

    // =================== Helper Stub Class ===================
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
