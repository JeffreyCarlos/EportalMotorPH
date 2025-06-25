/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author basil
 */
public class UpdateEmployeeForm extends JFrame {
    private JComboBox<String> empIdComboBox; // Changed from JTextField to JComboBox
    private JTextField firstNameField, lastNameField, birthdayField, addressField, phoneField,
            sssField, philhealthField, tinField, pagibigField, statusField, positionField,
            supervisorField, salaryField, riceSubsidyField, phoneAllowanceField,
            clothingAllowanceField, semiMonthlyRateField, hourlyRateField;

    private Employee selectedEmployee;
    private EmployeeUI parentUI; // Reference to EmployeeUI

    public UpdateEmployeeForm(EmployeeUI parentUI, String empId) {
        this.parentUI = parentUI;
        setTitle("Update Employee");
        setSize(600, 400);
        setLayout(new GridLayout(10, 2)); // Simple grid layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize JComboBox for Employee ID
        add(new JLabel("Employee ID:"));
        empIdComboBox = new JComboBox<>();
        empIdComboBox.addItem("Select Employee ID"); // Default placeholder
        populateEmployeeIds(); // Populate dropdown with employee IDs
        empIdComboBox.setSelectedItem(empId); // Pre-select the provided empId
        empIdComboBox.addActionListener(e -> loadSelectedEmployee()); // Load employee data when ID is selected
        add(empIdComboBox);

        // Labels & Fields (initially empty, populated by loadSelectedEmployee)
        add(new JLabel("First Name:")); firstNameField = new JTextField(); add(firstNameField);
        add(new JLabel("Last Name:")); lastNameField = new JTextField(); add(lastNameField);
        add(new JLabel("Birthday:")); birthdayField = new JTextField(); add(birthdayField);
        add(new JLabel("Address:")); addressField = new JTextField(); add(addressField);
        add(new JLabel("Phone Number:")); phoneField = new JTextField(); add(phoneField);
        add(new JLabel("SSS #:")); sssField = new JTextField(); add(sssField);
        add(new JLabel("Philhealth #:")); philhealthField = new JTextField(); add(philhealthField);
        add(new JLabel("TIN #:")); tinField = new JTextField(); add(tinField);
        add(new JLabel("Pagibig #:")); pagibigField = new JTextField(); add(pagibigField);
        add(new JLabel("Status:")); statusField = new JTextField(); add(statusField);
        add(new JLabel("Position:")); positionField = new JTextField(); add(positionField);
        add(new JLabel("Immediate Supervisor:")); supervisorField = new JTextField(); add(supervisorField);
        add(new JLabel("Basic Salary:")); salaryField = new JTextField(); add(salaryField);
        add(new JLabel("Rice Subsidy:")); riceSubsidyField = new JTextField(); add(riceSubsidyField);
        add(new JLabel("Phone Allowance:")); phoneAllowanceField = new JTextField(); add(phoneAllowanceField);
        add(new JLabel("Clothing Allowance:")); clothingAllowanceField = new JTextField(); add(clothingAllowanceField);
        add(new JLabel("Gross Semi-Monthly Rate:")); semiMonthlyRateField = new JTextField(); add(semiMonthlyRateField);
        add(new JLabel("Hourly Rate:")); hourlyRateField = new JTextField(); add(hourlyRateField);

        // Save Button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveUpdatedEmployee());
        add(saveButton);

        // Load initial employee data if empId is provided
        loadSelectedEmployee();

        setVisible(true);
    }

