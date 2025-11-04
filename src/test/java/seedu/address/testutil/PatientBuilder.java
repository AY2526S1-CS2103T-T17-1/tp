package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "1990-01-01";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_EMERGENCY = "Emergency Contact";
    public static final String DEFAULT_ID = "S1234567A";
    public static final String DEFAULT_LANG = "English";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Birthday birthday;
    private String gender;
    private String emergency;
    private String id;
    private String lang;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        gender = DEFAULT_GENDER;
        emergency = DEFAULT_EMERGENCY;
        id = DEFAULT_ID;
        lang = DEFAULT_LANG;
    }

    /**
     * Initializes the PatientBuilder with the data of {@code personToCopy}.
     */
    public PatientBuilder(Patient personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        birthday = personToCopy.getBirthday();
        gender = personToCopy.getGender();
        emergency = personToCopy.getEmergency();
        id = personToCopy.getId();
        lang = personToCopy.getLang();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Sets the {@code Emergency} contact of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmergency(String emergency) {
        this.emergency = emergency;
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Patient} that we are building.
     */
    public PatientBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Language} of the {@code Patient} that we are building.
     */
    public PatientBuilder withLang(String lang) {
        this.lang = lang;
        return this;
    }

    public Patient build() {
        return new Patient(name, birthday, gender, phone, email, address, emergency, id, lang);
    }
}
