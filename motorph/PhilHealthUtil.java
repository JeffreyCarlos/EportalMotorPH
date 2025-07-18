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
public class PhilHealthUtil {
    private static final String FILE_PATH = "data/employee-philhealth-contributions.csv";

    public static double getDeduction(double salary) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double rangeFrom = Double.parseDouble(parts[0]);
                double rangeTo = Double.parseDouble(parts[1]);
                double contributionRate = Double.parseDouble(parts[2]); // e.g., 0.035 for 3.5%

                if (salary >= rangeFrom && salary <= rangeTo) {
                    double totalContribution = salary * contributionRate;
                    return totalContribution / 2; // Employee share (usually 50/50)
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("PhilHealth lookup failed: " + e.getMessage());
        }
        return 0.0;
    }
}

