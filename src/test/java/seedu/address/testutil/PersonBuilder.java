package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Premium;
import seedu.address.model.person.PremiumList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "2002-01-01";
    public static final String DEFAULT_PREMIUM_NAME = "LifeShield";
    public static final Integer DEFAULT_PREMIUM_AMOUNT = 100;
    public static final ArrayList<Premium> DEFAULT_PREMIUM_LIST = new ArrayList<Premium>();

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Birthday birthday;
    private Set<Tag> tags;
    private Premium premium;
    private PremiumList premiumList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        tags = new HashSet<>();
        premium = new Premium(DEFAULT_PREMIUM_NAME, DEFAULT_PREMIUM_AMOUNT);
        DEFAULT_PREMIUM_LIST.add(premium);
        this.premiumList = new PremiumList(DEFAULT_PREMIUM_LIST);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        birthday = personToCopy.getBirthday();
        tags = new HashSet<>(personToCopy.getTags());
        premiumList = personToCopy.getPremiumList();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }
    /**
     * Sets the {@code Premium} of the {@code Person} that we are building.
     */
    public PersonBuilder withPremiumList(PremiumList premiumList) {
        this.premiumList = premiumList;
        return this;
    }

    /**
     * Sets the premium list for the {@code PersonBuilder} using the provided premium input string.
     * This method parses the given premium input string and assigns it to the premium list.
     * If the parsing fails, an {@code IllegalArgumentException} is thrown.
     *
     * @param premiumInput The premium input string to be parsed and assigned to the premium list.
     * @return The {@code PersonBuilder} object, with the updated premium list.
     * @throws IllegalArgumentException If the parsing of the premium input string fails.
     */
    public PersonBuilder withPremiumList(String premiumInput) {
        try {
            this.premiumList = ParserUtil.parsePremium(premiumInput);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, birthday, premiumList, tags);
    }

}
