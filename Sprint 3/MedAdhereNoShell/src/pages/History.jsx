import React, { useEffect, useState } from "react";
import { Container, Table } from "react-bootstrap";
import Navbar from "../components/Navbar";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

export default function History() {
  const username = localStorage.getItem("username");
  const userID = localStorage.getItem("userID");
  const navigate = useNavigate();
  const [logs, setLogs] = useState([]);
  
 


  useEffect(() => {
    const userID = localStorage.getItem("userID");

    if (!userID) {
      Navigate("/login");
    }
    fetchLogs(userID);
    //console.log(logs);
    //fetchMedDetails(logs);
  }, [navigate]);

  const fetchLogs = async (userID) => {

    try {
      const resLog = await axios.get(`/api/medication_logs/api/getByPatient/${userID}`);
      setLogs(resLog.data);
    } catch (error) {
      console.error("Error fetching logs:", error);
    }
  };

  

  return (
    <>
      <Navbar />
      <Container className="mt-4">
        <h3>Medication History</h3>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Log ID</th>
              <th>Medication</th>
              <th>Dose</th>
            </tr>
          </thead>
          <tbody>
            {logs.map((log) => (
              <tr key={log.id}>
                <td>{log.timestamp}</td>
                <td>{log.id}</td>
                <td>{log.prescription.medName}</td>
                <td>{log.prescription.dose}</td>
              </tr>
            ))}
            
          </tbody>
        </Table>
      </Container>
    </>
  );
}
