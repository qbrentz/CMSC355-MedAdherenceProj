package serviceTests;

//import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.repositories.PrescriptionRepository;
import com.example.medadherence.services.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Test
    void getAllPrescriptions_ReturnsList() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setMedName("TestMed");
        when(prescriptionRepository.findAll()).thenReturn(Arrays.asList(prescription));

        // Act
        List<Prescription> result = prescriptionService.getAllPrescriptions();

        // Assert
        assertEquals(1, result.size());
        assertEquals("TestMed", result.get(0).getMedName());
    }

    @Test
    void addPrescription_SavesAndReturnsPrescription() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setMedName("Ibuprofen");
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);

        // Act
        Prescription result = prescriptionService.addPrescription(prescription);

        // Assert
        assertEquals("Ibuprofen", result.getMedName());
        verify(prescriptionRepository, times(1)).save(prescription);
    }

    @Test
    void getPrescriptionById_ReturnsPrescriptionIfExists() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedName("Paracetamol");
        when(prescriptionRepository.findById(1L)).thenReturn(Optional.of(prescription));

        // Act
        Prescription result = prescriptionService.getPrescriptionById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Paracetamol", result.getMedName());
    }

    @Test
    void getPrescriptionById_ReturnsNullIfNotFound() {
        // Arrange
        when(prescriptionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Prescription result = prescriptionService.getPrescriptionById(1L);

        // Assert
        assertNull(result);
    }


    /*
    @Test
    void updatePrescription_UpdatesAndReturnsPrescriptionIfExists() {
        // Arrange
        Patient patient = new Patient("J Doe", "jdoe@example.com", "jdoe");
        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setId(1L);
        updatedPrescription.setMedName("UpdatedMed");
        updatedPrescription.setDose(500);
        updatedPrescription.setInventory(30);
        updatedPrescription.setPharmacyId("Pharmacy123");
        updatedPrescription.setMedicationId("Medication123");
        updatedPrescription.setSchedule("Twice a day");
        updatedPrescription.setPatient(patient);
        when(prescriptionRepository.existsById(1L)).thenReturn(true);
        when(prescriptionRepository.save(updatedPrescription)).thenReturn(updatedPrescription);

        // Act
        Prescription result = prescriptionService.updatePrescription(1L, updatedPrescription);

        // Assert
        assertNotNull(result);
        assertEquals("UpdatedMed", result.getMedName());
    }
    */


    @Test
    void updatePrescription_ReturnsNullIfPrescriptionDoesNotExist() {
        // Arrange
        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setMedName("NonExistentMed");
        lenient().when(prescriptionRepository.existsById(1L)).thenReturn(false);

        // Act
        Prescription result = prescriptionService.updatePrescription(1L, updatedPrescription);

        // Assert
        assertNull(result);
    }

    @Test
    void deletePrescription_DeletesPrescriptionIfExists() {
        // Arrange
        when(prescriptionRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = prescriptionService.deletePrescription(1L);

        // Assert
        assertTrue(result);
        verify(prescriptionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePrescription_ReturnsFalseIfPrescriptionDoesNotExist() {
        // Arrange
        when(prescriptionRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = prescriptionService.deletePrescription(1L);

        // Assert
        assertFalse(result);
        verify(prescriptionRepository, never()).deleteById(anyLong());
    }
}
