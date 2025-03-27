package controlTests;

import com.example.medadherence.models.Prescription;
import com.example.medadherence.services.PrescriptionService;
import com.example.medadherence.controllers.PrescriptionController;
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
public class PrescriptionControllerTest {

    @Mock
    private PrescriptionService prescriptionService;

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Test
    void getAllPrescriptions_ReturnsPrescriptions() {
        // Arrange
        List<Prescription> prescriptions = Arrays.asList(new Prescription());
        when(prescriptionService.getAllPrescriptions()).thenReturn(prescriptions);

        // Act
        List<Prescription> result = prescriptionController.getAllPrescriptions();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void addPrescription_ReturnsCreatedPrescription() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setMedName("Ibuprofen");
        when(prescriptionService.addPrescription(any(Prescription.class))).thenReturn(prescription);

        // Act
        ResponseEntity<Prescription> response = prescriptionController.addPrescription(prescription);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Ibuprofen", response.getBody().getMedName());
    }

    @Test
    void getPrescriptionById_ReturnsPrescriptionIfExists() {
        // Arrange
        Prescription prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedName("Paracetamol");
        when(prescriptionService.getPrescriptionById(1L)).thenReturn(prescription);

        // Act
        ResponseEntity<Prescription> response = prescriptionController.getPrescriptionById(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Paracetamol", response.getBody().getMedName());
    }

    @Test
    void getPrescriptionById_ReturnsNotFoundIfPrescriptionDoesNotExist() {
        // Arrange
        when(prescriptionService.getPrescriptionById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Prescription> response = prescriptionController.getPrescriptionById(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deletePrescription_ReturnsSuccessMessageIfPrescriptionExists() {
        // Arrange
        when(prescriptionService.deletePrescription(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = prescriptionController.deletePrescription(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Prescription deleted successfully", response.getBody());
    }

    @Test
    void deletePrescription_ReturnsNotFoundIfPrescriptionDoesNotExist() {
        // Arrange
        when(prescriptionService.deletePrescription(1L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = prescriptionController.deletePrescription(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }
}