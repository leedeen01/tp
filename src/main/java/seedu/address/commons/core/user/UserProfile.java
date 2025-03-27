package seedu.address.commons.core.user;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * A Serializable class that contains the User Profile.
 * Guarantees: immutable.
 */
public class UserProfile implements Serializable {

    private static final Name DEFAULT_NAME = new Name("Guest Name");
    private static final Email DEFAULT_EMAIL = new Email("guest@gmail.com");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");

    private Name name;
    private Email email;
    private Phone phone;

    /**
     * Constructs a {@code UserProfile} with default values.
     */
    public UserProfile() {
        this.name = DEFAULT_NAME;
        this.email = DEFAULT_EMAIL;
        this.phone = DEFAULT_PHONE;
    }

    /**
     * Constructs a {@code UserProfile} with the given details.
     */
    public UserProfile(Name name, Email email, Phone phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setUserProfile(UserProfile currentProfile, UserProfile userProfile) {
        if (userProfile.getName() != null) {
            this.name = userProfile.getName();
        } else {
            this.name = currentProfile.getName();
        }
        if (userProfile.getEmail() != null) {
            this.email = userProfile.getEmail();
        } else {
            this.email = currentProfile.getEmail();
        }
        if (userProfile.getPhone() != null) {
            this.phone = userProfile.getPhone();
        } else {
            this.phone = currentProfile.getPhone();
        }
    }

    public void setUserProfileName(Name userProfileName) {
        if (!Objects.equals(userProfileName, DEFAULT_NAME)) {
            this.name = userProfileName;
        }
    }

    public void setUserProfileEmail(Email userProfileEmail) {
        if (!Objects.equals(userProfileEmail, DEFAULT_EMAIL)) {
            this.email = userProfileEmail;
        }
    }

    public void setUserProfilePhone(Phone userProfilePhone) {
        if (!Objects.equals(userProfilePhone, DEFAULT_PHONE)) {
            this.phone = userProfilePhone;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserProfile)) {
            return false;
        }

        UserProfile otherProfile = (UserProfile) other;
        return name.equals(otherProfile.name)
                && email.equals(otherProfile.email)
                && phone.equals(otherProfile.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .toString();
    }
}
