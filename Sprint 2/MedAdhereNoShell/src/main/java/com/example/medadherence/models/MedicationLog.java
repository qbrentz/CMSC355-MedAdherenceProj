package com.example.medadherence.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //public Prescription prescription;
    private Long logId;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "medName")
    public Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public MedicationLog() {}

    public MedicationLog(LocalDateTime timestamp, Prescription prescription) {
        this.timestamp = timestamp;
        this.prescription = prescription;
    }

    // Getters and Setters
    public Long getId() { return logId; }
    public void setId(Long logId) { this.logId = logId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription;}
    public Prescription getPrescription() {return prescription; }

}
