/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eportal.motorph;

/**
 *
 * @author HP
 */
public class Employee {
    private String empId;
    private String lastName;
    private String firstName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String sssId;
    private String philhealthId;
    private String tinId;
    private String pagibigId;
    private String status;
    private String position;
    private String immediateSupervisor;
    private String basicSalary;
    private String riceSubsidy;
    private String phoneAllowance;
    private String clothingAllowance;
    private String grossSemimonthlyrate;
    private String hourlyRate;

    public Employee(String empId, String firstName, String lastName, String birthday,
                    String address, String phoneNumber, String sssId, String philhealthId,
                    String tinId, String pagibigId, String status, String position,
                    String immediateSupervisor, String basicSalary, String riceSubsidy,
                    String phoneAllowance, String clothingAllowance, String grossSemimonthlyrate,
                    String hourlyRate )          {
        
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssId = sssId;
        this.philhealthId = philhealthId;
        this.tinId = tinId;
        this.pagibigId = pagibigId;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemimonthlyrate = grossSemimonthlyrate;
        this.hourlyRate = hourlyRate;
        
    }
    
//Getters

    public String getEmpId() { return empId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthday() { return birthday; }
    public String getAddress() { return address; }
    public String getPhonenumber() { return phoneNumber;}
    public String getsssId() { return sssId; }
    public String getphilhealthId() {return philhealthId; }
    public String gettinId() {return tinId; }
    public String getpagibigId() {return pagibigId; }
    public String getStatus() { return status; }
    public String getPosition() { return position ; }
    public String getImmediateSupervisor() { return immediateSupervisor; }
    public String getBasicSalary() { return basicSalary; }
    public String getRicesubsidy() { return riceSubsidy; }
    public String getPhoneAllowance() { return phoneAllowance; }
    public String getClothingAllowance() { return clothingAllowance; }
    public String getGrossSemiMonthlyRate() { return grossSemimonthlyrate; }
    public String getHourlyRate() { return hourlyRate; }    

    
    //Setters
    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setSssId(String sssId) { this.sssId = sssId; }
    public void setPhilhealthId(String philhealthId) { this.philhealthId = philhealthId; }
    public void setTinId(String tinId) { this.tinId = tinId; }
    public void setPagibigId(String pagibigId) { this.pagibigId = pagibigId; }
    public void setStatus(String status) { this.status = status; }
    public void setPosition(String position) { this.position = position; }
    public void setImmediateSupervisor(String immediateSupervisor) { this.immediateSupervisor = immediateSupervisor; }
    public void setBasicSalary(String basicSalary) { this.basicSalary = basicSalary; }
    public void setRiceSubsidy(String riceSubsidy) { this.riceSubsidy = riceSubsidy; }
    public void setPhoneAllowance(String phoneAllowance) { this.phoneAllowance = phoneAllowance; }
    public void setClothingAllowance(String clothingAllowance) { this.clothingAllowance = clothingAllowance; }
    public void setGrossSemimonthlyRate(String grossSemimonthlyRate) { this.grossSemimonthlyrate = grossSemimonthlyRate; }
    public void setHourlyRate(String hourlyRate) { this.hourlyRate = hourlyRate; }
    
    
    }

