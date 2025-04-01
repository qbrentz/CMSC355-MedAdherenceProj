package com.example.medadherence.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public MedicationLog() {}

    public MedicationLog(LocalDateTime timestamp, Prescription prescription) {
        this.timestamp = timestamp;
        this.prescription = prescription;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription;}
    public Prescription getPrescription() {return prescription; }

}
