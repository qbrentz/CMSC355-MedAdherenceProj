import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Card } from "react-bootstrap";
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [userID, setUserID] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Make API call to check if username exists and retrieve userID
      const res = await axios.get(`/api/patient/api/usernameExists/${username}`);
      console.log("API Response:", res.data); // Debugging
  
      // Check API response
      if (res.data && res.data.userID) { // Assuming backend returns an object with userID
        localStorage.setItem("username", username);
        localStorage.setItem("userID", res.data.userID); // Store userID in localStorage
        navigate("/dashboard"); // Navigate to Dashboard
      } else {
        setError("Invalid username or user does not exist");
      }
    } catch (err) {
      console.error("API Error:", err); // Debugging
      setError("Login failed. Please try again.");
    }
  };


  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: "24rem" }} className="p-4">
        <Card.Body>
          <Card.Title className="text-center mb-4">Patient Login</Card.Title>
          {error && <p className="text-danger">{error}</p>}
          <Form onSubmit={handleLogin}>
            <Form.Group className="mb-3">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter your username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </Form.Group>
            <Button variant="primary" type="submit" className="w-100">
              Login
            </Button>
            
          </Form>
          <div className="text-center mt-3">
            <p>Don't have an account?</p>
          <Button variant="secondary" onClick={() => navigate("/signup")}>
              Sign Up
            </Button>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );


}


