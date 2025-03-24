package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPolicy.ETF_BONDS;
import static seedu.address.testutil.TypicalPolicy.GREAT_INVESTMENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.exceptions.DuplicatePolicyException;
import seedu.address.model.policy.exceptions.PolicyNotFoundException;
import seedu.address.testutil.PolicyBuilder;

public class UniquePolicyListTest {

    private final UniquePolicyList uniquePolicyList = new UniquePolicyList();

    @Test
    public void contains_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.contains(null));
    }

    @Test
    public void contains_policyNotInList_returnsFalse() {
        assertFalse(uniquePolicyList.contains(GREAT_INVESTMENT));
    }

    @Test
    public void contains_policyInList_returnsTrue() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        assertTrue(uniquePolicyList.contains(GREAT_INVESTMENT));
    }

    @Test
    public void contains_policyWithSameIdentityFieldsInList_returnsTrue() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        Policy editedPolicyA = new PolicyBuilder(GREAT_INVESTMENT)
                .withPolicyLink("https://www.investcorp.com/policy102")
                .build();
        assertTrue(uniquePolicyList.contains(editedPolicyA));
    }

    @Test
    public void add_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.add(null));
    }

    @Test
    public void add_duplicatePolicy_throwsDuplicatePolicyException() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.add(GREAT_INVESTMENT));
    }

    @Test
    public void setPolicy_nullTargetPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(null, GREAT_INVESTMENT));
    }

    @Test
    public void setPolicy_nullEditedPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy(GREAT_INVESTMENT, null));
    }

    @Test
    public void setPolicy_targetPolicyNotInList_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () ->
            uniquePolicyList.setPolicy(GREAT_INVESTMENT, GREAT_INVESTMENT));
    }

    @Test
    public void setPolicy_editedPolicyIsSamePolicy_success() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        uniquePolicyList.setPolicy(GREAT_INVESTMENT, GREAT_INVESTMENT);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(GREAT_INVESTMENT);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasSameIdentity_success() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        Policy editedPolicyA = new PolicyBuilder(GREAT_INVESTMENT)
                .withPolicyLink("https://www.investcorp.com/policy102")
                .build();
        uniquePolicyList.setPolicy(GREAT_INVESTMENT, editedPolicyA);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(editedPolicyA);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasDifferentIdentity_success() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        uniquePolicyList.setPolicy(GREAT_INVESTMENT, ETF_BONDS);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(ETF_BONDS);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicy_editedPolicyHasNonUniqueIdentity_throwsDuplicatePolicyException() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        uniquePolicyList.add(ETF_BONDS);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.setPolicy(GREAT_INVESTMENT, ETF_BONDS));
    }

    @Test
    public void remove_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.remove(null));
    }

    @Test
    public void remove_policyDoesNotExist_throwsPolicyNotFoundException() {
        assertThrows(PolicyNotFoundException.class, () -> uniquePolicyList.remove(GREAT_INVESTMENT));
    }

    @Test
    public void remove_existingPolicy_removesPolicy() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        uniquePolicyList.remove(GREAT_INVESTMENT);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullUniquePolicyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy((UniquePolicyList) null));
    }

    @Test
    public void setPolicies_uniquePolicyList_replacesOwnListWithProvidedUniquePolicyList() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(ETF_BONDS);
        uniquePolicyList.setPolicy(expectedUniquePolicyList);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePolicyList.setPolicy((List<Policy>) null));
    }

    @Test
    public void setPolicies_list_replacesOwnListWithProvidedList() {
        uniquePolicyList.add(GREAT_INVESTMENT);
        List<Policy> policyList = Collections.singletonList(ETF_BONDS);
        uniquePolicyList.setPolicy(policyList);
        UniquePolicyList expectedUniquePolicyList = new UniquePolicyList();
        expectedUniquePolicyList.add(ETF_BONDS);
        assertEquals(expectedUniquePolicyList, uniquePolicyList);
    }

    @Test
    public void setPolicies_listWithDuplicatePolicies_throwsDuplicatePolicyException() {
        List<Policy> listWithDuplicatePolicies = Arrays.asList(GREAT_INVESTMENT, GREAT_INVESTMENT);
        assertThrows(DuplicatePolicyException.class, () -> uniquePolicyList.setPolicy(listWithDuplicatePolicies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePolicyList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePolicyList.asUnmodifiableObservableList().toString(), uniquePolicyList.toString());
    }
}
