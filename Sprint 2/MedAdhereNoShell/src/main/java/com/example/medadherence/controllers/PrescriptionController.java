package com.example.medadherence.controllers;

import com.example.medadherence.models.Prescription;
import com.example.medadherence.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Get all prescriptions
    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    // Add a new prescription
    @PostMapping
    public ResponseEntity<Prescription> addPrescription(@RequestBody Prescription prescription) {
        return ResponseEntity.ok(prescriptionService.addPrescription(prescription));
    }

    // Get a prescription by ID
    @GetMapping("/api/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        if (prescription != null) {
            return ResponseEntity.ok(prescription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a prescription
    @PutMapping("/api/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription updatedPrescription) {
        Prescription prescription = prescriptionService.updatePrescription(id, updatedPrescription);
        if (prescription != null) {
            return ResponseEntity.ok(prescription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a prescription
    @DeleteMapping("/api/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        boolean isDeleted = prescriptionService.deletePrescription(id);
        if (isDeleted) {
            return ResponseEntity.ok("Prescription deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
