package com.example.medadherence.controllers;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.repositories.PatientRepository;
import com.example.medadherence.repositories.PrescriptionRepository;
import com.example.medadherence.services.MedicationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.Map;

@RestController
@RequestMapping("/api/medication_logs")
public class MedicationLogController {

    @Autowired
    private MedicationLogService medicationLogService;
    @Autowired
    private PatientRepository PatientRepository;
    @Autowired
    private PrescriptionRepository PrescriptionRepository;

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
    @PostMapping("/api/add/{patientId}/{prescriptionId}")
    public ResponseEntity<MedicationLog> addMedicationLog(@PathVariable Long patientId, @PathVariable Long prescriptionId) {
        // Extract patient and prescription IDs from the payload
        try {
            // Find the patient and prescription
            Patient patient = PatientRepository.findById(patientId).orElse(null);
            Prescription prescription = PrescriptionRepository.findById(prescriptionId).orElse(null);

            if (patient == null || prescription == null) {
                return ResponseEntity.badRequest().build();
            }

            // Create a new MedicationLog object
            MedicationLog log = new MedicationLog(LocalDateTime.now(), prescription);
            log.setPatient(patient);

            // Save the log
            MedicationLog savedLog = medicationLogService.addMedicationLog(log);
            return ResponseEntity.ok(savedLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
}
    

    /*@PostMapping("/api/newLog/{log}")
    public ResponseEntity<MedicationLog> addLog(@PathVariable MedicationLog log) {
        //MedicationLog new_log = new MedicationLog(log.getTimestamp(), log.getPrescription());
        return ResponseEntity.ok(medicationLogService.addLog(log));
    }*/

    

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
