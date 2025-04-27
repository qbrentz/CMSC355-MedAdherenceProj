package controlTests;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.services.PatientService;
import com.example.medadherence.services.MedicationLogService;
import com.example.medadherence.services.PrescriptionService;
import com.example.medadherence.controllers.MedicationLogController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MedicationLogControllerTest {

    @Mock
    private MedicationLogService medicationLogService;

    @InjectMocks
    private MedicationLogController medicationLogController;

    @Mock
    private PrescriptionService prescriptionService;

    @Mock
    private PatientService patientService;

    @Test
    void getAllLogs_ReturnsList() {
        // Arrange
        List<MedicationLog> logs = Arrays.asList(new MedicationLog());
        when(medicationLogService.getAllLogs()).thenReturn(logs);

        // Act
        List<MedicationLog> result = medicationLogController.getAllLogs();

        // Assert
        assertEquals(1, result.size());
    }


 
   /* @Test
    void addLog_ReturnsCreatedLog() {
        // Arrange
        
        LocalDateTime timeStamp = LocalDateTime.now();
        Long patientID = 123L;
        Long prescriptionID = 123L;
        Patient patient = new Patient("john","jdoe@test.com","jdoe");
        patient.setId(patientID);
        Prescription prescription = new Prescription("test",1123, 123, "test","testMed","testSched", patient);
        prescription.setId(prescriptionID);

        MedicationLog log = new MedicationLog(timeStamp, prescriptionID, patientID);
        log.setPatient(patient);
        log.setPrescription(prescription);
        when(medicationLogService.addLog(any(MedicationLog.class))).thenReturn(log);

        // Act
        ResponseEntity<MedicationLog> response = medicationLogController.addMedicationLog(log);

        // Assert
        assertNotNull(response.getBody());
    }
*/


    @Test
    void getLogById_ReturnsLogIfExists() {
        // Arrange
        MedicationLog log = new MedicationLog();
        log.setId(1L);
        when(medicationLogService.getLogById(1L)).thenReturn(log);

        // Act
        ResponseEntity<MedicationLog> response = medicationLogController.getLogById(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getLogById_ReturnsNotFoundIfLogDoesNotExist() {
        // Arrange
        when(medicationLogService.getLogById(1L)).thenReturn(null);

        // Act
        ResponseEntity<MedicationLog> response = medicationLogController.getLogById(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteLog_ReturnsSuccessMessageIfLogExists() {
        // Arrange
        when(medicationLogService.deleteLog(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = medicationLogController.deleteLog(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Medication log deleted successfully", response.getBody());
    }

    @Test
    void deleteLog_ReturnsNotFoundIfLogDoesNotExist() {
        // Arrange
        when(medicationLogService.deleteLog(1L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = medicationLogController.deleteLog(1L);

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }
}
