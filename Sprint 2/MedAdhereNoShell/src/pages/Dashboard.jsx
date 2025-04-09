import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import Navbar from "../components/Navbar";
import axios from "axios";

export default function Dashboard() {
  const [prescriptions, setPrescriptions] = useState([]);

    useEffect(() => {
        axios.get("/api/prescriptions")
            .then(response => {
                setPrescriptions(response.data);
            })
            .catch(error => {
                console.error("Error fetching prescriptions:", error);
            });
    }, []);

    return (
        <>
            <Navbar />
            <Container className="mt-4">
                <h1>Dashboard</h1>
                <ul>
                    {prescriptions.map(prescription => (
                        <li key={prescription.id}>{prescription.name} - {prescription.dosage}</li>
                    ))}
                </ul>
            </Container>
        </>
    );
}