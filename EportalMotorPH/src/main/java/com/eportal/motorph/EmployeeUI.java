/*
 * Click nfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author basil
 */
public class EmployeeUI extends JFrame {
    private JComboBox<String> empIdComboBox;
    private JTextField txtFirst, txtLast;
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
        empIdComboBox = new JComboBox<>();
        empIdComboBox.addItem("Select Employee ID");
        populateEmployeeIds();
        addComponent(formPanel, gbc, empIdComboBox, 1, 0);

        addComponent(formPanel, gbc, new JLabel("First Name:"), 0, 1);
        txtFirst = new JTextField(15);
        addComponent(formPanel, gbc, txtFirst, 1, 1);

        addComponent(formPanel, gbc, new JLabel("Last Name:"), 0, 2);
        txtLast = new JTextField(15);
        addComponent(formPanel, gbc, txtLast, 1, 2);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 8, 10, 10));
        JButton btnViewAll = new JButton("View All");
        JButton btnView = new JButton("View Employee");
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnViewSalary = new JButton("View Salary");
        JButton btnViewPayroll = new JButton("View Payroll");
        JButton btnProcessPayroll = new JButton("Process Payroll");
        JButton btnLogout = new JButton("Logout");
        
        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnView);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewSalary);
        buttonPanel.add(btnViewPayroll);
        buttonPanel.add(btnProcessPayroll);
        buttonPanel.add(btnLogout);
        
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

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        btnViewAll.addActionListener(e -> viewEmployees());
        
        btnView.addActionListener(e -> viewSpecificEmployee());
        
        btnViewSalary.addActionListener(e -> {
            String selectedId = (String) empIdComboBox.getSelectedItem();
            if (selectedId == null || selectedId.equals("Select Employee ID")) {
                new SalaryViewer(null);
            } else {
                new SalaryViewer(selectedId);
            }
        });
        
        btnViewPayroll.addActionListener(e -> {
            String selectedId = (String) empIdComboBox.getSelectedItem();
            if (selectedId == null || selectedId.equals("Select Employee ID")) {
                new SalaryViewer(null);
            } else {
                new SalaryViewer(selectedId);
            }
        });
        
        btnAdd.addActionListener(e -> {
            EmployeeForm employeeForm = new EmployeeForm();
            employeeForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    viewEmployees();
                    populateEmployeeIds();
                }
            });
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            String selectedId = (String) empIdComboBox.getSelectedItem();
            
            if (selectedRow == -1 && (selectedId == null || selectedId.equals("Select Employee ID"))) {
                JOptionPane.showMessageDialog(this, "Please select an employee from the table or dropdown!", "Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String empId = selectedRow != -1 ? (String) tableModel.getValueAt(selectedRow, 0) : selectedId;
            UpdateEmployeeForm updateForm = new UpdateEmployeeForm(this, empId);
            updateForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    viewEmployees();
                    populateEmployeeIds();
                }
            });
        });

        btnDelete.addActionListener(e -> deleteEmployee());
        
        btnProcessPayroll.addActionListener(e -> processPayroll());
        
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginUI();
            }
        });
        
        setVisible(true);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, JComponent component, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    private void populateEmployeeIds() {
        empIdComboBox.removeAllItems();
        empIdComboBox.addItem("Select Employee ID");
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            empIdComboBox.addItem(e.getEmpId());
        }
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
                e.getEmpId(), e.getFirstName(), e.getLastName(), e.getBirthday(),
                e.getAddress(), e.getPhonenumber(), e.getsssId(), e.getphilhealthId(),
                e.gettinId(), e.getpagibigId(), e.getStatus(), e.getPosition(),
                e.getImmediateSupervisor(), e.getBasicSalary(), e.getRicesubsidy(),
                e.getPhoneAllowance(), e.getClothingAllowance(), e.getGrossSemiMonthlyRate(),
                e.getHourlyRate()
            });
        }
        populateEmployeeIds();
    }
    
    private void viewSpecificEmployee() {
        String selectedId = (String) empIdComboBox.getSelectedItem();
        if (selectedId == null || selectedId.equals("Select Employee ID")) {
            JOptionPane.showMessageDialog(this, "Please select an Employee ID!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee employee = getEmployeeById(selectedId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Employee ID* ").append(employee.getEmpId()).append("\n")
               .append("First Name* ").append(employee.getFirstName()).append("\n")
               .append("Last Name* ").append(employee.getLastName()).append("\n")
               .append("Birthday* ").append(employee.getBirthday()).append("\n")
               .append("Address* ").append(employee.getAddress()).append("\n")
               .append("Phone Number: ").append(employee.getPhonenumber()).append("\n")
               .append("SSS #: ").append(employee.getsssId()).append("\n")
               .append("Philhealth #: ").append(employee.getphilhealthId()).append("\n")
               .append("TIN #: ").append(employee.gettinId()).append("\n")
               .append("Pagibig #: ").append(employee.getpagibigId()).append("\n")
               .append("Status: ").append(employee.getStatus()).append("\n")
               .append("Position: ").append(employee.getPosition()).append("\n")
               .append("Immediate Supervisor: ").append(employee.getImmediateSupervisor()).append("\n")
               .append("Basic Salary: ").append(employee.getBasicSalary()).append("\n")
               .append("Rice Subsidy: ").append(employee.getRicesubsidy()).append("\n")
               .append("Phone Allowance: ").append(employee.getPhoneAllowance()).append("\n")
               .append("Clothing Allowance: ").append(employee.getClothingAllowance()).append("\n")
               .append("Gross Semi-Monthly Rate: ").append(employee.getGrossSemiMonthlyRate()).append("\n")
               .append("Hourly Rate: ").append(employee.getHourlyRate());

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void processPayroll() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthComboBox = new JComboBox<>(months);
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        Random random = new Random();
        int randomYear = random.nextInt(3) + 2022; // Random year between 2022 and 2024
        for (int i = 2022; i <= 2024; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setSelectedItem(randomYear);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Select Month:"));
        panel.add(monthComboBox);
        panel.add(new JLabel("Select Year:"));
        panel.add(yearComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Process Payroll", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String month = (String) monthComboBox.getSelectedItem();
            int year = (Integer) yearComboBox.getSelectedItem();
            EmployeeCSVUtil.processPayroll(month, year);
            JOptionPane.showMessageDialog(this, "Payroll processed successfully for " + month + ", " + year + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Employee getEmployeeById(String empId) {
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            if (e.getEmpId().equals(empId)) {
                return e;
            }
        }
        return null;
    }

    private void deleteEmployee() {
        String selectedId = (String) empIdComboBox.getSelectedItem();
        if (selectedId == null || selectedId.equals("Select Employee ID")) {
            JOptionPane.showMessageDialog(this, "Please select an Employee ID!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        boolean removed = employees.removeIf(e -> e.getEmpId().equals(selectedId));

        if (removed) {
            EmployeeCSVUtil.writeEmployees(employees);
            JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
            viewEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeUI::new);
    }
}