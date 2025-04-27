package com.example.medadherence.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class MedicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long logId;
    private LocalDateTime timestamp;
    public Long prescriptionID;
    public Long patientID;

    @ManyToOne
    @JoinColumn(name = "med_name")
    public Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    public Patient patient;

    public MedicationLog() {}

    public MedicationLog(LocalDateTime timestamp, Long prescriptionID, Long patientID) {
        this.timestamp = timestamp;
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
    }

    // Getters and Setters
    public Long getId() { return logId; }
    public void setId(Long logId) { this.logId = logId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setPrescriptionID(Long prescriptionID) { this.prescriptionID = prescriptionID;}
    public Long getPrescriptionID() {return prescriptionID; }
    public Long getPatientID() { return patientID; }
    public void setPatientID(Long patientID) { this.patientID = patientID; }
    public void setPatient(Patient patient) {this.patient = patient; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }
    public Patient getPatient() { return patient; }
    public Prescription getPrescription() { return prescription; }

}
