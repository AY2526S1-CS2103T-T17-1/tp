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

        assertTrue(firstPredicate.equals(firstPredicate));

        HavingPatientIdPredicate firstPredicateCopy = new HavingPatientIdPredicate(firstPatientId);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertTrue(firstPredicate.equals(secondPredicate));

        assertFalse(firstPredicate.equals(1));

        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_nullPrescription_returnsFalse() {
        HavingPatientIdPredicate predicate = new HavingPatientIdPredicate("test");
        assertFalse(predicate.test(null));
    }
}
