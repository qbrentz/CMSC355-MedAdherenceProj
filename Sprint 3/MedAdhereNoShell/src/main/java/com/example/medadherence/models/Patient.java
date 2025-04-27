package com.example.medadherence.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String email;
    public LocalDateTime createTime;
    public String userName;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<MedicationLog> medicationLogs;

    public Patient(){}
    
    public Patient(String name, String email, String userName) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (userName == null) {
            throw new IllegalArgumentException("UserName cannot be null");
        }
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.createTime = LocalDateTime.now();
    }
    
    public LocalDateTime getCreateTime() { return createTime;}
    public void setCreateTime(LocalDateTime time) { this.createTime = time; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
}

