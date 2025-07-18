/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author jeffr
 */
public class EmployeeUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeUI() {
        setTitle("Employee Manager");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnView = new JButton("View All");
        JButton btnAdd = new JButton("Add");
        JButton btnViewEmployee = new JButton("View Employee");
        JButton btnLogout = new JButton("Logout");

        buttonPanel.add(btnView);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnViewEmployee);
        buttonPanel.add(btnLogout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        add(formPanel);

        // Table Setup
        String[] columnNames = {
            "Employee ID", "First Name", "Last Name",
            "SSS Number", "Philhealth Number", "TIN Number", "Pagibig Number"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        add(tableScrollPane, gbc);

        // Button ActionListeners
        btnView.addActionListener(e -> viewEmployees());

        btnAdd.addActionListener(e -> {
            EmployeeForm employeeForm = new EmployeeForm();
            employeeForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    viewEmployees(); // Refresh on close
                }
            });
        });

        btnViewEmployee.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to view.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String empId = (String) tableModel.getValueAt(selectedRow, 0);
            new EmployeeViewer(this, empId);
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginUI(); // Launch login screen
                dispose(); // Close this window
               
            }
        });

        setVisible(true);
    }

    public void viewEmployees() {
        List<Employee> list = EmployeeCSVUtil.readEmployees();
        tableModel.setRowCount(0);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Employee e : list) {
            tableModel.addRow(new Object[]{
                e.getEmpId(), e.getFirstName(), e.getLastName(), e.getsssId(), e.getphilhealthId(),
                e.gettinId(), e.getpagibigId()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeUI::new);
    }
}