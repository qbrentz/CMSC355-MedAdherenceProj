package modelTests;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Prescription;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class MedicationLogTest {
    @Test
    void testMedicationLogConstructor() {
        LocalDateTime now = LocalDateTime.now();
        MedicationLog log = new MedicationLog();
        log.setTimestamp(now);
        assertEquals(now, log.getTimestamp());
    }

    @Test
    void testRelationshipWithPrescription() {
        Prescription prescription = new Prescription("Ibuprofen", 0, 0, "200mg", null, null, null);
        MedicationLog log = new MedicationLog();
        log.setPrescription(prescription);
        assertEquals(prescription, log.getPrescription());
    }
}