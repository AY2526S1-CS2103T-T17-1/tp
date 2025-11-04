package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Patient;

/**
 * Adds an appointment to the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "a-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment. \n"
            + "Parameters: "
            + "n/PATIENT_NAME "
            + "d/DOCTOR_NAME "
            + "t/DATE_TIME(yyyy-MM-dd HH:mm) "
            + "[note/NOTE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "n/John Doe d/Dr Wee t/2025-11-11 14:00 note/Follow-up";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient '%1$s' does not exist in the address book.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists.";

    private final Appointment toAdd;
    private final String patientName;

    /**
     * Constructs an {@code AddAppointmentCommand} with the specified patient name and appointment details.
     *
     * @param patientName The name of the patient to whom the appointment will be added. Must not be {@code null}.
     * @param appointment The {@code Appointment} object containing the appointment details. Must not be {@code null}.
     */
    public AddAppointmentCommand(String patientName, Appointment appointment) {
        requireNonNull(patientName);
        requireNonNull(appointment);
        this.patientName = patientName;
        this.toAdd = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Patient> matchedPatient = model.getAddressBook()
                .getPatientList()
                .stream()
                .filter(p -> normalizeName(p.getName().fullName)
                        .equals(normalizeName(patientName)))
                .findFirst();

        if (matchedPatient.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PATIENT_NOT_FOUND, patientName));
        }

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private static String normalizeName(String name) {
        return name.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && toAdd.equals(((AddAppointmentCommand) other).toAdd)
                && patientName.equals(((AddAppointmentCommand) other).patientName));
    }
}
