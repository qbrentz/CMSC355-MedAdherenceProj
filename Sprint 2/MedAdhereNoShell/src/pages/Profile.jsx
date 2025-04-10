import React, { useEffect, useState } from "react";
import { Container, Form, Button } from "react-bootstrap";
import Navbar from "../components/Navbar";
import axios from "axios";

export default function Profile() {
  const username = localStorage.getItem("username");
  const [id, setId] = useState("");
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/patient/api/${username}`)
      .then(res => {
        const user = res.data[0];
        setEmail(user.email);
        setName(user.name);
        setId(user.id);
      })
      .catch(err => console.error("Failed to load profile", err));
  }, [username]);

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.put(`http://localhost:8080/api/patient/${id}`, {
      name,
      email,
    }).then(() => alert("Profile updated!"))
      .catch(() => alert("Update failed"));
  };

  const handleDelete = (e) => {
    e.preventDefault();
    axios.delete(`http://localhost:8080/api/patient/api/${id} `)
      .then(() => {
        alert("Account deleted!");
        localStorage.removeItem("username");
        window.location.href = "/login";
      })
      .catch(() => alert("Delete failed"));

  return (
    <>
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
    </>
  );
}
}