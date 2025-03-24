package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_POLICY_POL101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_POLICY_POL456;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_LINK_POL456;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_POL456;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NUMBER_POL456;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROVIDER_COMPANY_POL456;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.testutil.EditPolicyDescriptorBuilder;

public class EditPolicyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPolicyDescriptor descriptorWithSameValues = new EditPolicyDescriptor(DESC_POLICY_POL101);
        assertTrue(DESC_POLICY_POL101.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_POLICY_POL101.equals(DESC_POLICY_POL101));

        // null -> returns false
        assertFalse(DESC_POLICY_POL101.equals(null));

        // different types -> returns false
        assertFalse(DESC_POLICY_POL101.equals(5));

        // different values -> returns false
        assertFalse(DESC_POLICY_POL101.equals(DESC_POLICY_POL456));

        // different policy name -> returns false
        EditPolicyDescriptor editedPolicy = new EditPolicyDescriptorBuilder(DESC_POLICY_POL101)
                .withPolicyName(VALID_POLICY_NAME_POL456).build();
        assertFalse(DESC_POLICY_POL101.equals(editedPolicy));

        // different policy number -> returns false
        editedPolicy = new EditPolicyDescriptorBuilder(DESC_POLICY_POL101)
                .withPolicyNumber(VALID_POLICY_NUMBER_POL456).build();
        assertFalse(DESC_POLICY_POL101.equals(editedPolicy));

        // different provider company -> returns false
        editedPolicy = new EditPolicyDescriptorBuilder(DESC_POLICY_POL101)
                .withProviderCompany(VALID_PROVIDER_COMPANY_POL456).build();
        assertFalse(DESC_POLICY_POL101.equals(editedPolicy));

        // different policy link -> returns false
        editedPolicy = new EditPolicyDescriptorBuilder(DESC_POLICY_POL101)
                .withPolicyLink(VALID_POLICY_LINK_POL456).build();
        assertFalse(DESC_POLICY_POL101.equals(editedPolicy));
    }

    @Test
    public void toStringMethod() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor();
        String expected = EditPolicyDescriptor.class.getCanonicalName() + "{policyName="
                + descriptor.getPolicyName().orElse(null) + ", policyNumber="
                + descriptor.getPolicyNumber().orElse(null) + ", providerCompany="
                + descriptor.getProviderCompany().orElse(null) + ", policyLink="
                + descriptor.getPolicyLink().orElse(null) + "}";
        assertEquals(expected, descriptor.toString());
    }
}
