/*
 * Click nb://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginui;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error starting application: " + e.getMessage());
            System.exit(1);
        }
    }
}