package com.example.medadherence.controllers;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.services.MedicationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication_logs")
public class MedicationLogController {

    @Autowired
    private MedicationLogService medicationLogService;

    // Get all medication logs
    @GetMapping
    public List<MedicationLog> getAllLogs() {
        return medicationLogService.getAllLogs();
    }

    @GetMapping("/api/getByPatient/{patientId}")
    public ResponseEntity<List<MedicationLog>> getLogsByPatientId(@PathVariable Long patientId) {
    List<MedicationLog> logs = medicationLogService.getLogsByPatientId(patientId);
    if (!logs.isEmpty()) {
        return ResponseEntity.ok(logs);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    // Add a new medication log
    @PostMapping("/api/add")
    public ResponseEntity<MedicationLog> addLog(@RequestBody MedicationLog log) {
        return ResponseEntity.ok(medicationLogService.addLog(log));
    }

    // Get a medication log by ID
    @GetMapping("/api/{id}")
    public ResponseEntity<MedicationLog> getLogById(@PathVariable Long id) {
        MedicationLog log = medicationLogService.getLogById(id);
        if (log != null) {
            return ResponseEntity.ok(log);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a medication log by ID
    @DeleteMapping("/api/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        boolean isDeleted = medicationLogService.deleteLog(id);
        if (isDeleted) {
            return ResponseEntity.ok("Medication log deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
