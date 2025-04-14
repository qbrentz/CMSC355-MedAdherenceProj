import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Card } from "react-bootstrap";
import axios from "axios";

export function SignUp() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const navigate = useNavigate();
  
    const handleSignUp = async (e) => {
      e.preventDefault();
      try {
        const newPatient = { name: name, userName: username, email: email };
        const res = await axios.post("/api/patient", newPatient);
        if (res.status === 200 || res.status === 201) {
          setSuccess("Account created successfully! Redirecting to login...");
          setTimeout(() => navigate("/login"), 2000); // Redirect to login after 2 seconds
        }
      } catch (err) {
        setError("Failed to create account. Please try again.");
      }
    };
  
    return (
      <Container className="d-flex justify-content-center align-items-center vh-100">
        <Card style={{ width: "24rem" }} className="p-4">
          <Card.Body>
            <Card.Title className="text-center mb-4">Create an Account</Card.Title>
            {error && <p className="text-danger">{error}</p>}
            {success && <p className="text-success">{success}</p>}
            <Form onSubmit={handleSignUp}>
              <Form.Group className="mb-3">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter your email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </Form.Group>
              <Button variant="primary" type="submit" className="w-100">
                Sign Up
              </Button>
            </Form>
          </Card.Body>
        </Card>
      </Container>
    );
  }
