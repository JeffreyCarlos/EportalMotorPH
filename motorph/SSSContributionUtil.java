/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author HP
 */
public class SSSContributionUtil {
    private static final String FILE_PATH = "data/sss-contribution-schedule.csv"; // adjust path as needed

    public static double getDeduction(double salary) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double rangeFrom = Double.parseDouble(parts[0]);
                double rangeTo = Double.parseDouble(parts[1]);
                double employeeShare = Double.parseDouble(parts[3]);

                if (salary >= rangeFrom && salary <= rangeTo) {
                    return employeeShare;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("SSS Lookup Failed: " + e.getMessage());
        }
        return 0.0; // Default if not found
    }
}
