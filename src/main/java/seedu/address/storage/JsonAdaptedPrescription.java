package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.prescription.Prescription;

/**
 * TODO: add javadoc
 */
public class JsonAdaptedPrescription {
    private final String patientId;
    private final String medicationName;
    private final Float dosage;
    private final Integer frequency;
    private final LocalDateTime startDate;
    private final Integer duration;
    private final String note;

    /**
     * TODO: add javadoc
     * @param patientId
     * @param medicationName
     * @param dosage
     * @param frequency
     * @param startDate
     * @param duration
     * @param note
     */
    @JsonCreator
    public JsonAdaptedPrescription(@JsonProperty("patientId") String patientId,
                                   @JsonProperty("medicationName") String medicationName,
                                   @JsonProperty("dosage") Float dosage,
                                   @JsonProperty("frequency") Integer frequency,
                                   @JsonProperty("startDate") LocalDateTime startDate,
                                   @JsonProperty("duration") Integer duration,
                                   @JsonProperty("note") String note) {
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.duration = duration;
        this.note = note;
    }

    /**
     * TODO: add javadoc
     * @param p
     */
    public JsonAdaptedPrescription(Prescription p) {
        this.patientId = p.getPatientId();
        this.medicationName = p.getMedicationName();
        this.dosage = p.getDosage();
        this.frequency = p.getFrequency();
        this.startDate = p.getStartDate();
        this.duration = p.getDuration();
        this.note = p.getNote();
    }

    /**
     * TODO: add javadoc
     * @return
     */
    public Prescription toModelType() {
        return new Prescription(patientId, medicationName, dosage, frequency, startDate, duration, note);
    }
}
