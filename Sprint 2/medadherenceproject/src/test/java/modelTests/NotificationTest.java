package modelTests;

import com.example.medadherence.models.Notification;
import com.example.medadherence.models.Patient;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class NotificationTest {
    @Test
    void testNotificationConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification();
        notification.setMessage("Reminder to take medicine");
        notification.setTimestamp(now);
        assertEquals("Reminder to take medicine", notification.getMessage());
        assertEquals(now, notification.getTimestamp());
    }

    @Test
    void testMissingRequiredFields() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Notification(null, null, null);
        });
        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    void testRelationshipWithPatient() {
        Patient patient = new Patient("John Doe", "john@example.com");
        Notification notification = new Notification("Take your medication", null, patient);
        notification.setPatient(patient);
        assertEquals(patient, notification.getPatient());
    }
}