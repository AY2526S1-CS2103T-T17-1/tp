package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.prescription.Prescription;

/**
 * Unmodifiable view of an address book.
 */
public interface ReadOnlyAddressBook {
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the appointment list.
     */
    ObservableList<Appointment> getAppointmentList();

    /**
     * Returns an unmodifiable view of the prescription list.
     */
    ObservableList<Prescription> getPrescriptionList();
}
