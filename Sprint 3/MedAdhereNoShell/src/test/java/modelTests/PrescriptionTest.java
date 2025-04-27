package modelTests;

import com.example.medadherence.models.Patient;
//import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class PrescriptionTest {
    @Test
    void testPrescriptionConstructor() {
        Prescription prescription = new Prescription();
        prescription.setMedName("Aspirin");
        assertEquals("Aspirin", prescription.getMedName());
        prescription.setDose(100);
        assertEquals(100, prescription.getDose());
        prescription.setInventory(10);
        assertEquals(10, prescription.getInventory());
        prescription.setPharmacyId("123");
        prescription.setSchedule("daily");
        assertEquals("daily", prescription.getSchedule());
    }

    @Test
    void testMissingRequiredFields() {
        Patient patientId = new Patient();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Prescription(null, 200F, 1, "123", "456", "daily", patientId);
        });
        assertEquals("Medication name cannot be null", exception.getMessage());
    }

     @Test
    void testRelationshipWithPatient() {
        Patient patient = new Patient("J Doe","jdoe@example.com", "jdoe");
        Prescription prescription = new Prescription("Ibuprofen", 200F, 1, "123", "456", "daily", patient);
        prescription.setPatient(patient);
        assertEquals(patient, prescription.getPatient());
    }

}