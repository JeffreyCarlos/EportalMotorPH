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
 * @author basil
 */
public class EmployeeForm extends JFrame {
    private JTextField empIdField, firstNameField, lastNameField, birthdayField, addressField, phoneField,
            sssField, philhealthField, tinField, pagibigField, statusField, positionField,
            supervisorField, salaryField, riceSubsidyField, phoneAllowanceField,
            clothingAllowanceField, semiMonthlyRateField, hourlyRateField;

    public EmployeeForm() {
        setTitle("Add New Employee");
        setSize(600, 400);
        setLayout(new GridLayout(10, 2)); // Simple grid layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels & Fields
        add(new JLabel("Employee ID:"));
        empIdField = new JTextField(generateNextEmployeeId()); // Auto-generate next ID
        empIdField.setEditable(false); // Make ID field non-editable
        add(empIdField);
        add(new JLabel("First Name:")); firstNameField = new JTextField(); add(firstNameField);
        add(new JLabel("Last Name:")); lastNameField = new JTextField(); add(lastNameField);
        add(new JLabel("Birthday:(mm/dd/yyyy)")); birthdayField = new JTextField(); add(birthdayField);
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
        JButton saveButton = new JButton("Save Employee");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });
        add(saveButton);

        setVisible(true);
    }

    private String generateNextEmployeeId() {
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        int maxId = 0;
        for (Employee e : employees) {
            try {
                int id = Integer.parseInt(e.getEmpId());
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ex) {
                // Skip non-numeric IDs
            }
        }
        return String.valueOf(maxId + 1); // Return next available ID
    }

    private void saveEmployee() {
        String empId = empIdField.getText().trim();

        // Check if all fields are filled
        if (!validateFields()) {
            JOptionPane.showMessageDialog(this, "Error: All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee newEmployee = new Employee(
            empId, firstNameField.getText().trim(), lastNameField.getText().trim(),
            birthdayField.getText().trim(), addressField.getText().trim(), phoneField.getText().trim(),
            sssField.getText().trim(), philhealthField.getText().trim(), tinField.getText().trim(), pagibigField.getText().trim(),
            statusField.getText().trim(), positionField.getText().trim(), supervisorField.getText().trim(),
            salaryField.getText().trim(), riceSubsidyField.getText().trim(), phoneAllowanceField.getText().trim(),
            clothingAllowanceField.getText().trim(), semiMonthlyRateField.getText().trim(), hourlyRateField.getText().trim()
        );

        EmployeeCSVUtil.addEmployee(newEmployee);
        JOptionPane.showMessageDialog(this, "Employee added successfully!");
        dispose(); // Close the window after saving
    }

    private boolean validateFields() {
        return !(empIdField.getText().trim().isEmpty() ||
                 firstNameField.getText().trim().isEmpty() ||
                 lastNameField.getText().trim().isEmpty() ||
                 birthdayField.getText().trim().isEmpty() ||
                 addressField.getText().trim().isEmpty() ||
                 phoneField.getText().trim().isEmpty() ||
                 sssField.getText().trim().isEmpty() ||
                 philhealthField.getText().trim().isEmpty() ||
                 tinField.getText().trim().isEmpty() ||
                 pagibigField.getText().trim().isEmpty() ||
                 statusField.getText().trim().isEmpty() ||
                 positionField.getText().trim().isEmpty() ||
                 supervisorField.getText().trim().isEmpty() ||
                 salaryField.getText().trim().isEmpty() ||
                 riceSubsidyField.getText().trim().isEmpty() ||
                 phoneAllowanceField.getText().trim().isEmpty() ||
                 clothingAllowanceField.getText().trim().isEmpty() ||
                 semiMonthlyRateField.getText().trim().isEmpty() ||
                 hourlyRateField.getText().trim().isEmpty());
    }

}