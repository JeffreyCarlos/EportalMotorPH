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
 * @author jeffr
 */

public class EmployeeUI extends JFrame {
    private JTextField txtId, txtFirst, txtLast, txtBday, txtAdd, txtPhone, txtSss, txtPhilhealth,
                        txtTin, txtPagibig, txtStatus, txtPos, txtSup, txtBasicSal, txtRiceSub,
                        txtPhoneAllow, txtClothing, txtGrossSemi, txtHourlyRate;
    private JTextArea displayArea;

    public EmployeeUI() {
        setTitle("Employee Manager");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel formPanel = new JPanel(new GridBagLayout());
        addComponent(formPanel, gbc, new JLabel("Employee #:"), 0, 0);
        txtId = new JTextField(15);
        addComponent(formPanel, gbc, txtId, 1, 0);

        addComponent(formPanel, gbc, new JLabel("First Name:"), 0, 1);
        txtFirst = new JTextField(15);
        addComponent(formPanel, gbc, txtFirst, 1, 1);

        addComponent(formPanel, gbc, new JLabel("Last Name:"), 0, 2);
        txtLast = new JTextField(15);
        addComponent(formPanel, gbc, txtLast, 1, 2);

        addComponent(formPanel, gbc, new JLabel("Birthday:"), 0, 3);
        txtBday = new JTextField(15);
        addComponent(formPanel, gbc, txtBday, 1, 3);

        addComponent(formPanel, gbc, new JLabel("Address:"), 0, 4);
        txtAdd = new JTextField(15);
        addComponent(formPanel, gbc, txtAdd, 1, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Buttons span two columns
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnView = new JButton("View All");
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        buttonPanel.add(btnView);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        formPanel.add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        formPanel.add(scrollPane, gbc);

        add(formPanel);

        // Button Actions
        btnView.addActionListener(e -> viewEmployees());
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());

        setVisible(true);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    private void viewEmployees() {
        List<Employee> list = EmployeeCSVUtil.readEmployees();
        displayArea.setText("Employee List:\n");
        for (Employee e : list) {
            displayArea.append(e.getEmpId() + " - " + e.getLastName() + " " + e.getFirstName() + " " +
                               e.getBirthday() + " " + e.getAddress() + "\n");
        }
    }


    private void addEmployee() {
        Employee e = new Employee(txtId.getText(), txtFirst.getText(), txtLast.getText(), txtBday.getText(),
                                  txtAdd.getText(), txtPhone.getText(), txtSss.getText(), txtPhilhealth.getText(),
                                  txtTin.getText(), txtPagibig.getText(), txtStatus.getText(), txtPos.getText(),
                                  txtSup.getText(), txtBasicSal.getText(), txtRiceSub.getText(), txtPhoneAllow.getText(),
                                  txtClothing.getText(), txtGrossSemi.getText(), txtHourlyRate.getText());
     
        
        if (txtId.getText().isEmpty() || txtFirst.getText().isEmpty() || txtLast.getText().isEmpty() || 
    txtBday.getText().isEmpty() || txtAdd.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}
        
        
        EmployeeCSVUtil.addEmployee(e);
        JOptionPane.showMessageDialog(this, "Employee Added!");
        viewEmployees();
    }

    private void updateEmployee() {
        EmployeeCSVUtil.updateEmployee(txtId.getText(), txtFirst.getText(), txtLast.getText());
        JOptionPane.showMessageDialog(this, "Employee Updated!");
        viewEmployees();
    }

    private void deleteEmployee() {
        EmployeeCSVUtil.deleteEmployee(txtId.getText());
        JOptionPane.showMessageDialog(this, "Employee Deleted!");
        viewEmployees();
    }

    // ✅ Include this main method to launch the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeUI());
    }
}
