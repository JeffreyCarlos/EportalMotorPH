
/*
 * Click nb://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {
    private String username;
    private JTabbedPane tabbedPane;
    private List<Employee> employees;

    public Dashboard(String username) {
        this.username = username;
        initUI();
    }

    private void initUI() {
        setTitle("MotorPH Dashboard - " + username);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Hardcode Employee10001 data
        employees = new ArrayList<>();
        employees.add(new Employee("Employee10001", "Garcia, Manuel III", "10/11/1983",
                "Valero Carpark Building Valero Street 1227, Makati City", "966-860-270", "44-4506057-3",
                "820126853951", "442-605-657-000", "691295330870", "Regular", "Chief Executive Officer", "N/A",
                90000.0, 1500.0, 2000.0, 1000.0, 45000.0, 0.0, 45000.0, 535.71));

        tabbedPane = new JTabbedPane();
        setupTabsBasedOnRole();

        add(tabbedPane);
        setVisible(true);
    }

    private void setupTabsBasedOnRole() {
        if (username.equals("Admin")) {
            tabbedPane.addTab("View Employees", createEmployeeViewPanel());
            tabbedPane.addTab("Manage Employees", createEmployeeManagePanel());
        } else if (username.equals("Manager")) {
            tabbedPane.addTab("View Salaries", createSalaryViewPanel());
        } else if (username.equals("Employee10001")) {
            tabbedPane.addTab("My Details", createEmployeeDetailsPanel());
            tabbedPane.addTab("My Salary", createEmployeeSalaryPanel());
        }
        tabbedPane.addTab("Logout", createLogoutPanel());
    }

    private JPanel createEmployeeViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Position", "Gross Pay", "Deductions", "Net Pay"}, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Employee emp : employees) {
            model.addRow(new Object[]{emp.id, emp.name, emp.position, String.format("%.2f", emp.grossPay),
                    String.format("%.2f", emp.deductions), String.format("%.2f", emp.netPay)});
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEmployeeManagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Employee ID:");
        JTextField idField = new JTextField(15);
        JLabel nameLabel = new JLabel("Name (Last, First):");
        JTextField nameField = new JTextField(15);
        JLabel positionLabel = new JLabel("Position:");
        JTextField positionField = new JTextField(15);
        JLabel grossPayLabel = new JLabel("Gross Pay (Semi-monthly):");
        JTextField grossPayField = new JTextField(15);
        JLabel deductionsLabel = new JLabel("Deductions:");
        JTextField deductionsField = new JTextField(15);
        deductionsField.setText("0.00");
        deductionsField.setEditable(false);
        JLabel netPayLabel = new JLabel("Net Pay:");
        JTextField netPayField = new JTextField(15);
        netPayField.setEditable(false);

        JButton addButton = new JButton("Add Employee");
        JButton updateButton = new JButton("Update Employee");
        JButton deleteButton = new JButton("Delete Employee");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(positionLabel, gbc);
        gbc.gridx = 1;
        panel.add(positionField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(grossPayLabel, gbc);
        gbc.gridx = 1;
        panel.add(grossPayField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(deductionsLabel, gbc);
        gbc.gridx = 1;
        panel.add(deductionsField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(netPayLabel, gbc);
        gbc.gridx = 1;
        panel.add(netPayField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, gbc);

        grossPayField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateNetPay(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateNetPay(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateNetPay(); }
            private void updateNetPay() {
                try {
                    double grossPay = Double.parseDouble(grossPayField.getText().trim());
                    netPayField.setText(String.format("%.2f", grossPay));
                } catch (NumberFormatException ex) {
                    netPayField.setText("0.00");
                }
            }
        });

        addButton.addActionListener(addEvent -> {
            String id = idField.getText().trim();
            if (id.isEmpty() || employees.stream().anyMatch(emp -> emp.id.equals(id))) {
                JOptionPane.showMessageDialog(this, id.isEmpty() ? "Employee ID is required." : "Employee ID already exists.");
                return;
            }
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required.");
                return;
            }
            String position = positionField.getText().trim();
            double grossPay = parseDoubleSafe(grossPayField.getText());
            double deductions = 0.0;
            double netPay = grossPay;
            employees.add(new Employee(id, name, position.isEmpty() ? "N/A" : position, "", "", "", "", "", "", "", position, "", 0.0, 0.0, 0.0, 0.0, grossPay, deductions, netPay, 0.0));
            JOptionPane.showMessageDialog(this, "Employee added (in-memory only).");
            refreshEmployeeView();
            clearFields(idField, nameField, positionField, grossPayField);
        });

        updateButton.addActionListener(updateEvent -> {
            String id = idField.getText().trim();
            Employee emp = employees.stream().filter(e -> e.id.equals(id)).findFirst().orElse(null);
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                return;
            }
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required.");
                return;
            }
            String position = positionField.getText().trim();
            double grossPay = parseDoubleSafe(grossPayField.getText());
            double deductions = 0.0;
            double netPay = grossPay;
            emp.name = name;
            emp.position = position.isEmpty() ? "N/A" : position;
            emp.grossPay = grossPay;
            emp.deductions = deductions;
            emp.netPay = netPay;
            JOptionPane.showMessageDialog(this, "Employee updated (in-memory only).");
            refreshEmployeeView();
            clearFields(idField, nameField, positionField, grossPayField);
        });

        deleteButton.addActionListener(deleteEvent -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee ID is required.");
                return;
            }
            Employee emp = employees.stream().filter(e -> e.id.equals(id)).findFirst().orElse(null);
            if (emp != null && employees.remove(emp)) {
                JOptionPane.showMessageDialog(this, "Employee deleted (in-memory only).");
                refreshEmployeeView();
                clearFields(idField, nameField, positionField, grossPayField);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        });

        return panel;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private JPanel createSalaryViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Position", "Gross Pay", "Deductions", "Net Pay"}, 0);
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        for (Employee emp : employees) {
            model.addRow(new Object[]{emp.id, emp.name, emp.position, String.format("%.2f", emp.grossPay),
                    String.format("%.2f", emp.deductions), String.format("%.2f", emp.netPay)});
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEmployeeDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Hardcode Employee10001 details
        Employee emp = employees.stream().filter(e -> e.id.equals("Employee10001")).findFirst().orElse(null);
        if (emp == null) {
            JLabel label = new JLabel("No data found for Employee ID: Employee10001");
            panel.add(label, gbc);
            return panel;
        }

        JLabel idLabel = new JLabel("Employee ID: " + emp.id);
        JLabel nameLabel = new JLabel("Name: " + emp.name);
        JLabel birthdayLabel = new JLabel("Birthday: " + emp.birthday);
        JLabel addressLabel = new JLabel("Address: " + emp.address);
        JLabel phoneLabel = new JLabel("Phone Number: " + emp.phoneNumber);
        JLabel sssLabel = new JLabel("SSS #: " + emp.sssNumber);
        JLabel philhealthLabel = new JLabel("Philhealth #: " + emp.philhealthNumber);
        JLabel tinLabel = new JLabel("TIN #: " + emp.tinNumber);
        JLabel pagibigLabel = new JLabel("Pag-ibig #: " + emp.pagibigNumber);
        JLabel statusLabel = new JLabel("Status: " + emp.status);
        JLabel positionLabel = new JLabel("Position: " + emp.position);
        JLabel supervisorLabel = new JLabel("Immediate Supervisor: " + emp.supervisor);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridy = 2;
        panel.add(birthdayLabel, gbc);
        gbc.gridy = 3;
        panel.add(addressLabel, gbc);
        gbc.gridy = 4;
        panel.add(phoneLabel, gbc);
        gbc.gridy = 5;
        panel.add(sssLabel, gbc);
        gbc.gridy = 6;
        panel.add(philhealthLabel, gbc);
        gbc.gridy = 7;
        panel.add(tinLabel, gbc);
        gbc.gridy = 8;
        panel.add(pagibigLabel, gbc);
        gbc.gridy = 9;
        panel.add(statusLabel, gbc);
        gbc.gridy = 10;
        panel.add(positionLabel, gbc);
        gbc.gridy = 11;
        panel.add(supervisorLabel, gbc);

        return panel;
    }

    private JPanel createEmployeeSalaryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Hardcode Employee10001 salary
        Employee emp = employees.stream().filter(e -> e.id.equals("Employee10001")).findFirst().orElse(null);
        if (emp == null) {
            JLabel label = new JLabel("No salary data found for Employee ID: Employee10001");
            panel.add(label, gbc);
            return panel;
        }

        JLabel basicSalaryLabel = new JLabel("Basic Salary: " + String.format("%.2f", emp.basicSalary));
        JLabel riceSubsidyLabel = new JLabel("Rice Subsidy: " + String.format("%.2f", emp.riceSubsidy));
        JLabel phoneAllowanceLabel = new JLabel("Phone Allowance: " + String.format("%.2f", emp.phoneAllowance));
        JLabel clothingAllowanceLabel = new JLabel("Clothing Allowance: " + String.format("%.2f", emp.clothingAllowance));
        JLabel grossPayLabel = new JLabel("Gross Pay (Semi-monthly): " + String.format("%.2f", emp.grossPay));
        JLabel deductionsLabel = new JLabel("Deductions: " + String.format("%.2f", emp.deductions));
        JLabel netPayLabel = new JLabel("Net Pay: " + String.format("%.2f", emp.netPay));
        JLabel hourlyRateLabel = new JLabel("Hourly Rate: " + String.format("%.2f", emp.hourlyRate));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(basicSalaryLabel, gbc);
        gbc.gridy = 1;
        panel.add(riceSubsidyLabel, gbc);
        gbc.gridy = 2;
        panel.add(phoneAllowanceLabel, gbc);
        gbc.gridy = 3;
        panel.add(clothingAllowanceLabel, gbc);
        gbc.gridy = 4;
        panel.add(grossPayLabel, gbc);
        gbc.gridy = 5;
        panel.add(deductionsLabel, gbc);
        gbc.gridy = 6;
        panel.add(netPayLabel, gbc);
        gbc.gridy = 7;
        panel.add(hourlyRateLabel, gbc);

        return panel;
    }

    private JPanel createLogoutPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
            });
        });
        panel.add(logoutButton);
        return panel;
    }

    private void refreshEmployeeView() {
        if (username.equals("Admin") && tabbedPane.getTabCount() > 0) {
            tabbedPane.setComponentAt(0, createEmployeeViewPanel());
            tabbedPane.revalidate();
            tabbedPane.repaint();
        }
    }

    private double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static class Employee {
        public String id;
        public String name;
        public String birthday;
        public String address;
        public String phoneNumber;
        public String sssNumber;
        public String philhealthNumber;
        public String tinNumber;
        public String pagibigNumber;
        public String status;
        public String position;
        public String supervisor;
        public double basicSalary;
        public double riceSubsidy;
        public double phoneAllowance;
        public double clothingAllowance;
        public double grossPay;
        public double deductions;
        public double netPay;
        public double hourlyRate;

        public Employee(String id, String name, String birthday, String address, String phoneNumber, String sssNumber,
                        String philhealthNumber, String tinNumber, String pagibigNumber, String status, String position,
                        String supervisor, double basicSalary, double riceSubsidy, double phoneAllowance,
                        double clothingAllowance, double grossPay, double deductions, double netPay, double hourlyRate) {
            this.id = id;
            this.name = name;
            this.birthday = birthday;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.sssNumber = sssNumber;
            this.philhealthNumber = philhealthNumber;
            this.tinNumber = tinNumber;
            this.pagibigNumber = pagibigNumber;
            this.status = status;
            this.position = position;
            this.supervisor = supervisor;
            this.basicSalary = basicSalary;
            this.riceSubsidy = riceSubsidy;
            this.phoneAllowance = phoneAllowance;
            this.clothingAllowance = clothingAllowance;
            this.grossPay = grossPay;
            this.deductions = deductions;
            this.netPay = netPay;
            this.hourlyRate = hourlyRate;
        }
    }
}
