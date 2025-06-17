/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
/**
 *
 * @author jeffr
 */
public class SalaryViewer extends JFrame {
    
    private JComboBox<Integer> yearDropdown;

    public SalaryViewer() {
        setTitle("View Salary");
        setSize(400, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel for year selection
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Year:"));

        yearDropdown = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 10; i <= currentYear + 10; i++) { // Allow 5 years back & forward
            yearDropdown.addItem(i);
        }
        topPanel.add(yearDropdown);
        add(topPanel, BorderLayout.NORTH);

        // Panel for months
        JPanel monthsPanel = new JPanel(new GridLayout(4, 3));
        String[] months = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};

        for (String month : months) {
            JButton monthButton = new JButton(month);
            monthButton.addActionListener(e -> viewSalaryDetails(month));
            monthsPanel.add(monthButton);
        }
        add(monthsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void viewSalaryDetails(String month) {
        int selectedYear = (int) yearDropdown.getSelectedItem();
        JOptionPane.showMessageDialog(this, "Showing salary details for " + month + ", " + selectedYear);
        // Future expansion: Fetch and display actual salary data
    }
}
