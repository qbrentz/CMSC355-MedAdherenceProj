package com.example.medadherence.repositories;

import com.example.medadherence.models.Patient;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByName(String name);

    List<Patient> findByEmail(String email);

    boolean existsByUserName(String userName);

    Patient findByUserName(String userName);
    
}