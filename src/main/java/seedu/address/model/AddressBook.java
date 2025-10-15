package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.person.Patient;
import seedu.address.model.person.UniquePatientList;
import seedu.address.model.prescription.Prescription;
import seedu.address.model.prescription.UniquePrescriptionList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePatient comparison for persons, and equality for appointments).
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePatientList persons;
    private final UniqueAppointmentList appointments;
    private final UniquePrescriptionList prescriptions;

    {
        persons = new UniquePatientList();
        appointments = new UniqueAppointmentList();
        prescriptions = new UniquePrescriptionList();
    }

    /**
     * Constructs an empty {@code AddressBook}.
     */
    public AddressBook() {}

    /**
     * Creates an {@code AddressBook} using the data in {@code toBeCopied}.
     *
     * @param toBeCopied the ReadOnlyAddressBook containing data to copy
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the current list of persons with the provided list.
     *
     * @param persons the new list of {@link Person} objects to set
     */
    public void setPatients(List<Patient> persons) {
        this.persons.setPatients(persons);
    }

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     *
     * @param appointments the list of appointments to set
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Replaces the contents of the prescription list with {@code prescriptions}.
     * {@code prescriptions} must not contain duplicate prescriptions.
     *
     * @param prescriptions the list of prescriptions to set
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions.setPrescriptions(prescriptions);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     *
     * @param newData the new data to replace the current data
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPatients(newData.getPatientList());
        setAppointments(newData.getAppointmentList());
        setPrescriptions(newData.getPrescriptionList());
    }

    //// person-level operations

    /**
     * Checks if the address book contains the specified person.
     *
     * @param person the person to check for
     * @return true if the address book contains the person, false otherwise
     */
    public boolean hasPatient(Patient person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     *
     * @param p the person to add
     */
    public void addPatient(Patient p) {
        persons.add(p);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     *
     * @param target the patient to be replaced
     * @param editedPatient the replacement patient
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);
        persons.setPatient(target, editedPatient);
    }

    /**
     * Removes the specified patient from the address book.
     * The person must exist in the address book.
     *
     * @param key the patient to remove
     */
    public void removePatient(Patient key) {
        persons.remove(key);
    }

    //// appointment-level operations

    /**
     * Checks if the address book contains the specified appointment.
     *
     * @param appointment the appointment to check for
     * @return true if the address book contains the appointment, false otherwise
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Adds an appointment to the address book.
     * The appointment must not already exist in the address book.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Removes the specified appointment from the address book.
     * The appointment must exist in the address book.
     *
     * @param appointment the appointment to remove
     */
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    //// prescription-level operations

    /**
     * Checks if the address book contains the specified prescription.
     *
     * @param prescription the prescription to check for
     * @return true if the address book contains the prescription, false otherwise
     */
    public boolean hasPrescription(Prescription prescription) {
        requireNonNull(prescription);
        return prescriptions.contains(prescription);
    }

    /**
     * Adds a prescription to the address book.
     * The prescription must not already exist in the address book.
     *
     * @param prescription the prescription to add
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * Replaces the given prescription {@code target} in the list with {@code editedPrescription}.
     *
     * @param target the prescription to be replaced
     * @param editedPrescription the replacement prescription
     */
    public void setPrescription(Prescription target, Prescription editedPrescription) {
        requireNonNull(editedPrescription);
        prescriptions.setPrescription(target, editedPrescription);
    }

    /**
     * Removes the specified prescription from the address book.
     * The prescription must exist in the address book.
     *
     * @param key the prescription to remove
     */
    public void removePrescription(Prescription key) {
        prescriptions.remove(key);
    }

    //// list getters

    @Override
    public ObservableList<Patient> getPatientList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Prescription> getPrescriptionList() {
        return prescriptions.asUnmodifiableObservableList();
    }

    //// other @Override methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("persons", persons)
            .add("appointments", appointments)
            .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddressBook)) {
            return false;
        }
        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
            && appointments.equals(otherAddressBook.appointments);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() ^ appointments.hashCode();
    }
}
