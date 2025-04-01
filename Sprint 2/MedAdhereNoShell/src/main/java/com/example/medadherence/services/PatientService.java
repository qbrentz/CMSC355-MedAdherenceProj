package com.example.medadherence.services;

import com.example.medadherence.models.*;
import com.example.medadherence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {
    @Autowired private PatientRepository patientRepository;
    public List<Patient> getAllPatients() { return patientRepository.findAll(); }
    public Patient addPatient(Patient patient) { return patientRepository.save(patient); }
    public Patient getPatientById(Long id) { return patientRepository.findById(id).orElse(null);}
    public List<Patient> getPatientByName(String name) { return patientRepository.findByName(name);}
    public Patient getPatientByUserName(String userName) { return patientRepository.findByUserName(userName);}
    public Patient updatePatient(Long id, Patient updatedPatient) { return patientRepository.save(updatedPatient);}
    
    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

}