    private void populateEmployeeIds() {
        empIdComboBox.removeAllItems(); // Clear existing items
        empIdComboBox.addItem("Select Employee ID"); // Add placeholder
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            empIdComboBox.addItem(e.getEmpId()); // Add each employee ID
        }
    }

    private void loadSelectedEmployee() {
        String selectedId = (String) empIdComboBox.getSelectedItem();
        if (selectedId == null || selectedId.equals("Select Employee ID")) {
            clearFields();
            selectedEmployee = null;
            return;
        }

        selectedEmployee = getEmployeeById(selectedId);
        if (selectedEmployee != null) {
            firstNameField.setText(selectedEmployee.getFirstName());
            lastNameField.setText(selectedEmployee.getLastName());
            birthdayField.setText(selectedEmployee.getBirthday());
            addressField.setText(selectedEmployee.getAddress());
            phoneField.setText(selectedEmployee.getPhonenumber());
            sssField.setText(selectedEmployee.getsssId());
            philhealthField.setText(selectedEmployee.getphilhealthId());
            tinField.setText(selectedEmployee.gettinId());
            pagibigField.setText(selectedEmployee.getpagibigId());
            statusField.setText(selectedEmployee.getStatus());
            positionField.setText(selectedEmployee.getPosition());
            supervisorField.setText(selectedEmployee.getImmediateSupervisor());
            salaryField.setText(selectedEmployee.getBasicSalary());
            riceSubsidyField.setText(selectedEmployee.getRicesubsidy());
            phoneAllowanceField.setText(selectedEmployee.getPhoneAllowance());
            clothingAllowanceField.setText(selectedEmployee.getClothingAllowance());
            semiMonthlyRateField.setText(selectedEmployee.getGrossSemiMonthlyRate());
            hourlyRateField.setText(selectedEmployee.getHourlyRate());
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            clearFields();
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        birthdayField.setText("");
        addressField.setText("");
        phoneField.setText("");
        sssField.setText("");
        philhealthField.setText("");
        tinField.setText("");
        pagibigField.setText("");
        statusField.setText("");
        positionField.setText("");
        supervisorField.setText("");
        salaryField.setText("");
        riceSubsidyField.setText("");
        phoneAllowanceField.setText("");
        clothingAllowanceField.setText("");
        semiMonthlyRateField.setText("");
        hourlyRateField.setText("");
    }

    private Employee getEmployeeById(String empId) {
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            if (e.getEmpId().equals(empId)) {
                return e;
            }
        }
        return null; // Employee not found
    }

    private void saveUpdatedEmployee() {
        String selectedId = (String) empIdComboBox.getSelectedItem();
        if (selectedId == null || selectedId.equals("Select Employee ID")) {
            JOptionPane.showMessageDialog(this, "Please select an Employee ID!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedEmployee == null) {
            JOptionPane.showMessageDialog(this, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedEmployee.setFirstName(firstNameField.getText());
        selectedEmployee.setLastName(lastNameField.getText());
        selectedEmployee.setBirthday(birthdayField.getText());
        selectedEmployee.setAddress(addressField.getText());
        selectedEmployee.setPhoneNumber(phoneField.getText());
        selectedEmployee.setSssId(sssField.getText());
        selectedEmployee.setPhilhealthId(philhealthField.getText());
        selectedEmployee.setTinId(tinField.getText());
        selectedEmployee.setPagibigId(pagibigField.getText());
        selectedEmployee.setStatus(statusField.getText());
        selectedEmployee.setPosition(positionField.getText());
        selectedEmployee.setImmediateSupervisor(supervisorField.getText());
        selectedEmployee.setBasicSalary(salaryField.getText());
        selectedEmployee.setRiceSubsidy(riceSubsidyField.getText());
        selectedEmployee.setPhoneAllowance(phoneAllowanceField.getText());
        selectedEmployee.setClothingAllowance(clothingAllowanceField.getText());
        selectedEmployee.setGrossSemimonthlyRate(semiMonthlyRateField.getText());
        selectedEmployee.setHourlyRate(hourlyRateField.getText());

        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmpId().equals(selectedEmployee.getEmpId())) {
                employees.set(i, selectedEmployee);
                break;
            }
        }

        EmployeeCSVUtil.writeEmployees(employees);
        JOptionPane.showMessageDialog(this, "Employee Updated Successfully!");

        // Refresh main EmployeeUI table
        if (parentUI != null) {
            parentUI.viewEmployees(); // Ensures UI refreshes
        }

        dispose(); // Close the window after saving
    }
}