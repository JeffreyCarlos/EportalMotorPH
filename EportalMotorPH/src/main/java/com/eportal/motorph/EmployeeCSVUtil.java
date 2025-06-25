/*
 * Click nfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

import com.opencsv.*;
import java.io.*;
import java.util.*;
import com.opencsv.exceptions.CsvException;

/**
 *
 * @author basil
 */
public class EmployeeCSVUtil {
    private static final String CSV_FILE = "resources/employee-details.csv";

    public static List<Employee> readEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(CSV_FILE))
                .withSkipLines(1) // Skip header row
                .build()) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                employees.add(new Employee(line[0], line[1], line[2], line[3], line[4], line[5],
                                          line[6], line[7], line[8], line[9], line[10], line[11],
                                          line[12], line[13], line[14], line[15], line[16], line[17],
                                          line[18]));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void writeEmployees(List<Employee> employees) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            writer.writeNext(new String[] {
                "Employee ID", "First Name", "Last Name", "Birthday", "Address", "Phone Number",
                "SSS #", "Philhealth #", "TIN #", "Pagibig #", "Status", "Position", "Immediate Supervisor",
                "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Gross Semi-Monthly Rate", "Hourly Rate"
            });

            for (Employee e : employees) {
                writer.writeNext(new String[] {
                    e.getEmpId(), e.getFirstName(), e.getLastName(), e.getBirthday(), e.getAddress(),
                    e.getPhonenumber(), e.getsssId(), e.getphilhealthId(), e.gettinId(), e.getpagibigId(),
                    e.getStatus(), e.getPosition(), e.getImmediateSupervisor(), e.getBasicSalary(),
                    e.getRicesubsidy(), e.getPhoneAllowance(), e.getClothingAllowance(),
                    e.getGrossSemiMonthlyRate(), e.getHourlyRate()
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addEmployee(Employee e) {
        List<Employee> list = readEmployees();
        list.add(e);
        writeEmployees(list);
    }

    public static void updateEmployee(String empId, String newFirst, String newLast) {
        List<Employee> list = readEmployees();
        boolean found = false;
        for (Employee e : list) {
            if (e.getEmpId().equals(empId)) {
                e.setFirstName(newFirst);
                e.setLastName(newLast);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee ID not found during update!");
        }
        writeEmployees(list);
    }

    public static void deleteEmployee(String empId) {
        List<Employee> list = readEmployees();
        list.removeIf(e -> e.getEmpId().equals(empId));
        writeEmployees(list);
    }

    public static void processPayroll(String month, int year) {
        // No payroll CSV file, this method can remain empty or be removed if not needed
    }
}