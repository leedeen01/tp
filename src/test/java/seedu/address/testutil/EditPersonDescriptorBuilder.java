package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PremiumList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    /**
     * Creates a new EditPersonDescriptorBuilder with an empty descriptor.
     */
    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    /**
     * Creates a new EditPersonDescriptorBuilder with a copy of the given descriptor.
     *
     * @param descriptor The EditPersonDescriptor to copy
     */
    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Creates a new EditPersonDescriptorBuilder with fields containing the given person's details.
     *
     * @param person The Person whose details will be used to populate the descriptor
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
        descriptor.setPremium(person.getPremiumList());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param name The name to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param phone The phone number to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param email The email to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param address The address to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     *
     * @param tags The tags to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Premium} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param premiumList The premium list to set
     * @return this builder
     */
    public EditPersonDescriptorBuilder withPremiumList(PremiumList premiumList) {
        descriptor.setPremium(premiumList);
        return this;
    }

    /**
     * Sets the premium list of the {@code EditPersonDescriptor} using the specified premium string.
     * This method parses the provided premium string and sets it in the descriptor.
     * If the parsing fails, a {@code RuntimeException} is thrown.
     *
     * @param premium The premium string to be parsed and set in the descriptor.
     * @return The {@code EditPersonDescriptorBuilder} object, with the updated premium list.
     * @throws RuntimeException If the parsing of the premium string fails.
     */
    public EditPersonDescriptorBuilder withPremiumList(String premium) {
        try {
            descriptor.setPremium(ParserUtil.parsePremium(premium));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Builds and returns the configured EditPersonDescriptor.
     *
     * @return the configured EditPersonDescriptor
     */
    public EditPersonDescriptor build() {
        return descriptor;
    }
}
