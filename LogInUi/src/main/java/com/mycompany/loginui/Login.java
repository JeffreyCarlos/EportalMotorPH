/*
 * Click nb://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;

    public Login() {
        setTitle("MotorPH - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("MotorPH Employee Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> verifyLogin());
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        add(panel);
    }

    private void verifyLogin() {
        String enteredUser = usernameField.getText().trim();
        String enteredPass = String.valueOf(passwordField.getPassword()).trim();

        if (enteredUser.isEmpty() || enteredPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        // Hardcoded credentials
        Map<String, String> credentials = new HashMap<>();
        credentials.put("Admin", "PasswordAd");
        credentials.put("Manager", "PasswordMa");
        credentials.put("Employee10001", "10001");

        if (credentials.containsKey(enteredUser) && credentials.get(enteredUser).equals(enteredPass)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
            SwingUtilities.invokeLater(() -> new Dashboard(enteredUser).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
        });
    }
    
}