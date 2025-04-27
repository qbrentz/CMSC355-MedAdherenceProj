package controlTests;

import com.example.medadherence.models.Notification;
import com.example.medadherence.services.NotificationService;
import com.example.medadherence.controllers.NotificationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getAllNotifications_ReturnsList() {
        // Arrange
        List<Notification> notifications = Arrays.asList(new Notification());
        when(notificationService.getAllNotifications()).thenReturn(notifications);

        // Act
        List<Notification> result = notificationController.getAllNotifications();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void addNotification_ReturnsCreatedNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("Test");
        when(notificationService.addNotification(any(Notification.class))).thenReturn(notification);

        // Act
        ResponseEntity<Notification> response = notificationController.addNotification(notification);

        // Safely handle the response body
        Notification responseBody = response.getBody();

        if (responseBody != null) {
            // Safely use responseBody
            Long id = responseBody.getId();
            System.out.println("Response ID: " + id);
        } else {
            // Handle the null case
            System.err.println("Response body is null");
        }

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().getMessage());
    }

    @Test
    void getNotificationById_ReturnsNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Test Notification");
        when(notificationService.getNotificationById(1L)).thenReturn(notification);

        // Act
        ResponseEntity<Notification> response = notificationController.getNotificationById(1L);

        // Safely handle the response body
        Notification responseBody = response.getBody();

        if (responseBody != null) {
            // Safely use responseBody
            Long id = responseBody.getId();
            System.out.println("Response ID: " + id);
        } else {
            // Handle the null case
            System.err.println("Response body is null");
        }

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Notification", response.getBody().getMessage());
    }

    @Test
    void getNotificationById_ReturnsNotFound() {
        // Arrange
        when(notificationService.getNotificationById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Notification> response = notificationController.getNotificationById(1L);

        // Safely handle the response body
        Notification responseBody = response.getBody();

        if (responseBody != null) {
            // Safely use responseBody
            Long id = responseBody.getId();
            System.out.println("Response ID: " + id);
        } else {
            // Handle the null case
            System.err.println("Response body is null");
        }

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteNotification_ReturnsSuccessMessage() {
        // Arrange
        Long notificationId = 1L;
        when(notificationService.deleteNotification(notificationId)).thenReturn("Notification deleted successfully");

        // Act
        ResponseEntity<String> response = notificationController.deleteNotification(notificationId);

        // Safely handle the response body
        String responseBody = response.getBody();

        if (responseBody != null) {
            // Safely use responseBody
            System.out.println("Response: " + responseBody);
        } else {
            // Handle the null case
            System.err.println("Response body is null");
        }

        // Assert
        assertNotNull(response.getBody());
        assertEquals("Notification deleted successfully", response.getBody());
    }

    @Test
    void deleteNotification_ReturnsNotFound() {
        // Arrange
        Long notificationId = 1L;
        when(notificationService.deleteNotification(notificationId)).thenReturn("Notification not found");

        // Act
        ResponseEntity<String> response = notificationController.deleteNotification(notificationId);

        // Safely handle the response body
        String responseBody = response.getBody();

        if (responseBody != null) {
            // Safely use responseBody
            System.out.println("Response: " + responseBody);
        } else {
            // Handle the null case
            System.err.println("Response body is null");
        }

        // Assert
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }
}
