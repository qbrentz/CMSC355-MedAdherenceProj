package com.example.medadherence.controllers;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.services.MedicationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medication_logs")
public class MedicationLogController {

    @Autowired
    private MedicationLogService medicationLogService;

    // Get all medication logs
    @GetMapping
    public List<MedicationLog> getAllLogs() {
        return medicationLogService.getAllLogs();
    }

    // Add a new medication log
    @PostMapping
    public ResponseEntity<MedicationLog> addLog(@RequestBody MedicationLog log) {
        return ResponseEntity.ok(medicationLogService.addLog(log));
    }

    // Get a medication log by ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicationLog> getLogById(@PathVariable Long id) {
        MedicationLog log = medicationLogService.getLogById(id);
        if (log != null) {
            return ResponseEntity.ok(log);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a medication log by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        boolean isDeleted = medicationLogService.deleteLog(id);
        if (isDeleted) {
            return ResponseEntity.ok("Medication log deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}