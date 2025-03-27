package serviceTests;

import com.example.medadherence.models.Notification;
import com.example.medadherence.repositories.NotificationRepository;
import com.example.medadherence.services.NotificationService;
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
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService service;

    @Test
    void getAllNotifications_ReturnsList() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("Test Notification");
        when(repository.findAll()).thenReturn(Collections.singletonList(notification));

        // Act
        List<Notification> result = service.getAllNotifications();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Notification", result.get(0).getMessage());
    }

    @Test
    void addNotification_SavesAndReturnsNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("New Notification");
        when(repository.save(notification)).thenReturn(notification);

        // Act
        Notification result = service.addNotification(notification);

        // Assert
        assertNotNull(result);
        assertEquals("New Notification", result.getMessage());
    }

    @Test
    void getNotificationById_ReturnsNotificationIfExists() {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Existing Notification");
        when(repository.findById(1L)).thenReturn(Optional.of(notification));

        // Act
        Notification result = service.getNotificationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Existing Notification", result.getMessage());
    }

    @Test
    void getNotificationById_ReturnsNullIfNotFound() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Notification result = service.getNotificationById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteNotification_DeletesNotificationIfExists() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);

        // Act
        String result = service.deleteNotification(1L);

        // Assert
        assertEquals("Notification deleted successfully", result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotification_ReturnsNotFoundIfNotificationDoesNotExist() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(false);

        // Act
        String result = service.deleteNotification(1L);

        // Assert
        assertEquals("Notification not found", result);
        verify(repository, never()).deleteById(anyLong());
    }
}
