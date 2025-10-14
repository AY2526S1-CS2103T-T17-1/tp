package seedu.address.model.prescription.exceptions;

/**
 * TODO: javadoc
 */
public class DuplicatePrescriptionException extends RuntimeException {
    public DuplicatePrescriptionException() {
        super("Operation would result in duplicate prescriptions");
    }
}
