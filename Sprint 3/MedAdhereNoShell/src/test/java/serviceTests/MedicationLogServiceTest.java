package serviceTests;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.repositories.MedicationLogRepository;
import com.example.medadherence.services.MedicationLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicationLogServiceTest {

    @Mock
    private MedicationLogRepository repository;

    @InjectMocks
    private MedicationLogService service;

    @Test
    void getAllLogs_ReturnsList() {
        // Arrange
        MedicationLog log = new MedicationLog();
        when(repository.findAll()).thenReturn(Collections.singletonList(log));

        // Act
        List<MedicationLog> result = service.getAllLogs();

        // Assert
        assertEquals(1, result.size());
        assertEquals(log, result.get(0));
    }

    @Test
    void addLog_SavesAndReturnsLog() {
        // Arrange
        MedicationLog log = new MedicationLog();
        when(repository.save(log)).thenReturn(log);

        // Act
        MedicationLog result = service.addLog(log);

        // Assert
        assertNotNull(result);
        assertEquals(log, result);
    }

    @Test
    void getLogById_ReturnsLogIfExists() {
        // Arrange
        MedicationLog log = new MedicationLog();
        log.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(log));

        // Act
        MedicationLog result = service.getLogById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getLogById_ReturnsNullIfNotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act
        MedicationLog result = service.getLogById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteLog_DeletesLogIfExists() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = service.deleteLog(1L);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLog_ReturnsFalseIfLogDoesNotExist() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = service.deleteLog(1L);

        // Assert
        assertFalse(result);
        verify(repository, never()).deleteById(anyLong());
    }
}
