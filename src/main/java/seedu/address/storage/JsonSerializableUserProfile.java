package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.user.UserProfile;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * An Immutable UserProfile that is serializable to JSON format.
 */
@JsonRootName(value = "userProfile")
class JsonSerializableUserProfile {

    private final String name;
    private final String email;
    private final String phone;

    /**
     * Constructs a {@code JsonSerializableUserProfile} with the given details.
     */
    @JsonCreator
    public JsonSerializableUserProfile(@JsonProperty("name") String name,
                                       @JsonProperty("email") String email,
                                       @JsonProperty("phone") String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Converts a given {@code UserProfile} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
     */
    public JsonSerializableUserProfile(UserProfile source) {
        this.name = source.getName().fullName;
        this.email = source.getEmail().value;
        this.phone = source.getPhone().value;
    }

    /**
     * Converts this JSON-friendly version of {@code UserProfile} into the model's {@code UserProfile} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserProfile toModelType() throws IllegalValueException {
        final Name modelName = new Name(name);
        final Email modelEmail = new Email(email);
        final Phone modelPhone = new Phone(phone);

        return new UserProfile(modelName, modelEmail, modelPhone);
    }
}
