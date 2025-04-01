package com.example.medadherence.services;


import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Notification;
import com.example.medadherence.models.Patient;
import com.example.medadherence.repositories.MedicationLogRepository;
import com.example.medadherence.repositories.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationLogService {

    @Autowired
    
    private final MedicationLogRepository medicationLogRepository;
    private final NotificationRepository notificationRepository;

    public MedicationLogService(MedicationLogRepository medicationLogRepository, NotificationRepository notificationRepository) {
        this.medicationLogRepository = medicationLogRepository;
        this.notificationRepository = notificationRepository;
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

    // Get all medication logs
    public List<MedicationLog> getAllLogs() {
        return medicationLogRepository.findAll();
    }

    // Add a new medication log
    public MedicationLog addLog(MedicationLog log) {
        return medicationLogRepository.save(log);
    }

    // Get a medication log by ID
    public MedicationLog getLogById(Long id) {
        Optional<MedicationLog> log = medicationLogRepository.findById(id);
        return log.orElse(null);
    }

    // Delete a medication log by ID
    public boolean deleteLog(Long id) {
        if (medicationLogRepository.existsById(id)) {
            medicationLogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}