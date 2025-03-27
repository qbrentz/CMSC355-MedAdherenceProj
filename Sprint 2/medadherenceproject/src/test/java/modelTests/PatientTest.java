package modelTests;

import com.example.medadherence.models.Patient;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class PatientTest {
    @Test
    void testPatientConstructor() {
        Patient patient = new Patient("John Doe", "john@example.com");
        assertEquals("John Doe", patient.getName());
        assertEquals("john@example.com", patient.getEmail());
        patient.setId(123L);
        assertEquals(123, patient.getId());
        }

        @Test
    void testDefaultCreateTime() {
        Patient patient = new Patient("John Doe", "john@example.com");
        assertNotNull(patient.getCreateTime());
        //Requires slight modification to the test to account 
        //for the fact that the create time is set to the current time
        assertTrue(patient.getCreateTime().isBefore(LocalDateTime.now().plusSeconds(1L)));
    }

    @Test
    void testMissingRequiredFields() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Patient(null, "john@example.com");
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void testDuplicateEmail() {
        Patient patient1 = new Patient("John Doe", "john@example.com");
        Patient patient2 = new Patient("Jane Doe", "john@example.com");
        assertNotEquals(patient1, patient2); // Ensure they are not the same object
    }
}