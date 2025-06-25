/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author jeffr
 */
public class LogInCSVUtil {
    
    private static final String CSV_FILE = "resources/logins.csv";

    public static boolean validateCredentials(String username, String password) {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String[]> records = reader.readAll(); // Read all CSV records at once
            for (String[] line : records) {
                if (line.length >= 2 && line[0].equals(username) && line[1].equals(password)) {
                    return true; // Credentials match
                }
            }
        } catch (IOException | CsvException e) { // Catch correct exception type
            e.printStackTrace(); // Log error for debugging
        }
        return false; // No match found
    }
}
