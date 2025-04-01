package com.example.medadherence.repositories;

import com.example.medadherence.models.MedicationLog;
import com.example.medadherence.models.Patient;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {
    List<MedicationLog> findByPatientAndTimestampBetween(Patient patient, LocalDateTime start, LocalDateTime end);
}