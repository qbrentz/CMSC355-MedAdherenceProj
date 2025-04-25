import React, { useEffect, useState } from "react";
import { Container, Table } from "react-bootstrap";
import Navbar from "../components/Navbar";
import axios from "axios";

export default function History() {
  const username = localStorage.getItem("username");
  const userID = localStorage.getItem("userID");
  const [logs, setLogs] = useState([]);

  useEffect(() => {
    axios
      .get(`/api/medication_logs/api/getByPatient/${userID}`)
      .then((res) => setLogs(res.data))
      .catch((err) => console.error("Failed to load logs", err));
  }, [username]);

  return (
    <>
      <Navbar />
      <Container className="mt-4">
        <h3>Medication History</h3>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Medication</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {logs.map((log) => (
              <tr key={log.id}>
                <td>{log.timestamp}</td>
                <td>{log.medName}</td>
                <td>{log.status}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Container>
    </>
  );
}
