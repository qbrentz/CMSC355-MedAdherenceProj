import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Dashboard from "./Dashboard";
import axios from "axios";
import { BrowserRouter as Router } from "react-router-dom";

// Mock axios
jest.mock("axios");

describe("Dashboard Component", () => {
  beforeEach(() => {
    localStorage.setItem("userID", "123"); // Mock userID in localStorage
    localStorage.setItem("username", "testUser"); // Mock username in localStorage
  });

  afterEach(() => {
    localStorage.clear();
    jest.clearAllMocks();
  });

  test("renders the Dashboard component", () => {
    render(
      <Router>
        <Dashboard />
      </Router>
    );

    expect(screen.getByText("Dashboard")).toBeInTheDocument();
    expect(screen.getByText("Welcome to your Medication Adherence Tracker, testUser!")).toBeInTheDocument();
  });

  test("fetches and displays prescriptions", async () => {
    const mockPrescriptions = [
      {
        id: 1,
        medName: "Medication A",
        dose: "10mg",
        inventory: 30,
        pharmacyId: "Pharmacy123",
        schedule: "Daily",
      },
      {
        id: 2,
        medName: "Medication B",
        dose: "20mg",
        inventory: 15,
        pharmacyId: "Pharmacy456",
        schedule: "Weekly",
      },
    ];

    axios.get.mockResolvedValueOnce({ data: mockPrescriptions });

    render(
      <Router>
        <Dashboard />
      </Router>
    );

    // Wait for prescriptions to load
    await waitFor(() => {
      expect(screen.getByText("Medication A")).toBeInTheDocument();
      expect(screen.getByText("Medication B")).toBeInTheDocument();
    });
  });

  test("displays message when no prescriptions are found", async () => {
    axios.get.mockResolvedValueOnce({ data: [] });

    render(
      <Router>
        <Dashboard />
      </Router>
    );

    // Wait for prescriptions to load
    await waitFor(() => {
      expect(screen.getByText("No prescriptions found. Please add a prescription below.")).toBeInTheDocument();
    });
  });

  test("handles adding a new prescription", async () => {
    axios.post.mockResolvedValueOnce({
      data: {
        id: 3,
        medName: "Medication C",
        dose: "5mg",
        inventory: 20,
        pharmacyId: "Pharmacy789",
        schedule: "Monthly",
      },
    });

    render(
      <Router>
        <Dashboard />
      </Router>
    );

    fireEvent.change(screen.getByPlaceholderText("Enter medication name"), {
      target: { value: "Medication C" },
    });
    fireEvent.change(screen.getByPlaceholderText("Enter dose"), {
      target: { value: "5mg" },
    });
    fireEvent.change(screen.getByPlaceholderText("Enter inventory count"), {
      target: { value: "20" },
    });
    fireEvent.change(screen.getByPlaceholderText("Enter pharmacy ID"), {
      target: { value: "Pharmacy789" },
    });
    fireEvent.change(screen.getByPlaceholderText("Enter schedule (e.g., daily, weekly)"), {
      target: { value: "Monthly" },
    });

    fireEvent.click(screen.getByText("Add Prescription"));

    // Wait for the prescription to be added
    await waitFor(() => {
      expect(screen.getByText("Medication C")).toBeInTheDocument();
    });
  });

  test("handles deleting a prescription", async () => {
    const mockPrescriptions = [
      {
        id: 1,
        medName: "Medication A",
        dose: "10mg",
        inventory: 30,
        pharmacyId: "Pharmacy123",
        schedule: "Daily",
      },
    ];

    axios.get.mockResolvedValueOnce({ data: mockPrescriptions });
    axios.delete.mockResolvedValueOnce({});

    render(
      <Router>
        <Dashboard />
      </Router>
    );

    // Wait for prescriptions to load
    await waitFor(() => {
      expect(screen.getByText("Medication A")).toBeInTheDocument();
    });

    fireEvent.click(screen.getByText("Delete"));

    // Wait for the prescription to be deleted
    await waitFor(() => {
      expect(screen.queryByText("Medication A")).not.toBeInTheDocument();
    });
  });

  test("handles logging out", () => {
    render(
      <Router>
        <Dashboard />
      </Router>
    );

    fireEvent.click(screen.getByText("Logout"));

    expect(localStorage.getItem("userID")).toBeNull();
    expect(localStorage.getItem("username")).toBeNull();
  });
});