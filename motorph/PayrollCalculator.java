/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;


import java.io.*;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException; 

/**
 *
 * @author HP
 */
public class PayrollCalculator {

    public static PayrollEntry computePayroll(Employee employee) {
    double basicSalary = parseAmount(employee.getBasicSalary());
    double riceAllowance = parseAmount(employee.getRicesubsidy());
    double phoneAllowance = parseAmount(employee.getPhoneAllowance());
    double clothingAllowance = parseAmount(employee.getClothingAllowance());

    double grossPay = basicSalary + riceAllowance + phoneAllowance + clothingAllowance;

    double sssDeduction = getSSSDeduction(basicSalary);
    double philhealthDeduction = getPhilHealthDeduction(basicSalary);
    double taxDeduction = getTaxDeduction(basicSalary);

    double totalDeductions = sssDeduction + philhealthDeduction + taxDeduction;
    double netPay = grossPay - totalDeductions;

    return new PayrollEntry(
        employee.getEmpId(),
        employee.getFirstName() + " " + employee.getLastName(),
        basicSalary,
        grossPay,
        sssDeduction,
        philhealthDeduction,
        taxDeduction,
        totalDeductions,
        netPay
    );

}

    private static double getSSSDeduction(double salary) {
        try (CSVReader reader = new CSVReader(new FileReader("resources/sss-contribution-schedule.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                double rangeFrom = Double.parseDouble(row[0].replace(",", "").trim());
                double rangeTo = Double.parseDouble(row[1].replace(",", "").trim());
                if (salary >= rangeFrom && salary <= rangeTo) {
                    return Double.parseDouble(row[2].replace(",", "").trim());
                }
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static double getPhilHealthDeduction(double salary) {
        try (CSVReader reader = new CSVReader(new FileReader("resources/employee-philhealth-contributions.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                double base = Double.parseDouble(row[0].replace(",", "").trim());
                if (salary == base) {
                    return Double.parseDouble(row[1].replace(",", "").trim());
                }
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static double getTaxDeduction(double salary) {
        try (CSVReader reader = new CSVReader(new FileReader("resources/employee-withholding-tax.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                double rangeFrom = Double.parseDouble(row[0].replace(",", "").trim());
                double rangeTo = Double.parseDouble(row[1].replace(",", "").trim());
                if (salary >= rangeFrom && salary <= rangeTo) {
                    return Double.parseDouble(row[2].replace(",", "").trim());
                }
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static double parseAmount(String amount) {
        try {
            return Double.parseDouble(amount.replace(",", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
