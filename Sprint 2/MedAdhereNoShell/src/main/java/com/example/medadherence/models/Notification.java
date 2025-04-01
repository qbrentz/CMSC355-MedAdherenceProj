package com.example.medadherence.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Notification() {}
    
    public Notification(String message, LocalDateTime timestamp, Patient patient) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.message = message;
        this.timestamp = timestamp;
        this.patient = patient;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Patient getPatient() { return patient;}
    public void setPatient(Patient patient) { this.patient = patient;}

}