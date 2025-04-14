package com.example.medadherence.models;

import jakarta.persistence.*;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String medName;
    public float dose;
    public int inventory;
    public String pharmacyId;
    public String medicationId;
    public String schedule;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    public Prescription() {}
    
    public Prescription(String medName, float dose, int inventory, String pharmacyId, String medicationId, String schedule, Patient patient) {
        if (medName == null) {
            throw new IllegalArgumentException("Medication name cannot be null");
        }
        this.medName = medName;
        this.dose = dose;
        this.inventory = inventory;
        this.pharmacyId = pharmacyId;
        this.medicationId = medicationId;
        this.schedule = schedule;
        this.patient = patient;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMedName() { return medName; }
    public void setMedName(String medName) { this.medName = medName; }
    public float getDose() { return dose; }
    public void setDose(float dose) { this.dose = dose; }
    public int getInventory() { return inventory; }
    public void setInventory(int inventory) { this.inventory = inventory; }
    public String getPharmacyId() { return pharmacyId; }
    public void setPharmacyId(String pharmacyId) { this.pharmacyId = pharmacyId; }
    public String getMedicationId() { return medicationId; }
    public void setMedicationId(String medicationId) { this.medicationId = medicationId; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    //public Patient getPatient() { return patient; }
    //public void setPatient(Patient patient) { this.patient = patient; }
}