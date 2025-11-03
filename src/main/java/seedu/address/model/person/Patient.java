package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // all references to Tag are removed
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Birthday birthday;
    private final String gender;
    private final String emergency;
    private final String id;
    private final String lang;
    private final Address address;

    private static final String[] GENDERS = {"MALE", "FEMALE", "OTHER"};


    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Birthday birthday, String gender, Phone phone, Email email,
                   Address address, String emergency, String id, String lang) {
        // optional fields: email, address

        requireAllNonNull(name, birthday, gender, phone, emergency, id, lang);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        isValidGender(gender);
        this.gender = gender;
        this.emergency = emergency;
        this.id = id;
        this.lang = lang;
    }


    /**
     * Temporary constructor to not break the tests.
     */
    public Patient(Name name, Phone phone, Email email, Address address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        this.birthday = new Birthday(LocalDateTime.now());
        this.gender = "";
        this.emergency = "";
        this.id = "";
        this.lang = "";
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getEmergency() {
        return emergency;
    }

    public String getId() {
        return id;
    }

    public String getLang() {
        return lang;
    }


    void isValidGender(String gender) {
        for (int i = 0; i < GENDERS.length; i++) {
            if (GENDERS[i].equalsIgnoreCase(gender)) {
                return;
            }
        }

        throw new IllegalArgumentException("Gender must be either: MALE, FEMALE, OTHER");
    }

    /**
     * Returns true if both patients have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Patient)) {
            return false;
        }

        if (!super.equals(other)) {
            return false;
        }

        final Patient otherPatient = (Patient) other;

        return id.equals(otherPatient.id)
                && gender.equals(otherPatient.gender)
                && birthday.equals(otherPatient.birthday);
        // Not checking for language or emergency, since they may change over time.
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, id, gender, birthday);
    }

    @Override
    public String toString() {
        return String.format("Patient[Name: " + getName() + ", Birthday: " + birthday + ", Gender: %s, ID: %s, Phone: "
                        + getPhone() + ", Email: " + getEmail() + ", Address: " + getAddress()
                        + ", Emergency: %s, Language: %s.]", gender, id, emergency, lang);
    }

}
