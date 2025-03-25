package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PolicyBook;
import seedu.address.model.ReadOnlyPolicyBook;
import seedu.address.model.policy.Policy;

/**
 * An Immutable PolicyBook that is serializable to JSON format.
 */
@JsonRootName(value = "policybook")
class JsonSerializablePolicyBook {

    public static final String MESSAGE_DUPLICATE_POLICY = "Policies list contains duplicate policy(s).";

    private final List<JsonAdaptedPolicy> policies = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePolicyBook} with the given policies.
     */
    @JsonCreator
    public JsonSerializablePolicyBook(@JsonProperty("policies") List<JsonAdaptedPolicy> policies) {
        this.policies.addAll(policies);
    }

    /**
     * Converts a given {@code ReadOnlyPolicyBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePolicyBook}.
     */
    public JsonSerializablePolicyBook(ReadOnlyPolicyBook source) {
        policies.addAll(source.getPolicyList().stream().map(JsonAdaptedPolicy::new).collect(Collectors.toList()));
    }

    /**
     * Converts this policy book into the model's {@code PolicyBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PolicyBook toModelType() throws IllegalValueException {
        PolicyBook policyBook = new PolicyBook();
        for (JsonAdaptedPolicy jsonAdaptedPolicy : policies) {
            Policy policy = jsonAdaptedPolicy.toModelType();
            if (policyBook.hasPolicy(policy)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_POLICY);
            }
            policyBook.addPolicy(policy);
        }
        return policyBook;
    }
}
