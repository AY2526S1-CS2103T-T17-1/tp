package seedu.address.model.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.prescription.exceptions.DuplicatePrescriptionException;
import seedu.address.model.prescription.exceptions.PrescriptionNotFoundException;

/**
 * A list of prescriptions that enforces uniqueness.
 * Prescription identity is defined by equality.
 */
public class UniquePrescriptionList implements Iterable<Prescription> {

    private final ObservableList<Prescription> internalList = FXCollections.observableArrayList();
    private final ObservableList<Prescription> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    public UniquePrescriptionList() {

    }

    /**
     * Returns true if the list contains an equivalent prescription as the given argument.
     */
    public boolean contains(Prescription toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a prescription to the list.
     * The prescription must not already exist in the list.
     */
    public void add(Prescription toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePrescriptionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the prescription {@code target} in the list with {@code editedPrescription}.
     * {@code target} must exist in the list.
     * The prescription identity of {@code editedPrescription} must not be the same as another
     * existing prescription in the list.
     */
    public void setPrescription(Prescription target, Prescription editedPrescription) {
        requireAllNonNull(target, editedPrescription);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PrescriptionNotFoundException();
        }

        if (!target.equals(editedPrescription) && contains(editedPrescription)) {
            throw new DuplicatePrescriptionException();
        }

        internalList.set(index, editedPrescription);
    }

    /**
     * Removes the equivalent prescription from the list.
     * The prescription must exist in the list.
     */
    public void remove(Prescription toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PrescriptionNotFoundException();
        }
    }

    /**
     * Replaces list of prescriptions {@code internalList}
     * with another UniquePrescriptionList {@code replacement}
     */
    public void setPrescriptions(UniquePrescriptionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code prescriptions}.
     * {@code prescriptions} must not contain duplicate prescriptions.
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        requireAllNonNull(prescriptions);
        if (!prescriptionsAreUnique(prescriptions)) {
            throw new DuplicatePrescriptionException();
        }

        internalList.setAll(prescriptions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Prescription> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if {@code prescriptions} contains only unique prescriptions.
     */

    public boolean prescriptionsAreUnique(List<Prescription> prescriptions) {
        for (int i = 0; i < prescriptions.size() - 1; i++) {
            for (int j = i + 1; j < prescriptions.size(); j++) {
                if (prescriptions.get(i).equals(prescriptions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<Prescription> iterator() {
        return internalList.iterator();
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePrescriptionList)) {
            return false;
        }

        UniquePrescriptionList otherUniquePrescriptionList = (UniquePrescriptionList) other;
        return internalList.equals(otherUniquePrescriptionList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }





}
