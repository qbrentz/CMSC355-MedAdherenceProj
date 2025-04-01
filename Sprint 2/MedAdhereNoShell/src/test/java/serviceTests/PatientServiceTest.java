package serviceTests;

import com.example.medadherence.models.Patient;
import com.example.medadherence.repositories.PatientRepository;
import com.example.medadherence.services.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void getAllPatients_ReturnsListOfPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(new Patient("John Doe", "john@example.com", "jdoe69"));
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void addPatient_SavesAndReturnsPatient() {
        // Arrange
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient result = patientService.addPatient(patient);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getPatientById_ReturnsPatientIfExists() {
        // Arrange
        Patient patient = new Patient("John Doe", "john@example.com", "jdoe69");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getPatientById_ReturnsNullIfNotFound() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Patient result = patientService.getPatientById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void updatePatient_UpdatesAndReturnsPatientIfExists() {
        // Arrange
        Patient updatedPatient = new Patient("Jane Doe", "jane@example.com", "janed69");
        updatedPatient.setId(1L);
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Act
        Patient result = patientService.updatePatient(1L, updatedPatient);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void updatePatient_ReturnsNullIfPatientDoesNotExist() {
        // Arrange
        Patient updatedPatient = new Patient("Jane Doe", "jane@example.com", "janed69");
        
        // Act
        Patient result = patientService.updatePatient(1L, updatedPatient);

        // Assert
        assertNull(result);
    }

    @Test
    void deletePatient_DeletesPatientIfExists() {
        
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = patientService.deletePatient(1L);

        // Assert
        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePatient_ReturnsFalseIfPatientDoesNotExist() {
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = patientService.deletePatient(1L);

        // Assert
        assertFalse(result);
        verify(patientRepository, never()).deleteById(anyLong());
    }
}