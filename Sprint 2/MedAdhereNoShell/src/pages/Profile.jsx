import React, { useEffect, useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import Navbar from "../components/Navbar";
import axios from "axios";

export default function Profile() {
  const username = localStorage.getItem("username");
  console.log(username);
  const [id, setId] = useState(Number(""));
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");

  useEffect(() => {
    const response = axios.get(`/api/patient/api/username/${username}`);
    response.then(res => { // Access the response data within the .then block
      console.log(res.data);
      setId(res.data.id); // Update the state variables correctly
      setEmail(res.data.email);
      setName(res.data.name);
    });
  }, [username]); // Add username as a dependency

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.put(`/api/patient/${id}`, {
      name,
      email,
    }).then(() => alert("Profile updated!"))
      .catch(() => alert("Update failed"));
  };

  const handleDelete = (e) => {
    e.preventDefault();
    axios.delete(`/api/patient/api/${id} `)
      .then(() => {
        alert("Account deleted!");
        localStorage.removeItem("username");
        window.location.href = "/login";
      })
      .catch(() => alert("Delete failed"));
    };

  return (
    <div>
      <Navbar />
      <Container className="mt-4">
        <h3>Edit Profile</h3>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter your name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Form.Group>
          <Button type="submit" variant="primary">
            Save Changes
          </Button>
        </Form>
        <div className="mt-3">
          <Button variant="danger" onClick={handleDelete}>
            Delete Account
          </Button>
        </div>
      </Container>
    </div>
  );
}
