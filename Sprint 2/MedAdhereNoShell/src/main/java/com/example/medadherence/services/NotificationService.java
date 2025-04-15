package com.example.medadherence.services;

import com.example.medadherence.models.Notification;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.repositories.NotificationRepository;
import com.example.medadherence.repositories.PatientRepository;
import com.example.medadherence.repositories.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    

    private final NotificationRepository notificationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;

    public NotificationService(NotificationRepository notificationRepository, PrescriptionRepository prescriptionRepository, PatientRepository patientRepository) {
        this.notificationRepository = notificationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
    }

    public void checkAndNotifyLowStock() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
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