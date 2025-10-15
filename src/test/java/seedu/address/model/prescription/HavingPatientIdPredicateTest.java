package seedu.address.model.prescription;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HavingPatientIdPredicateTest {
    @Test
    public void equals() {
        String firstPatientId = "first";
        String secondPatientId = "first";

        HavingPatientIdPredicate firstPredicate = new HavingPatientIdPredicate(firstPatientId);
        HavingPatientIdPredicate secondPredicate = new HavingPatientIdPredicate(secondPatientId);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> return true
        HavingPatientIdPredicate firstPredicateCopy = new HavingPatientIdPredicate(firstPatientId);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));


    }
}
