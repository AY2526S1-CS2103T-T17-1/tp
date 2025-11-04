package seedu.address.model.prescription;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Prescription}'s patientId matches the give patientId.
 */
public class HavingPatientIdPredicate implements Predicate<Prescription> {
    private final String patientId;

    public HavingPatientIdPredicate(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean test(Prescription prescription) {
        if (prescription == null) {
            return false;
        }

        String prescriptionPatientId = prescription.getPatientId();

        return prescriptionPatientId.toLowerCase().contains(patientId.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HavingPatientIdPredicate)) {
            return false;
        }

        HavingPatientIdPredicate otherHavingPatientIdPredicate = (HavingPatientIdPredicate) other;
        return patientId.equals(otherHavingPatientIdPredicate.patientId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("patientId", patientId).toString();
    }
}
