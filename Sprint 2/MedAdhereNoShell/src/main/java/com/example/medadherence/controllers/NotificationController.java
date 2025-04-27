package com.example.medadherence.controllers;

import com.example.medadherence.models.Notification;
import com.example.medadherence.models.Patient;
import com.example.medadherence.services.NotificationService;
import com.example.medadherence.services.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PatientService patientService;

    // Get all notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Get Notifications by patientID
    @GetMapping("/api/getByPatient/{userID}")
    public ResponseEntity<List<Notification>> getByPatient(@RequestBody Long userID){
        Patient patient = patientService.getPatientById(userID);
        notificationService.checkForMissedDoses(patient);
        notificationService.checkAndNotifyLowStock(patient);
        return ResponseEntity.ok(notificationService.getByPatient(userID));
    }

    // Add a new notification
    @PostMapping("/api/")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        //notificationService.checkAndNotifyLowStock();
        return ResponseEntity.ok(notificationService.addNotification(notification));
    }

    // Get a notification by ID
    @GetMapping("/api/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a notification by ID
    @DeleteMapping("/api/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        String result = notificationService.deleteNotification(id);
        if (result.equals("Notification deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}