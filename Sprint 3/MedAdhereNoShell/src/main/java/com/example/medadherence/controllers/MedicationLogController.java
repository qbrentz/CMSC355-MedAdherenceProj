package com.example.medadherence.controllers;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Patient;
import com.example.medadherence.models.Prescription;
import com.example.medadherence.services.PatientService;
import com.example.medadherence.services.PrescriptionService;
import com.example.medadherence.services.MedicationLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(value = "/api/medication_logs")
public class MedicationLogController {

    @Autowired
    private MedicationLogService medicationLogService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private PatientService PatientService;

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
    @PostMapping(value = "/api/add")
    public ResponseEntity<MedicationLog> addMedicationLog(@RequestBody MedicationLog newLog) {
        Patient patient = PatientService.getPatientById(newLog.patientID);
        Prescription prescription = prescriptionService.getPrescriptionById(newLog.prescriptionID);
        prescription.inventory = (prescription.inventory - 1);
        newLog.setTimestamp(LocalDateTime.now());
        newLog.setPatient(patient);
        newLog.setPrescription(prescription);
        
        return ResponseEntity.ok(medicationLogService.addLog(newLog));
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
