package seedu.address.model.prescription;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.prescription.exceptions.DuplicatePrescriptionException;

/**
 * TODO: javadoc
 */
public class UniquePrescriptionList implements Iterable<Prescription> {

    private final ObservableList<Prescription> internalList = FXCollections.observableArrayList();
    private final ObservableList<Prescription> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * TODO: javadoc
     * @param toCheck
     * @return
     */
    public boolean contains(Prescription toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * TODO: javadoc
     * @param toAdd
     */
    public void add(Prescription toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePrescriptionException();
        }
        internalList.add(toAdd);
    }

    /**
     * TODO: javadoc
     * @param prescriptions
     */
    public void setPrescriptions(List<Prescription> prescriptions) {
        requireNonNull(prescriptions);
        if (!prescriptionsAreUnique(prescriptions)) {
            throw new DuplicatePrescriptionException();
        }
        internalList.setAll(prescriptions);
    }

    /**
     * TODO: javadoc
     * @return
     */
    public ObservableList<Prescription> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * TODO: javadoc
     * @param toRemove
     */
    public void remove(Prescription toRemove) {
        requireNonNull(toRemove);
        internalList.remove(toRemove);
    }

    @Override
    public Iterator<Prescription> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniquePrescriptionList
                && internalList.equals(((UniquePrescriptionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean prescriptionsAreUnique(List<Prescription> prescriptions) {
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
    public String toString() {
        return internalUnmodifiableList.toString();
    }
}
