import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Login from "./Login";
import axios from "axios";
import { BrowserRouter as Router } from "react-router-dom";

// Mock axios
jest.mock("axios");

describe("Login Component", () => {
  afterEach(() => {
    jest.clearAllMocks();
    localStorage.clear();
  });

  test("renders the Login component", () => {
    render(
      <Router>
        <Login />
      </Router>
    );

    expect(screen.getByText("Patient Login")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Enter your username")).toBeInTheDocument();
    expect(screen.getByText("Login")).toBeInTheDocument();
    expect(screen.getByText("Don't have an account?")).toBeInTheDocument();
  });

  test("handles successful login", async () => {
    const mockResponse = { data: { userID: "123" } };
    axios.get.mockResolvedValueOnce(mockResponse);

    render(
      <Router>
        <Login />
      </Router>
    );

    // Simulate entering a username
    fireEvent.change(screen.getByPlaceholderText("Enter your username"), {
      target: { value: "testUser" },
    });

    // Simulate clicking the login button
    fireEvent.click(screen.getByText("Login"));

    // Wait for the API call and navigation
    await waitFor(() => {
      expect(localStorage.getItem("username")).toBe("testUser");
      expect(localStorage.getItem("userID")).toBe("123");
    });
  });

  test("handles invalid username", async () => {
    const mockResponse = { data: null };
    axios.get.mockResolvedValueOnce(mockResponse);

    render(
      <Router>
        <Login />
      </Router>
    );

    // Simulate entering an invalid username
    fireEvent.change(screen.getByPlaceholderText("Enter your username"), {
      target: { value: "invalidUser" },
    });

    // Simulate clicking the login button
    fireEvent.click(screen.getByText("Login"));

    // Wait for the error message to appear
    await waitFor(() => {
      expect(screen.getByText("Invalid username or user does not exist")).toBeInTheDocument();
    });
  });

  test("handles API error", async () => {
    axios.get.mockRejectedValueOnce(new Error("API Error"));

    render(
      <Router>
        <Login />
      </Router>
    );

    // Simulate entering a username
    fireEvent.change(screen.getByPlaceholderText("Enter your username"), {
      target: { value: "testUser" },
    });

    // Simulate clicking the login button
    fireEvent.click(screen.getByText("Login"));

    // Wait for the error message to appear
    await waitFor(() => {
      expect(screen.getByText("Login failed. Please try again.")).toBeInTheDocument();
    });
  });

  test("navigates to the signup page", () => {
    render(
      <Router>
        <Login />
      </Router>
    );

    // Simulate clicking the "Sign Up" button
    fireEvent.click(screen.getByText("Sign Up"));

    // Check if the navigation to the signup page occurred
    expect(window.location.pathname).toBe("/signup");
  });
});