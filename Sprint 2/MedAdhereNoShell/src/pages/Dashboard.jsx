import React, { useEffect, useState } from "react";
import { Container, Form, Button, Table } from "react-bootstrap";
import Navbar from "../components/Navbar";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

export default function Dashboard() {
  const [prescriptions, setPrescriptions] = useState([]);
  //const [logPrescriptionName, setLogPrescriptionName] = useState('');
  //const [logTime, setLogTime] = Date();
  const [medName, setMedName] = useState("");
  const [dose, setDose] = useState("");
  const [inventory, setInventory] = useState("");
  const [pharmacyId, setPharmacyId] = useState("");
  const [schedule, setSchedule] = useState("");
  const username = localStorage.getItem("username");
  const [editPrescriptionId, setEditPrescriptionId] = useState(null); // For editing prescriptions
  const navigate = useNavigate();

  useEffect(() => {
    const userID = localStorage.getItem("userID");
    
    if (!userID) {
      navigate("/login");
    }
    fetchPrescriptions(userID);
  }, [navigate]);

  const fetchPrescriptions = async (userID) => {
    if (!userID) {
      console.error("UserID is null. Cannot fetch prescriptions.");
      return;
    }
  
    try {
      const response = await axios.get(`/api/prescriptions/api/getByUserID/${userID}`);
      setPrescriptions(response.data);
    } catch (error) {
      console.error("Error fetching prescriptions:", error);
    }
  };

  const patientDetails = async (userID) => {
    try {
      const response = await axios.get(`/api/patient/api/${userID}`);
      console.log("Patient details:", response.data);
      const patient = response.data[0];
      return patient;
    } catch (error) {
      console.error("Error fetching patient details:", error);
    }
  };

  const handleAddMedicationLog = async (prescription) => {
    //e.preventDefault();
    const logTime = new Date(); // Time the medication was taken
    try {
      const response = await axios.post(`/api/medication_logs/api/add/${prescription}`, {
        timestamp: logTime, // Time the medication was taken
      });
      console.log("Medication log added:", response.data);
      resetLogForm();
    } catch (error) {
      console.error("Error adding medication log:", error);
    }
  };
  
  const resetLogForm = () => {
    setLogPrescriptionId("");
    setLogTime("");
  };
  

  const handleAddOrUpdatePrescription = async (e) => {
    e.preventDefault();
    const userID = localStorage.getItem("userID");
    try {
      if (editPrescriptionId) {
        // Update existing prescription
        const response = await axios.put(`/api/prescriptions/api/updateBy/${editPrescriptionId}`, {
          medName,
          dose,
          inventory,
          pharmacyId,
          schedule,
        });
        console.log("Prescription updated:", response.data);
      } else {
        // Add new prescription
        const response = await axios.post(`/api/prescriptions/api/addNew`, {
          medName,
          dose,
          inventory,
          pharmacyId,
          schedule,
          
        });
        console.log("Prescription added:", response.data);
      }
      //resetForm();
      fetchPrescriptions(localStorage.getItem("userID"));
    } catch (error) {
      console.error("Error adding/updating prescription:", error);
    }
  };



  const handleEditPrescription = (prescription) => {
    setEditPrescriptionId(prescription.id);
    setMedName(prescription.medName);
    setDose(prescription.dose);
    setInventory(prescription.inventory);
    setPharmacyId(prescription.pharmacyId);
    setSchedule(prescription.schedule);
  };

  const handleDeletePrescription = async (id) => {
    try {
      await axios.delete(`/api/prescriptions/${id}`);
      console.log("Prescription deleted:", id);
      fetchPrescriptions(localStorage.getItem("userID"));
    } catch (error) {
      console.error("Error deleting prescription:", error);
    }
  };

  const resetForm = () => {
    setEditPrescriptionId(null);
    setMedName("");
    setDose("");
    setInventory("");
    setPharmacyId("");
    setSchedule("");
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  
  
  
  return (
    <div>
      <Navbar />
      <Container>
        <h1>Dashboard</h1>
        <h2>Welcome to your Medication Adherence Tracker, {username}!</h2>
        <nav>
          <ul>
            <li><Link to="/history">History</Link></li>
            <li><Link to="/profile">Profile</Link></li>
          </ul>
        </nav>
  
        <h2>Prescriptions</h2>
      {prescriptions.length === 0 ? (
        <div>
          <p>No prescriptions found. Please add a prescription below.</p>
        </div>
      ) : (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Medication Name</th>
              <th>Dose</th>
              <th>Inventory</th>
              <th>Medication ID</th>
              <th>Pharmacy ID</th>
              <th>Schedule</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {prescriptions.map((prescription) => (
              <tr key={prescription.id}>
                <td>{prescription.medName}</td>
                <td>{prescription.dose}</td>
                <td>{prescription.inventory}</td>
                <td>{prescription.medicationId}</td>
                <td>{prescription.pharmacyId}</td>
                <td>{prescription.schedule}</td>
                <td>
                <Button
                    variant="info"
                    size="sm-6"
                    onClick={() => handleAddMedicationLog(prescription)}
                    >
                      Log Medication
                    </Button>
                  <Button
                    variant="warning"
                    size="sm-3"
                    onClick={() => handleEditPrescription(prescription)}
                  >
                    Edit
                  </Button>{" "}
                  <Button
                    variant="danger"
                    size="sm-3"
                    onClick={() => handleDeletePrescription(prescription.id)}
                  >
                    Delete
                  </Button>
                  
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    

      

  
        <h2>{editPrescriptionId ? "Edit Prescription" : "Add Prescription"}</h2>
        <Form onSubmit={handleAddOrUpdatePrescription}>
          <Form.Group className="mb-3">
            <Form.Label>Medication Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter medication name"
              value={medName}
              onChange={(e) => setMedName(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Dose</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter dose"
              value={dose}
              onChange={(e) => setDose(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Inventory</Form.Label>
            <Form.Control
              type="number"
              placeholder="Enter inventory count"
              value={inventory}
              onChange={(e) => setInventory(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Pharmacy ID</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter pharmacy ID"
              value={pharmacyId}
              onChange={(e) => setPharmacyId(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Schedule</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter schedule (e.g., daily, weekly)"
              value={schedule}
              onChange={(e) => setSchedule(e.target.value)}
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            {editPrescriptionId ? "Update Prescription" : "Add Prescription"}
          </Button>
          {editPrescriptionId && (
            <Button variant="secondary" onClick={resetForm} className="ms-2">
              Cancel
            </Button>
          )}
        </Form>
        <Button variant="danger" onClick={handleLogout} className="mt-3">
          Logout
        </Button>
      </Container>
    </div>
  );
}