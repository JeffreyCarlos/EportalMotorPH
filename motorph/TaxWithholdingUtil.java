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
public class TaxWithholdingUtil {
    private static final String FILE_PATH = "data/employee-withholding-tax.csv";

    public static double getTax(double salary) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                double rangeFrom = Double.parseDouble(parts[0]);
                double rangeTo = Double.parseDouble(parts[1]);
                double fixedAmount = Double.parseDouble(parts[2]);
                double percentOver = Double.parseDouble(parts[3].replace("%", "")) / 100.0;

                if (salary >= rangeFrom && salary <= rangeTo) {
                    double excess = salary - rangeFrom;
                    return fixedAmount + (excess * percentOver);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Tax lookup failed: " + e.getMessage());
        }
        return 0.0;
    }
}
