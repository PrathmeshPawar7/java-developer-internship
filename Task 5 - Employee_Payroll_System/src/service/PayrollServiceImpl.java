package service;

import dao.EmployeeDAO;
import dao.SalarySlipDAO;
import exception.EmployeeNotFoundException;
import exception.InvalidSalaryException;
import exception.PayrollAlreadyGeneratedException;
import model.Department;
import model.Employee;
import model.SalarySlip;
import util.IdGenerator;
import util.PayrollConstants;
import util.TaxCalculator;

import java.time.Month;
import java.util.List;

/**
 * Concrete business-logic implementation of PayrollService.
 * Coordinates between EmployeeDAO and SalarySlipDAO, and owns the salary
 * calculation pipeline: basic -> allowances -> gross -> deductions -> net.
 */
public class PayrollServiceImpl implements PayrollService {

    private final EmployeeDAO employeeDAO;
    private final SalarySlipDAO salarySlipDAO;
    private final IdGenerator idGenerator;

    public PayrollServiceImpl(EmployeeDAO employeeDAO, SalarySlipDAO salarySlipDAO, IdGenerator idGenerator) {
        this.employeeDAO = employeeDAO;
        this.salarySlipDAO = salarySlipDAO;
        this.idGenerator = idGenerator;
    }

    @Override
    public Employee addEmployee(String name, String email, String designation,
                                 Department department, double basicSalary) throws InvalidSalaryException {
        if (basicSalary <= 0) {
            throw new InvalidSalaryException("Basic salary must be greater than zero.");
        }
        Employee employee = new Employee(idGenerator.nextEmployeeId(), name, email,
                designation, department, basicSalary);
        employeeDAO.addEmployee(employee);
        return employee;
    }

    @Override
    public Employee getEmployee(int employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("No employee found with ID: " + employeeId);
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public boolean removeEmployee(int employeeId) throws EmployeeNotFoundException {
        getEmployee(employeeId); // validates existence
        return employeeDAO.removeEmployee(employeeId);
    }

    @Override
    public SalarySlip generatePayslip(int employeeId, Month month, int year)
            throws EmployeeNotFoundException, PayrollAlreadyGeneratedException {

        Employee employee = getEmployee(employeeId);

        if (salarySlipDAO.slipExists(employeeId, month, year)) {
            throw new PayrollAlreadyGeneratedException(String.format(
                    "A payslip for employee %d for %s %d has already been generated.",
                    employeeId, month, year));
        }

        // ---- Step 2: Salary calculation logic (basic -> allowances -> gross) ----
        double basic = employee.getBasicSalary();
        double hra = round(basic * PayrollConstants.HRA_PERCENT_OF_BASIC);
        double da = round(basic * PayrollConstants.DA_PERCENT_OF_BASIC);
        double specialAllowance = round(basic * PayrollConstants.SPECIAL_ALLOWANCE_PERCENT_OF_BASIC);
        double grossSalary = basic + hra + da + specialAllowance;

        // ---- Step 3: Tax and deductions ----
        double pf = Math.min(round(basic * PayrollConstants.PF_PERCENT_OF_BASIC),
                PayrollConstants.PF_MONTHLY_CAP);
        double professionalTax = grossSalary > PayrollConstants.PROFESSIONAL_TAX_EXEMPT_GROSS_THRESHOLD
                ? PayrollConstants.PROFESSIONAL_TAX_MONTHLY : 0.0;
        double incomeTax = TaxCalculator.calculateMonthlyTds(grossSalary);

        // ---- Step 4/5: Build and store the payslip ----
        SalarySlip slip = new SalarySlip(idGenerator.nextSlipId(), employeeId, month, year,
                basic, hra, da, specialAllowance, pf, professionalTax, incomeTax);
        salarySlipDAO.addSlip(slip);
        return slip;
    }

    @Override
    public List<SalarySlip> getAllPayslips() {
        return salarySlipDAO.getAllSlips();
    }

    @Override
    public List<SalarySlip> getPayslipsForEmployee(int employeeId) throws EmployeeNotFoundException {
        getEmployee(employeeId); // validates existence
        return salarySlipDAO.getSlipsByEmployee(employeeId);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
