package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;

/**
 * Contains unit tests for {@code AddPatientInfoCommand}.
 */
public class AddPatientInfoCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPatientInfoCommand(null));
    }

    @Test
    public void execute_validPatientAllFields_success() throws Exception {
        Model model = new ModelManager();
        Patient validPatient = new Patient(
                new Name("John Doe"),
                new Birthday(LocalDateTime.of(1990, 1, 1, 0, 0)),
                "M",
                new Phone("91234567"),
                new Email("john@example.com"),
                new Address("123 Main Street"),
                "81112222",
                "S1234567A",
                "English"
        );

        AddPatientInfoCommand addCommand = new AddPatientInfoCommand(validPatient);

        CommandResult result = addCommand.execute(model);

        assertEquals(String.format(AddPatientInfoCommand.MESSAGE_SUCCESS, validPatient),
                result.getFeedbackToUser());
        assertTrue(model.hasPatient(validPatient));
    }

    @Test
    public void execute_validPatientWithNullOptionalFields_success() throws Exception {
        Model model = new ModelManager();

        Patient validPatient = new Patient(
                new Name("Mary Tan"),
                new Birthday(LocalDateTime.of(1985, 5, 15, 0, 0)),
                "F",
                new Phone("98765432"),
                null,
                null,
                "82223333",
                "T9876543B",
                "Chinese"
        );

        AddPatientInfoCommand addCommand = new AddPatientInfoCommand(validPatient);

        CommandResult result = addCommand.execute(model);

        assertEquals(String.format(AddPatientInfoCommand.MESSAGE_SUCCESS, validPatient),
                result.getFeedbackToUser());
        assertTrue(model.hasPatient(validPatient));
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Model model = new ModelManager();
        Patient duplicatePatient = new Patient(
                new Name("John Doe"),
                new Birthday(LocalDateTime.of(1990, 1, 1, 0, 0)),
                "M",
                new Phone("91234567"),
                new Email("john@example.com"),
                new Address("123 Main Street"),
                "81112222",
                "S1234567A",
                "English"
        );
        model.addPatient(duplicatePatient);

        AddPatientInfoCommand addCommand = new AddPatientInfoCommand(duplicatePatient);

        assertThrows(CommandException.class,
                AddPatientInfoCommand.MESSAGE_DUPLICATE_PATIENT, () -> addCommand.execute(model));
    }

    @Test
    public void execute_patientWithEmptyStringFields_throwsCommandException() throws Exception {
        Model model = new ModelManager();

        Patient patientWithEmptyStrings = new Patient(
                new Name("Tom Lee"),
                new Birthday(LocalDateTime.of(1975, 12, 31, 0, 0)),
                "", // empty gender, won't be accepted
                new Phone("81234567"),
                new Email("john@example.com"),
                new Address("123 Main Street"),
                "81112222",
                "S1234567A",
                "English"
        );

        assertThrows(IllegalArgumentException.class,
                "Gender cannot be empty", () -> new AddPatientInfoCommand(patientWithEmptyStrings));

        assertFalse(model.hasPatient(patientWithEmptyStrings));
    }

    @Test
    public void execute_patientWithDifferentGender_success() throws Exception {
        Model model = new ModelManager();

        Patient patientMale = new Patient(
                new Name("John Male"),
                new Birthday(LocalDateTime.of(1990, 1, 1, 0, 0)),
                "Male",
                new Phone("91234561"),
                new Email("male@example.com"),
                new Address("Address 1"),
                "81112223",
                "S1234561A",
                "English"
        );

        Patient patientFemale = new Patient(
                new Name("Jane Female"),
                new Birthday(LocalDateTime.of(1991, 2, 2, 0, 0)),
                "F",
                new Phone("91234562"),
                new Email("female@example.com"),
                new Address("Address 2"),
                "81112224",
                "S1234562B",
                "Chinese"
        );

        Patient patientOther = new Patient(
                new Name("Alex Other"),
                new Birthday(LocalDateTime.of(1992, 3, 3, 0, 0)),
                "Other",
                new Phone("91234563"),
                new Email("other@example.com"),
                new Address("Address 3"),
                "81112225",
                "S1234563C",
                "Malay"
        );

        AddPatientInfoCommand addCommand1 = new AddPatientInfoCommand(patientMale);
        AddPatientInfoCommand addCommand2 = new AddPatientInfoCommand(patientFemale);
        AddPatientInfoCommand addCommand3 = new AddPatientInfoCommand(patientOther);

        addCommand1.execute(model);
        addCommand2.execute(model);
        addCommand3.execute(model);

        assertTrue(model.hasPatient(patientMale));
        assertTrue(model.hasPatient(patientFemale));
        assertTrue(model.hasPatient(patientOther));
    }

    @Test
    public void execute_patientWithComplexAddress_success() throws Exception {
        Model model = new ModelManager();
        Patient patientWithComplexAddress = new Patient(
                new Name("Complex Address Patient"),
                new Birthday(LocalDateTime.of(1980, 6, 15, 0, 0)),
                "F",
                new Phone("91234599"),
                new Email("complex@example.com"),
                new Address("Block 123, Unit 04-56, Street Name, Singapore 123456"), // complex address
                "81119999",
                "S1234599Z",
                "Tamil"
        );

        AddPatientInfoCommand addCommand = new AddPatientInfoCommand(patientWithComplexAddress);
        CommandResult result = addCommand.execute(model);

        assertEquals(String.format(AddPatientInfoCommand.MESSAGE_SUCCESS, patientWithComplexAddress),
                result.getFeedbackToUser());
        assertTrue(model.hasPatient(patientWithComplexAddress));
    }

    @Test
    public void equals() {
        Patient patientA = new Patient(
                new Name("John Doe"),
                new Birthday(LocalDateTime.of(1990, 1, 1, 0, 0)),
                "M",
                new Phone("91234567"),
                new Email("john@example.com"),
                new Address("123 Main Street"),
                "81112222",
                "S1234567A",
                "English"
        );

        Patient patientB = new Patient(
                new Name("Mary Tan"),
                new Birthday(LocalDateTime.of(1985, 5, 15, 0, 0)),
                "F",
                new Phone("98765432"),
                new Email("mary@example.com"),
                new Address("456 Side Street"),
                "82223333",
                "T9876543B",
                "Chinese"
        );

        AddPatientInfoCommand addPatientACommand = new AddPatientInfoCommand(patientA);
        AddPatientInfoCommand addPatientACommandCopy = new AddPatientInfoCommand(patientA);
        AddPatientInfoCommand addPatientBCommand = new AddPatientInfoCommand(patientB);

        assertEquals(addPatientACommand, addPatientACommandCopy);

        assertNotEquals(addPatientACommand, addPatientBCommand);
    }

    @Test
    public void toStringMethod() {
        Patient patient = new Patient(
                new Name("John Doe"),
                new Birthday(LocalDateTime.of(1990, 1, 1, 0, 0)),
                "M",
                new Phone("91234567"),
                new Email("john@example.com"),
                new Address("123 Main Street"),
                "81112222",
                "S1234567A",
                "English"
        );
        AddPatientInfoCommand addCommand = new AddPatientInfoCommand(patient);
        String expected = AddPatientInfoCommand.class.getCanonicalName() + "{toAdd=" + patient + "}";
        assertEquals(expected, addCommand.toString());
    }
}
