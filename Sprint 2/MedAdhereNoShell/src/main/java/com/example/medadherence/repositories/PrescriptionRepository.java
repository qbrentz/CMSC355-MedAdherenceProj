package com.example.medadherence.repositories;

import com.example.medadherence.models.Prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientId(Long patientId);
    List<Prescription> findByPatientUserName(String patientUserName);
    Prescription findBymedName(String medName);
}