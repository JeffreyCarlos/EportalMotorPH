
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.eportal.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 *
 * @author jeffr
 */

public class EmployeeViewer extends JFrame {
    private JTextField empIdField;
    private JTable detailTable;
    private DefaultTableModel tableModel;
    private JButton updateButton, deleteButton, viewSalaryButton, generatePayrollButton;

    private EmployeeUI dashboardRef;

    public EmployeeViewer(EmployeeUI dashboardRef, String empId) {
        this.dashboardRef = dashboardRef;
        initializeUI();
        empIdField.setText(empId);
        searchEmployee();
    }

    public EmployeeViewer() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Employee Viewer");
        setSize(400, 650);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter Employee ID:"));
        empIdField = new JTextField(10);
        topPanel.add(empIdField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchEmployee());
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Field", "Value"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        detailTable = new JTable(tableModel);
        detailTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(detailTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete This Employee");
        viewSalaryButton = new JButton("View Salary");
        generatePayrollButton = new JButton("Generate Payroll");
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        viewSalaryButton.setEnabled(false);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(generatePayrollButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        updateButton.addActionListener(e -> {
            String empId = empIdField.getText().trim();
            if (!empId.isEmpty()) {
                new UpdateEmployeeForm(dashboardRef, this, empId);
            }
        });

        deleteButton.addActionListener(e -> {
            String empId = empIdField.getText().trim();
            if (empId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an Employee ID to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<Employee> employees = EmployeeCSVUtil.readEmployees();
                boolean removed = employees.removeIf(emp -> emp.getEmpId().equals(empId));
                if (removed) {
                    EmployeeCSVUtil.writeEmployees(employees);
                    tableModel.setRowCount(0);
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    viewSalaryButton.setEnabled(false);
                    if (dashboardRef != null) {
                        dashboardRef.viewEmployees();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Employee not found.");
                }
            }
        });

        generatePayrollButton.addActionListener(e -> {
    String empId = empIdField.getText().trim();
    if (empId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
        return;
    }

    final JDialog loading = new JDialog(this, "Processing", true);
    JLabel loadingLabel = new JLabel("Calculating...", SwingConstants.CENTER);
    loading.add(loadingLabel);
    loading.setSize(200, 100);
    loading.setLocationRelativeTo(this);

    // Create a timer to delay the computation by 2 seconds
    Timer delayTimer = new Timer(2000, evt -> {
        loading.dispose(); // Close the "Calculating..." dialog

        // Now compute and show the payslip
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee emp : employees) {
            if (emp.getEmpId().equals(empId)) {
                PayrollEntry entry = PayrollCalculator.computePayroll(emp);
                showPayslip(emp, entry);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Employee not found.");
    });
    delayTimer.setRepeats(false);
    delayTimer.start();

    loading.setVisible(true); // This will block until loading.dispose() is called
});

        setVisible(true);
    }

    private void searchEmployee() {
        String empId = empIdField.getText().trim();
        tableModel.setRowCount(0);
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
            return;
        }

        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            if (e.getEmpId().equals(empId)) {
                addRow("Employee ID", e.getEmpId());
                addRow("First Name", e.getFirstName());
                addRow("Last Name", e.getLastName());
                addRow("Birthday", e.getBirthday());
                addRow("Address", e.getAddress());
                addRow("Phone Number", e.getPhonenumber());
                addRow("SSS #", e.getsssId());
                addRow("PhilHealth #", e.getphilhealthId());
                addRow("TIN #", e.gettinId());
                addRow("Pag-ibig #", e.getpagibigId());
                addRow("Status", e.getStatus());
                addRow("Position", e.getPosition());
                addRow("Supervisor", e.getImmediateSupervisor());
                addRow("Basic Salary", e.getBasicSalary());
                addRow("Rice Subsidy", e.getRicesubsidy());
                addRow("Phone Allowance", e.getPhoneAllowance());
                addRow("Clothing Allowance", e.getClothingAllowance());
                addRow("Gross Semi-Monthly Rate", e.getGrossSemiMonthlyRate());
                addRow("Hourly Rate", e.getHourlyRate());

                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
                viewSalaryButton.setEnabled(true);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Employee not found.");
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        viewSalaryButton.setEnabled(false);
    }

    private void addRow(String label, String value) {
        tableModel.addRow(new Object[]{label, value});
    }

    public void refreshDetails() {
        searchEmployee();
        
    }
    private double parseAmount(String value) {
    try {
        return Double.parseDouble(value.replace(",", "").trim());
    } catch (NumberFormatException e) {
        return 0;
    }
}


    private void showPayslip(Employee emp, PayrollEntry entry) {
        String payslip = String.format(
            "Payslip for %s - %s\n\n" +
            "BASIC SALARY & ALLOWANCES\n" +
            "-----------------------------\n" +
            "Basic Salary      : ₱%,.2f\n" +
            "Rice Allowance    : ₱%,.2f\n" +
            "Phone Allowance   : ₱%,.2f\n" +
            "Clothing Allowance: ₱%,.2f\n" +
            "-----------------------------\n" +
            "GROSS PAY         : ₱%,.2f\n\n" +
            "DEDUCTIONS\n" +
            "-----------------------------\n" +
            "SSS Contribution  : ₱%,.2f\n" +
            "PhilHealth        : ₱%,.2f\n" +
            "Withholding Tax   : ₱%,.2f\n" +
            "-----------------------------\n" +
            "TOTAL DEDUCTIONS  : ₱%,.2f\n\n" +
            "NET PAY           : ₱%,.2f",
            entry.empId, entry.fullName,
            entry.basicSalary,
            parseAmount(emp.getRicesubsidy()),
            parseAmount(emp.getPhoneAllowance()),
            parseAmount(emp.getClothingAllowance()),
            entry.grossPay,
            entry.sssDeduction,
            entry.philhealthDeduction,
            entry.taxDeduction,
            entry.totalDeductions,
            entry.netPay
        );

        JTextArea textArea = new JTextArea(payslip);
        textArea.setEditable(false);
        JScrollPane payslipScroll = new JScrollPane(textArea);
        payslipScroll.setPreferredSize(new Dimension(350, 400));
        JOptionPane.showMessageDialog(this, payslipScroll, "Employee Payslip", JOptionPane.INFORMATION_MESSAGE);

        int saveOption = JOptionPane.showConfirmDialog(this, "Do you want to save this payslip to a file?", "Save Payslip", JOptionPane.YES_NO_OPTION);
        if (saveOption == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("Payslip_" + emp.getEmpId() + ".txt"));
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
        

                
            }
        }
    }
}

