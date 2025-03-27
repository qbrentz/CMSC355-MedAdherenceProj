package com.example.medadherence.repositories;

import com.example.medadherence.models.MedicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {}