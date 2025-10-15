package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPrescriptions.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.prescription.HavingPatientIdPredicate;

/**
 * Unit tests for {@link ViewPrescriptionsCommand}.
 */
public class ViewPrescriptionsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validPatientId_displaysFilteredPrescriptions() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);

        expectedModel.updateFilteredPrescriptionList(predicate);

        CommandResult result = command.execute(model);

        assertEquals(
            String.format(Messages.MESSAGE_PRESCRIPTION_LISTED_OVERVIEW,
                expectedModel.getFilteredPrescriptionList().size()),
            result.getFeedbackToUser());

        assertEquals(expectedModel.getFilteredPrescriptionList(), model.getFilteredPrescriptionList());
    }

    @Test
    public void execute_noMatchingPatientId_showsEmptyList() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-99999");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);

        expectedModel.updateFilteredPrescriptionList(predicate);
        CommandResult result = command.execute(model);

        assertEquals(
            String.format(Messages.MESSAGE_PRESCRIPTION_LISTED_OVERVIEW, 0),
            result.getFeedbackToUser());
        assertTrue(model.getFilteredPrescriptionList().isEmpty());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentObjectSamePredicate_returnsTrue() {
        HavingPatientIdPredicate predicate1 = new HavingPatientIdPredicate("P-10293");
        HavingPatientIdPredicate predicate2 = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command1 = new ViewPrescriptionsCommand(predicate1);
        ViewPrescriptionsCommand command2 = new ViewPrescriptionsCommand(predicate2);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        HavingPatientIdPredicate predicate1 = new HavingPatientIdPredicate("P-10293");
        HavingPatientIdPredicate predicate2 = new HavingPatientIdPredicate("P-20485");
        ViewPrescriptionsCommand command1 = new ViewPrescriptionsCommand(predicate1);
        ViewPrescriptionsCommand command2 = new ViewPrescriptionsCommand(predicate2);
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_null_returnsFalse() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);
        assertFalse(command.equals(42));
    }

    @Test
    public void toString_containsClassNameAndPredicate() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("P-10293");
        ViewPrescriptionsCommand command = new ViewPrescriptionsCommand(predicate);
        String result = command.toString();

        assertTrue(result.contains("ViewPrescriptionsCommand"));
        assertTrue(result.contains("predicate=" + predicate));
    }

}
