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
    private JTextField txtId, txtFirst, txtLast;

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton btnView = new JButton("View All");
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnViewSalary = new JButton("ViewSalary");

        buttonPanel.add(btnView);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewSalary);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel);

       // Setup JTable
    String[] columnNames = {
          "Emp ID", "First Name", "Last Name", "Birthday", "Address", "Phone Number",
          "SSS #", "Philhealth #", "TIN #", "Pagibig #", "Status", "Position", "Immediate Supervisor",
          "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-Monthly Rate", "Hourly Rate"
};

    // Override DefaultTableModel to prevent editing
        tableModel = new DefaultTableModel(columnNames, 0) {
         @Override
public boolean isCellEditable(int row, int column) {
        return false; // Prevent editing in the table
    }
};

employeeTable = new JTable(tableModel);
JScrollPane tableScrollPane = new JScrollPane(employeeTable);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        add(tableScrollPane, gbc);

        // Button Actions
        btnView.addActionListener(e -> viewEmployees());
        
        btnViewSalary.addActionListener(e -> new SalaryViewer());
        
        
        btnAdd.addActionListener(e -> {
            EmployeeForm employeeForm = new EmployeeForm();
            employeeForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    viewEmployees(); // Refresh the table after closing the form
                }
            });
            
        });

        // **Updated "Update" Button**
        btnUpdate.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update!", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String empId = (String) tableModel.getValueAt(selectedRow, 0); // Get Employee ID
            new UpdateEmployeeForm(this, empId); // Pass EmployeeUI reference to refresh table
        });

        btnDelete.addActionListener(e -> deleteEmployee());


            
        
        
        setVisible(true);
        
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    public void viewEmployees() {
        List<Employee> list = EmployeeCSVUtil.readEmployees();
        tableModel.setRowCount(0); // Clear previous data

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Employee e : list) {
            tableModel.addRow(new Object[]{
                e.getEmpId(), e.getFirstName(), e.getLastName(), e.getBirthday(),
                e.getAddress(), e.getPhonenumber(), e.getsssId(), e.getphilhealthId(),
                e.gettinId(), e.getpagibigId(), e.getStatus(), e.getPosition(),
                e.getImmediateSupervisor(), e.getBasicSalary(), e.getRicesubsidy(),
                e.getPhoneAllowance(), e.getClothingAllowance(), e.getGrossSemiMonthlyRate(),
                e.getHourlyRate()
            });
            
            
        }
    }
    
    private void deleteEmployee() {
    if (txtId.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Error: Employee ID is required for deletion!", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Employee> employees = EmployeeCSVUtil.readEmployees();
    boolean removed = employees.removeIf(e -> e.getEmpId().equals(txtId.getText()));

    if (removed) {
        EmployeeCSVUtil.writeEmployees(employees);
        JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
        viewEmployees(); // Refresh the table
    } else {
        JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeUI::new);
    }
}
