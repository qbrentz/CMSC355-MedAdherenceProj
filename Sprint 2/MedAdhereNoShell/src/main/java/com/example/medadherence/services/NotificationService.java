package com.example.medadherence.services;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Notification;
import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.repositories.NotificationRepository;
import com.example.medadherence.repositories.PatientRepository;
import com.example.medadherence.repositories.PrescriptionRepository;
import com.example.medadherence.repositories.MedicationLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    

    private final NotificationRepository notificationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationLogRepository medicationLogRepository;

    public NotificationService(NotificationRepository notificationRepository, PrescriptionRepository prescriptionRepository, PatientRepository patientRepository, MedicationLogRepository medicationLogRepository) {
        this.notificationRepository = notificationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicationLogRepository = medicationLogRepository;
    }

    public void checkForMissedDoses(Patient patientId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minus(1, ChronoUnit.DAYS);

        List<MedicationLog> logs = medicationLogRepository.findByPatientAndTimestampBetween(patientId, oneDayAgo, now);

        if (logs.isEmpty()) {  // No logs in last 24 hours
            Notification notification = new Notification();
            notification.setPatient(patientId);
            notification.setMessage("Missed dose alert: No medication logs recorded in the past 24 hours.");
            notification.setTimestamp(now);
            notificationRepository.save(notification);
        }
    }

    public void checkAndNotifyLowStock(Patient patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId.id);
        for (Prescription prescription : prescriptions) {
            if (prescription.getInventory() < 5) {  // Alert if less than 5 doses left
                Notification notification = new Notification();
                notification.setPatient(prescription.getPatient());
                notification.setMessage("Low stock alert: " + prescription.getMedName() + " is running low.");
                notification.setTimestamp(LocalDateTime.now());
                notificationRepository.save(notification);
            }
        }
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // get notifactions by Patient
    public List<Notification> getByPatient(Long patientid){
        return notificationRepository.findByPatientId(patientid);
    }

    // Add a new notification
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Get a notification by ID
    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    // Delete a notification by ID
    public String deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return "Notification deleted successfully";
        }
        return "Notification not found";
    }
}