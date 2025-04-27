package controlTests;

import com.example.medadherence.models.Patient;
import com.example.medadherence.services.PatientService;
import com.example.medadherence.controllers.PatientController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void getAllPatients_ReturnsPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient("John Doe", "john@example.com", "jdoe69"));
        when(patientService.getAllPatients()).thenReturn(patients);

        // Act
        List<Patient> result = patientController.getAllPatients();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void addPatient_ReturnsCreatedPatient() {
        // Arrange
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.addPatient(patient);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void getPatientById_ReturnsPatientIfExists() {
        // Arrange
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        when(patientService.getPatientById(1L)).thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void getPatientById_ReturnsNotFoundIfPatientDoesNotExist() {
        // Arrange
        when(patientService.getPatientById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deletePatient_ReturnsSuccessMessageIfPatientExists() {
        // Arrange
        when(patientService.deletePatient(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = patientController.deletePatient(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Patient deleted successfully", response.getBody());
    }

    @Test
    void deletePatient_ReturnsNotFoundIfPatientDoesNotExist() {
        // Arrange
        when(patientService.deletePatient(1L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = patientController.deletePatient(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }
}