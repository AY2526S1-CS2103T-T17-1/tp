package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameMatchesPredicate;
import seedu.address.model.person.Patient;
import seedu.address.model.prescription.HavingPatientIdPredicate;
import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.TypicalPatients;
import seedu.address.testutil.TypicalPrescriptions;

/**
 * Unit tests for {@link ViewPrescriptionsCommand}.
 */
public class ViewPrescriptionsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // Create empty address book
        AddressBook addressBook = new AddressBook();

        // Add all typical patients
        AddressBook patientAddressBook = TypicalPatients.getTypicalAddressBook();
        for (Patient patient : patientAddressBook.getPatientList()) {
            addressBook.addPatient(patient);
        }

        // Add all typical prescriptions
        AddressBook prescriptionAddressBook = TypicalPrescriptions.getTypicalAddressBook();
        for (Prescription prescription : prescriptionAddressBook.getPrescriptionList()) {
            addressBook.addPrescription(prescription);
        }

        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());
    }
    @Test
    public void execute_validPatientId_displaysFilteredPrescriptions() throws CommandException {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("Benson Meier");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("Benson Meier");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);

        expectedModel.updateFilteredPrescriptionList(predicate);

        CommandResult result = command.execute(model);

        assertEquals(
                String.format(Messages.MESSAGE_PRESCRIPTION_LISTED_OVERVIEW,
                        expectedModel.getFilteredPrescriptionList().size()),
                result.getFeedbackToUser());

        assertEquals(expectedModel.getFilteredPrescriptionList(), model.getFilteredPrescriptionList());
    }

    @Test
    public void execute_noMatchingPatientId_showsEmptyList() throws CommandException {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("Alice Pauline");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("Alice Pauline");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);

        expectedModel.updateFilteredPrescriptionList(predicate);
        CommandResult result = command.execute(model);

        assertEquals(
                String.format(Messages.MESSAGE_PRESCRIPTION_LISTED_OVERVIEW, 0),
                result.getFeedbackToUser());
        assertTrue(model.getFilteredPrescriptionList().isEmpty());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("Charlotte Oliveiro");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("Charlotte Oliveiro");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentObjectSamePredicate_returnsTrue() {
        HavingPatientIdPredicate predicate1 = new HavingPatientIdPredicate("David Li");
        HavingPatientIdPredicate predicate2 = new HavingPatientIdPredicate("David Li");
        NameMatchesPredicate predicate3 = new NameMatchesPredicate("David Li");
        NameMatchesPredicate predicate4 = new NameMatchesPredicate("David Li");
        ViewPrescriptionsCommand command1 = new ViewPrescriptionsCommand(predicate1, predicate3);
        ViewPrescriptionsCommand command2 = new ViewPrescriptionsCommand(predicate2, predicate4);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        HavingPatientIdPredicate predicate1 = new HavingPatientIdPredicate("Irfan Ibrahim");
        HavingPatientIdPredicate predicate2 = new HavingPatientIdPredicate("Roy Balakrishnan");
        NameMatchesPredicate predicate3 = new NameMatchesPredicate("Irfan Ibrahim");
        NameMatchesPredicate predicate4 = new NameMatchesPredicate("Roy Balakrishnan");
        ViewPrescriptionsCommand command1 = new ViewPrescriptionsCommand(predicate1, predicate3);
        ViewPrescriptionsCommand command2 = new ViewPrescriptionsCommand(predicate2, predicate4);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_null_returnsFalse() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("John Tan");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("John Tan");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("Sarah Lim");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("Sarah Lim");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);
        assertFalse(command.equals(42));
    }

    @Test
    public void toString_containsClassNameAndPredicate() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("Alex Yeoh");
        NameMatchesPredicate predicate2 = new NameMatchesPredicate("Alex Yeoh");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate, predicate2);
        String result = command.toString();

        assertTrue(result.contains("ViewPrescriptionsCommand"));
        assertTrue(result.contains("predicate=" + predicate));
    }

}
