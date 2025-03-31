package seedu.address.testutil;

import seedu.address.logic.commands.EditPremiumCommand.EditPremiumDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PremiumList;

/**
 * A utility class to help with building EditPremiumDescriptor objects.
 */
public class EditPremiumDescriptorBuilder {

    private EditPremiumDescriptor descriptor;

    /**
     * Creates a new EditPremiumDescriptorBuilder with an empty descriptor.
     */
    public EditPremiumDescriptorBuilder() {
        descriptor = new EditPremiumDescriptor();
    }

    /**
     * Creates a new EditPremiumDescriptorBuilder with a copy of the given descriptor.
     *
     * @param descriptor The EditPremiumDescriptor to copy
     */
    public EditPremiumDescriptorBuilder(EditPremiumDescriptor descriptor) {
        this.descriptor = new EditPremiumDescriptor(descriptor);
    }

    /**
     * Creates a new EditPremiumDescriptorBuilder with fields containing the given person's premium details.
     *
     * @param person The Person whose premium details will be used to populate the descriptor
     */
    public EditPremiumDescriptorBuilder(Person person) {
        descriptor = new EditPremiumDescriptor();
        descriptor.setPremium(person.getPremiumList());
    }

    /**
     * Sets the {@code Premium} of the {@code EditPremiumDescriptor} that we are building.
     *
     * @param premiumList The premium list to set
     * @return this builder
     */
    public EditPremiumDescriptorBuilder withPremium(PremiumList premiumList) {
        descriptor.setPremium(premiumList);
        return this;
    }

    /**
     * Sets the premium of the {@code EditPremiumDescriptor} using the specified premium string.
     * This method parses the provided premium string and sets it in the descriptor.
     * If the parsing fails, a {@code RuntimeException} is thrown.
     *
     * @param premium The premium string to be parsed and set in the descriptor.
     * @return The {@code EditPremiumDescriptorBuilder} object, with the updated premium.
     * @throws RuntimeException If the parsing of the premium string fails.
     */
    public EditPremiumDescriptorBuilder withPremium(String premium) {
        try {
            descriptor.setPremium(ParserUtil.parsePremium(premium));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Builds and returns the configured EditPremiumDescriptor.
     *
     * @return the configured EditPremiumDescriptor
     */
    public EditPremiumDescriptor build() {
        return descriptor;
    }
}
