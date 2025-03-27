package com.example.medadherence.services;


import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.repositories.MedicationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationLogService {

    @Autowired
    private MedicationLogRepository medicationLogRepository;

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