package com.example.medadherence.services;

import com.example.medadherence.models.Prescription;
import com.example.medadherence.repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Get all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // Add a new prescription
    public Prescription addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    // Get a prescription by ID
    public Prescription getPrescriptionById(Long id) {
        Optional<Prescription> prescription = prescriptionRepository.findById(id);
        return prescription.orElse(null);
    }

    public List<Prescription> findByPatientId(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<Prescription> findByPatientUserName(String userName) {
        return prescriptionRepository.findByPatientUserName(userName);
    }

    // Update a prescription
    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        if (prescriptionRepository.existsById(id)) {
            updatedPrescription.setId(id); // Ensure the ID remains the same
            return prescriptionRepository.save(updatedPrescription);
        }
        return null;
    }

    // Delete a prescription
    public boolean deletePrescription(Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Prescription findBymedName(String medName) {
        return prescriptionRepository.findBymedName(medName);
    }
}
