package service;

import exception.EmployeeNotFoundException;
import exception.InvalidSalaryException;
import exception.PayrollAlreadyGeneratedException;
import model.Department;
import model.Employee;
import model.SalarySlip;

import java.time.Month;
import java.util.List;

/**
 * Business-logic contract for the Employee Payroll System.
 * The console UI (Main) depends only on this interface, not on DAO details.
 */
public interface PayrollService {

    Employee addEmployee(String name, String email, String designation,
                          Department department, double basicSalary) throws InvalidSalaryException;

    Employee getEmployee(int employeeId) throws EmployeeNotFoundException;

    List<Employee> getAllEmployees();

    boolean removeEmployee(int employeeId) throws EmployeeNotFoundException;

    SalarySlip generatePayslip(int employeeId, Month month, int year)
            throws EmployeeNotFoundException, PayrollAlreadyGeneratedException;

    List<SalarySlip> getAllPayslips();

    List<SalarySlip> getPayslipsForEmployee(int employeeId) throws EmployeeNotFoundException;
}
