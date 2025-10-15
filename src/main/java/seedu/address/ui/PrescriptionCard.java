package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.prescription.Prescription;

/**
 * An UI component that displays information of a {@code Prescription}.
 */
public class PrescriptionCard extends UiPart<Region> {

    private static final String FXML = "PrescriptionListCard.fxml";

    public final Prescription prescription;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label medicationName;
    @FXML
    private Label dosage;
    @FXML
    private Label frequency;
    @FXML
    private Label duration;
    @FXML
    private Label startDate;
    @FXML
    private Label note;
    @FXML
    private Label patientId;

    /**
     * Creates a {@code PrescriptionCard} with the given {@code Prescription} and index to display.
     */
    public PrescriptionCard(Prescription prescription, int displayedIndex) {
        super(FXML);
        this.prescription = prescription;

        id.setText(displayedIndex + ". ");
        medicationName.setText(prescription.getMedicationName());
        dosage.setText(String.format("%.1f mg", prescription.getDosage()));
        frequency.setText(prescription.getFrequency() + "×/day");
        duration.setText(prescription.getDuration() + " days");
        startDate.setText(prescription.getStartDate().toLocalDate().toString());
        note.setText(prescription.getNote() == null || prescription.getNote().isBlank()
            ? "(no additional notes)"
            : prescription.getNote());
        patientId.setText("Patient ID: " + prescription.getPatientId());
    }
}
