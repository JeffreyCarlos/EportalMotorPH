/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author jeffr
 */

public class UpdateEmployeeForm extends JFrame {
    private JTextField empIdField, firstNameField, lastNameField, birthdayField, addressField, phoneField,
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

        // Fetch employee details
        selectedEmployee = getEmployeeById(empId);
        if (selectedEmployee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Labels & Fields
        add(new JLabel("Employee ID:")); empIdField = new JTextField(selectedEmployee.getEmpId()); empIdField.setEditable(false); add(empIdField);
        add(new JLabel("First Name:")); firstNameField = new JTextField(selectedEmployee.getFirstName()); add(firstNameField);
        add(new JLabel("Last Name:")); lastNameField = new JTextField(selectedEmployee.getLastName()); add(lastNameField);
        add(new JLabel("Birthday:")); birthdayField = new JTextField(selectedEmployee.getBirthday()); add(birthdayField);
        add(new JLabel("Address:")); addressField = new JTextField(selectedEmployee.getAddress()); add(addressField);
        add(new JLabel("Phone Number:")); phoneField = new JTextField(selectedEmployee.getPhonenumber()); add(phoneField);
        add(new JLabel("SSS #:")); sssField = new JTextField(selectedEmployee.getsssId()); add(sssField);
        add(new JLabel("Philhealth #:")); philhealthField = new JTextField(selectedEmployee.getphilhealthId()); add(philhealthField);
        add(new JLabel("TIN #:")); tinField = new JTextField(selectedEmployee.gettinId()); add(tinField);
        add(new JLabel("Pagibig #:")); pagibigField = new JTextField(selectedEmployee.getpagibigId()); add(pagibigField);
        add(new JLabel("Status:")); statusField = new JTextField(selectedEmployee.getStatus()); add(statusField);
        add(new JLabel("Position:")); positionField = new JTextField(selectedEmployee.getPosition()); add(positionField);
        add(new JLabel("Immediate Supervisor:")); supervisorField = new JTextField(selectedEmployee.getImmediateSupervisor()); add(supervisorField);
        add(new JLabel("Basic Salary:")); salaryField = new JTextField(selectedEmployee.getBasicSalary()); add(salaryField);
        add(new JLabel("Rice Subsidy:")); riceSubsidyField = new JTextField(selectedEmployee.getRicesubsidy()); add(riceSubsidyField);
        add(new JLabel("Phone Allowance:")); phoneAllowanceField = new JTextField(selectedEmployee.getPhoneAllowance()); add(phoneAllowanceField);
        add(new JLabel("Clothing Allowance:")); clothingAllowanceField = new JTextField(selectedEmployee.getClothingAllowance()); add(clothingAllowanceField);
        add(new JLabel("Gross Semi-Monthly Rate:")); semiMonthlyRateField = new JTextField(selectedEmployee.getGrossSemiMonthlyRate()); add(semiMonthlyRateField);
        add(new JLabel("Hourly Rate:")); hourlyRateField = new JTextField(selectedEmployee.getHourlyRate()); add(hourlyRateField);

        // Save Button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveUpdatedEmployee());
        add(saveButton);

        setVisible(true);
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
