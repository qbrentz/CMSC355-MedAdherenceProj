package com.example.medadherence.controllers;

import com.example.medadherence.models.Patient;
import com.example.medadherence.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Add a new patient
    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.addPatient(patient));
    }

    // Get a patient by ID
    @GetMapping("/api/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a Patient by user name
    @GetMapping("/api/username/{userName}")
    public ResponseEntity<Patient> getPatientByUserName(@PathVariable String userName) {
        Patient patient = patientService.getPatientByUserName(userName);
        if (patient.userName != null) {
            return ResponseEntity.ok(patientService.getPatientByUserName(userName));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/usernameExists/{username}")
    public ResponseEntity<Map<String, Long>> checkUsernameExists(@PathVariable String username) {
        Patient patient = patientService.getPatientByUserName(username);
        if (patient != null) {
            return ResponseEntity.ok(Map.of("userID", patient.id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of());
        }
    }

    // Update a patient
    @PutMapping("/api/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(id, updatedPatient);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a patient
    @DeleteMapping("/api/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        boolean isDeleted = patientService.deletePatient(id);
        if (isDeleted) {
            return ResponseEntity.ok("Patient deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}