package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.prescription.Prescription;

/**
 * A utility class to help with building {@code Prescription} objects for testing.
 */
public class PrescriptionBuilder {

    public static final String DEFAULT_PATIENT_ID = "P-10293";
    public static final String DEFAULT_MEDICATION_NAME = "Paracetamol";
    public static final Float DEFAULT_DOSAGE = 500f;
    public static final Integer DEFAULT_FREQUENCY = 3;
    public static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2025, 1, 1, 9, 0);
    public static final Integer DEFAULT_DURATION = 7;
    public static final String DEFAULT_NOTE = "Take after meals";

    private String patientId;
    private String medicationName;
    private Float dosage;
    private Integer frequency;
    private LocalDateTime startDate;
    private Integer duration;
    private String note;

    /**
     * Creates a {@code PrescriptionBuilder} with default details.
     */
    public PrescriptionBuilder() {
        patientId = DEFAULT_PATIENT_ID;
        medicationName = DEFAULT_MEDICATION_NAME;
        dosage = DEFAULT_DOSAGE;
        frequency = DEFAULT_FREQUENCY;
        startDate = DEFAULT_START_DATE;
        duration = DEFAULT_DURATION;
        note = DEFAULT_NOTE;
    }

    /**
     * Initializes the builder with the data of {@code prescriptionToCopy}.
     *
     * @param prescriptionToCopy the prescription whose details are to be copied.
     */
    public PrescriptionBuilder(Prescription prescriptionToCopy) {
        patientId = prescriptionToCopy.getPatientId();
        medicationName = prescriptionToCopy.getMedicationName();
        dosage = prescriptionToCopy.getDosage();
        frequency = prescriptionToCopy.getFrequency();
        startDate = prescriptionToCopy.getStartDate();
        duration = prescriptionToCopy.getDuration();
        note = prescriptionToCopy.getNote();
    }

    /**
     * Sets the {@code patientId} of the {@code Prescription} that we are building.
     *
     * @param patientId the patient ID.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    /**
     * Sets the {@code medicationName} of the {@code Prescription} that we are building.
     *
     * @param medicationName the medication name.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withMedicationName(String medicationName) {
        this.medicationName = medicationName;
        return this;
    }

    /**
     * Sets the {@code dosage} of the {@code Prescription} that we are building.
     *
     * @param dosage the dosage in milligrams.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withDosage(float dosage) {
        this.dosage = dosage;
        return this;
    }

    /**
     * Sets the {@code frequency} of the {@code Prescription} that we are building.
     *
     * @param frequency the number of times the medication should be taken per day.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withFrequency(int frequency) {
        this.frequency = frequency;
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Prescription} that we are building.
     *
     * @param startDate the start date and time for the prescription.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code Prescription} that we are building.
     *
     * @param duration the number of days the medication should be taken.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the {@code note} of the {@code Prescription} that we are building.
     *
     * @param note any additional information about the prescription.
     * @return this builder for chaining.
     */
    public PrescriptionBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    /**
     * Builds and returns a {@code Prescription} with the specified details.
     *
     * @return a new {@code Prescription} object.
     */
    public Prescription build() {
        return new Prescription(patientId, medicationName, dosage, frequency, startDate, duration, note);
    }
}
