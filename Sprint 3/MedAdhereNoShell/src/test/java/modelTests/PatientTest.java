package modelTests;

import com.example.medadherence.models.Patient;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class PatientTest {
    @Test
    void testPatientConstructor() {
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        assertEquals("John Doe", patient.getName());
        assertEquals("john@example.com", patient.getEmail());
        patient.setId(123L);
        assertEquals(123, patient.getId());
        }

        @Test
    void testDefaultCreateTime() {
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        assertNotNull(patient.getCreateTime());
        //Requires slight modification to the test to account 
        //for the fact that the create time is set to the current time
        assertTrue(patient.getCreateTime().isBefore(LocalDateTime.now().plusSeconds(1L)));
    }

    @Test
    void testMissingRequiredFields() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Patient(null, "john@example.com", "jdoe69");
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }
    @Test
    void testMissingEmail() {
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Patient("John Doe", null, "jdoe69");
        });
        assertEquals("Email cannot be null", exception2.getMessage());
    }

    @Test
    void testMissingUserName(){
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->{
            new Patient("John Doe", "jdoe@example.com", null);
        });
        assertEquals("UserName cannot be null", exception3.getMessage());
    }

    @Test
    void testDuplicateEmail() {
        Patient patient1 = new Patient("John Doe", "john@example.com", "jdoe69");
        Patient patient2 = new Patient("Jane Doe", "john@example.com", "jdoe70");
        assertNotEquals(patient1, patient2); // Ensure they are not the same object
    }

    @Test
    void testDuplicateUserName(){
        Patient patient1 = new Patient("doug funny", "dfunny@example.com", "dfunny69");
        Patient patient2 = new Patient("jeff jeff", "doublejeff@example.com", "dfunny69");
        assertNotEquals(patient1, patient2); // Ensure they are not the same object
    }
}