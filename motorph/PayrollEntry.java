/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

/**
 *
 * @author HP
 */
public class PayrollEntry {
    public String empId, fullName;
    public double basicSalary, grossPay;
    public double sssDeduction, philhealthDeduction, taxDeduction;
    public double totalDeductions, netPay;

    public PayrollEntry(String empId, String fullName,
                        double basicSalary, double grossPay,
                        double sssDeduction, double philhealthDeduction, double taxDeduction,
                        double totalDeductions, double netPay) {
        this.empId = empId;
        this.fullName = fullName;
        this.basicSalary = basicSalary;
        this.grossPay = grossPay;
        this.sssDeduction = sssDeduction;
        this.philhealthDeduction = philhealthDeduction;
        this.taxDeduction = taxDeduction;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
    }
}