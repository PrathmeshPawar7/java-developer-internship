package model;

import java.time.LocalDate;
import java.time.Month;

/**
 * Represents a single generated payslip for one employee for one
 * month/year. Immutable once created - a payslip is a historical record
 * and should never be edited after generation; a correction should be a
 * new slip, not a mutation of an old one.
 */
public class SalarySlip {

    private final int slipId;
    private final int employeeId;
    private final Month month;
    private final int year;

    private final double basicSalary;
    private final double hra;
    private final double da;
    private final double specialAllowance;
    private final double grossSalary;

    private final double providentFund;
    private final double professionalTax;
    private final double incomeTax;
    private final double totalDeductions;

    private final double netSalary;
    private final LocalDate generatedOn;

    public SalarySlip(int slipId, int employeeId, Month month, int year,
                       double basicSalary, double hra, double da, double specialAllowance,
                       double providentFund, double professionalTax, double incomeTax) {
        this.slipId = slipId;
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
        this.specialAllowance = specialAllowance;
        this.grossSalary = basicSalary + hra + da + specialAllowance;
        this.providentFund = providentFund;
        this.professionalTax = professionalTax;
        this.incomeTax = incomeTax;
        this.totalDeductions = providentFund + professionalTax + incomeTax;
        this.netSalary = grossSalary - totalDeductions;
        this.generatedOn = LocalDate.now();
    }

    public int getSlipId() {
        return slipId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getHra() {
        return hra;
    }

    public double getDa() {
        return da;
    }

    public double getSpecialAllowance() {
        return specialAllowance;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getProvidentFund() {
        return providentFund;
    }

    public double getProfessionalTax() {
        return professionalTax;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    /**
     * Full formatted payslip, suitable for printing to console.
     */
    public String toPrintableSlip(String employeeName, String designation) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append("                    SALARY SLIP\n");
        sb.append("========================================================\n");
        sb.append(String.format("Slip ID       : %d%n", slipId));
        sb.append(String.format("Employee ID   : %d%n", employeeId));
        sb.append(String.format("Employee Name : %s%n", employeeName));
        sb.append(String.format("Designation   : %s%n", designation));
        sb.append(String.format("Pay Period    : %s %d%n", month, year));
        sb.append("--------------------------------------------------------\n");
        sb.append("EARNINGS                              AMOUNT (Rs.)\n");
        sb.append(String.format("Basic Salary                          %12.2f%n", basicSalary));
        sb.append(String.format("HRA                                    %12.2f%n", hra));
        sb.append(String.format("DA                                     %12.2f%n", da));
        sb.append(String.format("Special Allowance                      %12.2f%n", specialAllowance));
        sb.append("--------------------------------------------------------\n");
        sb.append(String.format("GROSS SALARY                           %12.2f%n", grossSalary));
        sb.append("--------------------------------------------------------\n");
        sb.append("DEDUCTIONS                             AMOUNT (Rs.)\n");
        sb.append(String.format("Provident Fund (PF)                    %12.2f%n", providentFund));
        sb.append(String.format("Professional Tax                       %12.2f%n", professionalTax));
        sb.append(String.format("Income Tax (TDS)                       %12.2f%n", incomeTax));
        sb.append("--------------------------------------------------------\n");
        sb.append(String.format("TOTAL DEDUCTIONS                       %12.2f%n", totalDeductions));
        sb.append("========================================================\n");
        sb.append(String.format("NET SALARY                              %12.2f%n", netSalary));
        sb.append("========================================================\n");
        sb.append(String.format("Generated On  : %s%n", generatedOn));
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "%-6d %-6d %-10s %-6d %-12.2f %-12.2f %-12.2f",
                slipId, employeeId, month, year, grossSalary, totalDeductions, netSalary
        );
    }
}
