package seedu.address.model.prescription.exceptions;

/**
 * Signals that the operation will result in duplicate Prescriptions.
 */
public class DuplicatePrescriptionException extends RuntimeException {
    public DuplicatePrescriptionException() {
        super("Operation would result in duplicate appointments");
    }
}
