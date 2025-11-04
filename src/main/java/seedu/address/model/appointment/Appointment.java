package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an Appointment in the hospital system.
 * Guarantees: all fields are non-null and immutable once created.
 */
public class Appointment {

    private final String patientName;
    private final LocalDateTime dateTime;
    private final String doctor;
    private final String reason;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientName The patient's unique ID or Name.
     * @param dateTime  The date and time of the appointment.
     * @param doctor    The doctor's name.
     * @param reason    The reason or notes for the appointment.
     * @throws IllegalArgumentException if any string field is empty.
     */
    public Appointment(String patientName, LocalDateTime dateTime, String doctor, String reason) {
        requireNonNull(patientName);
        requireNonNull(dateTime);
        requireNonNull(doctor);
        requireNonNull(reason);

        if (patientName.isBlank() || doctor.isBlank()) {
            throw new IllegalArgumentException("Patient Name and Doctor cannot be blank.");
        }

        this.patientName = patientName.trim();
        this.dateTime = dateTime;
        this.doctor = doctor.trim();
        this.reason = reason.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        final Appointment otherAppointment = (Appointment) other;
        return normalizeName(patientName).equals(normalizeName(otherAppointment.patientName))
            && doctor.equalsIgnoreCase(otherAppointment.doctor)
            && dateTime.equals(otherAppointment.dateTime);
    }

    private static String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, dateTime, doctor, reason);
    }

    @Override
    public String toString() {
        return String.format("Appointment[patient='%s', dateTime=%s, doctor='%s', reason='%s']",
                patientName, dateTime, doctor, reason);
    }
}
