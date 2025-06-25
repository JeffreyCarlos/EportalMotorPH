/*
 * Click nfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

/**
 *
 * @author basil
 */
public class SalaryViewer extends JFrame {
    private JComboBox<Integer> yearDropdown;
    private JComboBox<String> empIdComboBox;
    private String selectedEmpId;

    public SalaryViewer(String empId) {
        this.selectedEmpId = empId;
        setTitle("Payroll Viewer");
        setSize(400, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for year and employee selection
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.add(new JLabel("Select Year:"));
        yearDropdown = new JComboBox<>();
        for (int i = 2022; i <= 2024; i++) {
            yearDropdown.addItem(i);
        }
        topPanel.add(yearDropdown);

        topPanel.add(new JLabel("Select Employee ID:"));
        empIdComboBox = new JComboBox<>();
        empIdComboBox.addItem("Select Employee ID");
        populateEmployeeIds();
        if (empId != null && !empId.isEmpty()) {
            empIdComboBox.setSelectedItem(empId);
        }
        topPanel.add(empIdComboBox);

        add(topPanel, BorderLayout.NORTH);

        // Panel for months
        JPanel monthsPanel = new JPanel(new GridLayout(4, 3));
        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};

        for (String month : months) {
            JButton monthButton = new JButton(month);
            monthButton.addActionListener(e -> viewPayrollDetails(month));
            monthsPanel.add(monthButton);
        }
        add(monthsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void populateEmployeeIds() {
        empIdComboBox.removeAllItems();
        empIdComboBox.addItem("Select Employee ID");
        List<Employee> employees = EmployeeCSVUtil.readEmployees();
        for (Employee e : employees) {
            empIdComboBox.addItem(e.getEmpId());
        }
    }

    private void viewPayrollDetails(String month) {
        String selectedId = (String) empIdComboBox.getSelectedItem();
        if (selectedId == null || selectedId.equals("Select Employee ID")) {
            JOptionPane.showMessageDialog(this, "Please select an Employee ID!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedYear = (int) yearDropdown.getSelectedItem();
        Employee employee = getEmployeeById(selectedId);
        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        double basicSalary = parseDouble(employee.getBasicSalary());
        double riceSubsidy = parseDouble(employee.getRicesubsidy());
        double phoneAllowance = parseDouble(employee.getPhoneAllowance());
        double clothingAllowance = parseDouble(employee.getClothingAllowance());

        // Base gross pay from CSV data
        double baseGrossPay = basicSalary + riceSubsidy + phoneAllowance + clothingAllowance;
        // Randomize gross pay within ±10%
        double grossPay = baseGrossPay * (1 + (random.nextDouble() - 0.5) * 0.2);
        // Random deductions with reasonable ranges
        double sssDeduction = basicSalary * (0.03 + random.nextDouble() * 0.02); // 3%–5%
        double philhealthDeduction = basicSalary * (0.02 + random.nextDouble() * 0.02); // 2%–4%
        double pagibigDeduction = 50 + random.nextDouble() * 100; // 50–150
        double taxableIncome = grossPay - (sssDeduction + philhealthDeduction + pagibigDeduction);
        double withholdingTax = taxableIncome * (0.05 + random.nextDouble() * 0.10); // 5%–15%
        double netPay = grossPay - (sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax);

        StringBuilder details = new StringBuilder();
        details.append("Payroll for ").append(employee.getFirstName()).append(" ").append(employee.getLastName())
               .append(" (ID: ").append(employee.getEmpId()).append(")\n")
               .append("Period: ").append(month).append(", ").append(selectedYear).append("\n\n")
               .append("Gross Pay: ₱").append(df.format(grossPay)).append("\n")
               .append("  Basic Salary: ₱").append(employee.getBasicSalary()).append("\n")
               .append("  Rice Subsidy: ₱").append(employee.getRicesubsidy()).append("\n")
               .append("  Phone Allowance: ₱").append(employee.getPhoneAllowance()).append("\n")
               .append("  Clothing Allowance: ₱").append(employee.getClothingAllowance()).append("\n\n")
               .append("Deductions:\n")
               .append("  SSS: ₱").append(df.format(sssDeduction)).append("\n")
               .append("  PhilHealth: ₱").append(df.format(philhealthDeduction)).append("\n")
               .append("  Pag-IBIG: ₱").append(df.format(pagibigDeduction)).append("\n")
               .append("  Withholding Tax: ₱").append(df.format(withholdingTax)).append("\n\n")
               .append("Total Deductions: ₱").append(df.format(sssDeduction + philhealthDeduction + pagibigDeduction + withholdingTax)).append("\n")
               .append("Net Pay: ₱").append(df.format(netPay));

        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Payroll Details", JOptionPane.INFORMATION_MESSAGE);
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

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}