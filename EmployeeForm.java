/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author jeffr
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
        add(new JLabel("Employee ID:")); empIdField = new JTextField(); add(empIdField);
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
        JButton saveButton = new JButton("Save Employee");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEmployee();
            }
        });
        add(saveButton);

        setVisible(true);
    }

    private void saveEmployee() {
        Employee newEmployee = new Employee(
            empIdField.getText(), firstNameField.getText(), lastNameField.getText(),
            birthdayField.getText(), addressField.getText(), phoneField.getText(),
            sssField.getText(), philhealthField.getText(), tinField.getText(), pagibigField.getText(),
            statusField.getText(), positionField.getText(), supervisorField.getText(),
            salaryField.getText(), riceSubsidyField.getText(), phoneAllowanceField.getText(),
            clothingAllowanceField.getText(), semiMonthlyRateField.getText(), hourlyRateField.getText()
        );

        EmployeeCSVUtil.addEmployee(newEmployee);
        JOptionPane.showMessageDialog(this, "Employee added successfully!");
        dispose(); // Close the window after saving
    }

    public static void main(String[] args) {
        new EmployeeForm();
    }
}
