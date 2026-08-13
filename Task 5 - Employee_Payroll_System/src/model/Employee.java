package model;

import java.time.LocalDate;

/**
 * Represents an employee and their core salary structure.
 * Only the basic salary is stored directly - allowances and deductions are
 * derived from it at payslip-generation time (see SalaryCalculator), which
 * keeps this class a simple data holder rather than a place for business rules.
 */
public class Employee {

    private final int employeeId;
    private String name;
    private String email;
    private String designation;
    private Department department;
    private double basicSalary;
    private final LocalDate joiningDate;
    private boolean active;

    public Employee(int employeeId, String name, String email, String designation,
                     Department department, double basicSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.department = department;
        this.basicSalary = basicSalary;
        this.joiningDate = LocalDate.now();
        this.active = true;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format(
                "%-6d %-20s %-18s %-16s %-12.2f %-10s %-12s",
                employeeId, name, designation, department, basicSalary,
                active ? "ACTIVE" : "INACTIVE", joiningDate
        );
    }
}
