package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.prescription.Prescription;
import seedu.address.testutil.PrescriptionBuilder;

public class JsonAdaptedPrescriptionTest {

    private static final String VALID_PATIENT_ID = "P-10293";
    private static final String VALID_MEDICATION_NAME = "Paracetamol";
    private static final Float VALID_DOSAGE = 500.0f;
    private static final Integer VALID_FREQUENCY = 3;
    private static final LocalDateTime VALID_START_DATE = LocalDateTime.of(2024, 10, 10, 8, 0);
    private static final Integer VALID_DURATION = 7;
    private static final String VALID_NOTE = "Take after meals";

    @Test
    public void toModelType_validPrescription_returnsPrescription() throws Exception {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        Prescription expectedPrescription = new Prescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        assertEquals(expectedPrescription, prescription.toModelType());
    }

    @Test
    public void toModelType_nullPatientId_throwsNullPointerException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            null, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        assertThrows(NullPointerException.class, prescription::toModelType);
    }

    @Test
    public void toModelType_nullMedicationName_throwsNullPointerException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, null, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        assertThrows(NullPointerException.class, prescription::toModelType);
    }

    @Test
    public void toModelType_nullDosage_throwsNullPointerException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, null, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        assertThrows(NullPointerException.class, prescription::toModelType);
    }

    @Test
    public void toModelType_nullFrequency_throwsNullPointerException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, null,
            VALID_START_DATE, VALID_DURATION, VALID_NOTE);

        assertThrows(NullPointerException.class, prescription::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsNullPointerException() {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, null, VALID_NOTE);

        assertThrows(NullPointerException.class, prescription::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_returnsPrescriptionWithNullStartDate() throws Exception {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            null, VALID_DURATION, VALID_NOTE);

        Prescription modelPrescription = prescription.toModelType();

        assertEquals(VALID_PATIENT_ID, modelPrescription.getPatientId());
        assertEquals(VALID_MEDICATION_NAME, modelPrescription.getMedicationName());
        assertEquals(VALID_DOSAGE, modelPrescription.getDosage());
        assertEquals(VALID_FREQUENCY, modelPrescription.getFrequency());
        assertEquals(null, modelPrescription.getStartDate());
        assertEquals(VALID_DURATION, modelPrescription.getDuration());
        assertEquals(VALID_NOTE, modelPrescription.getNote());
    }

    @Test
    public void toModelType_nullNote_returnsPrescriptionWithNullNote() throws Exception {
        JsonAdaptedPrescription prescription = new JsonAdaptedPrescription(
            VALID_PATIENT_ID, VALID_MEDICATION_NAME, VALID_DOSAGE, VALID_FREQUENCY,
            VALID_START_DATE, VALID_DURATION, null);

        Prescription modelPrescription = prescription.toModelType();

        assertEquals(VALID_PATIENT_ID, modelPrescription.getPatientId());
        assertEquals(VALID_MEDICATION_NAME, modelPrescription.getMedicationName());
        assertEquals(VALID_DOSAGE, modelPrescription.getDosage());
        assertEquals(VALID_FREQUENCY, modelPrescription.getFrequency());
        assertEquals(VALID_START_DATE, modelPrescription.getStartDate());
        assertEquals(VALID_DURATION, modelPrescription.getDuration());
        assertEquals(null, modelPrescription.getNote());
    }

    @Test
    public void constructor_fromPrescription_preservesData() {
        Prescription prescription = new PrescriptionBuilder().build();
        JsonAdaptedPrescription adaptedPrescription = new JsonAdaptedPrescription(prescription);

        assertEquals(prescription.getPatientId(), adaptedPrescription.toModelType().getPatientId());
        assertEquals(prescription.getMedicationName(), adaptedPrescription.toModelType().getMedicationName());
        assertEquals(prescription.getDosage(), adaptedPrescription.toModelType().getDosage());
        assertEquals(prescription.getFrequency(), adaptedPrescription.toModelType().getFrequency());
        assertEquals(prescription.getStartDate(), adaptedPrescription.toModelType().getStartDate());
        assertEquals(prescription.getDuration(), adaptedPrescription.toModelType().getDuration());
        assertEquals(prescription.getNote(), adaptedPrescription.toModelType().getNote());
    }

}
