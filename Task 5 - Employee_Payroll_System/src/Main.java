import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import dao.SalarySlipDAO;
import dao.SalarySlipDAOImpl;
import exception.EmployeeNotFoundException;
import exception.InvalidSalaryException;
import exception.PayrollAlreadyGeneratedException;
import model.Department;
import model.Employee;
import model.SalarySlip;
import service.PayrollService;
import service.PayrollServiceImpl;
import util.IdGenerator;

import java.time.Month;
import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Employee Payroll System.
 * Wires up the DAO and Service layers, then drives a menu-based UI.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static PayrollService payrollService;

    public static void main(String[] args) {
        // Manual dependency wiring (constructor injection) - mirrors how
        // Spring would wire @Repository/@Service beans, without the framework.
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        SalarySlipDAO salarySlipDAO = new SalarySlipDAOImpl();
        IdGenerator idGenerator = new IdGenerator();
        payrollService = new PayrollServiceImpl(employeeDAO, salarySlipDAO, idGenerator);

        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayAllEmployees();
                case 3 -> viewEmployee();
                case 4 -> removeEmployee();
                case 5 -> generatePayslip();
                case 6 -> viewPayslipsForEmployee();
                case 7 -> viewAllPayslipsSummary();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting Payroll System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== EMPLOYEE PAYROLL SYSTEM =====");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. View Employee Details");
        System.out.println("4. Remove Employee");
        System.out.println("5. Generate Payslip");
        System.out.println("6. View Payslips for an Employee");
        System.out.println("7. View All Payslips (Summary)");
        System.out.println("0. Exit");
        System.out.println("====================================");
    }

    // ---------- Employee operations ----------

    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        String name = readString("Name: ");
        String email = readString("Email: ");
        String designation = readString("Designation: ");

        System.out.println("Department: 1.ENGINEERING 2.HUMAN_RESOURCES 3.FINANCE 4.SALES 5.OPERATIONS");
        int deptChoice = readInt("Choose department: ");
        Department department = mapDepartment(deptChoice);

        double basicSalary = readDouble("Basic Monthly Salary: ");

        try {
            Employee employee = payrollService.addEmployee(name, email, designation, department, basicSalary);
            System.out.println("Employee added successfully with ID: " + employee.getEmployeeId());
        } catch (InvalidSalaryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Department mapDepartment(int choice) {
        return switch (choice) {
            case 2 -> Department.HUMAN_RESOURCES;
            case 3 -> Department.FINANCE;
            case 4 -> Department.SALES;
            case 5 -> Department.OPERATIONS;
            default -> Department.ENGINEERING;
        };
    }

    private static void displayAllEmployees() {
        System.out.println("\n--- All Employees ---");
        printEmployeeTable(payrollService.getAllEmployees());
    }

    private static void viewEmployee() {
        int empId = readInt("\nEnter Employee ID: ");
        try {
            Employee employee = payrollService.getEmployee(empId);
            printEmployeeTable(List.of(employee));
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeEmployee() {
        int empId = readInt("\nEnter Employee ID to remove: ");
        try {
            payrollService.removeEmployee(empId);
            System.out.println("Employee removed successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printEmployeeTable(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }
        System.out.printf("%-6s %-20s %-18s %-16s %-12s %-10s %-12s%n",
                "ID", "Name", "Designation", "Department", "Basic", "Status", "Joined On");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    // ---------- Payroll operations ----------

    private static void generatePayslip() {
        System.out.println("\n--- Generate Payslip ---");
        int empId = readInt("Enter Employee ID: ");
        Month month = readMonth("Enter month (1-12): ");
        int year = readInt("Enter year (e.g. 2026): ");

        try {
            SalarySlip slip = payrollService.generatePayslip(empId, month, year);
            Employee employee = payrollService.getEmployee(empId);
            System.out.println("\nPayslip generated successfully!\n");
            System.out.println(slip.toPrintableSlip(employee.getName(), employee.getDesignation()));
        } catch (EmployeeNotFoundException | PayrollAlreadyGeneratedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewPayslipsForEmployee() {
        int empId = readInt("\nEnter Employee ID: ");
        try {
            Employee employee = payrollService.getEmployee(empId);
            List<SalarySlip> slips = payrollService.getPayslipsForEmployee(empId);
            if (slips.isEmpty()) {
                System.out.println("No payslips generated yet for this employee.");
                return;
            }
            System.out.println("Which slip would you like to view in full? (or 0 to see summary only)");
            printSlipSummaryTable(slips);
            int slipChoice = readInt("Enter Slip ID (0 to skip): ");
            if (slipChoice != 0) {
                slips.stream()
                        .filter(s -> s.getSlipId() == slipChoice)
                        .findFirst()
                        .ifPresentOrElse(
                                s -> System.out.println("\n" + s.toPrintableSlip(employee.getName(), employee.getDesignation())),
                                () -> System.out.println("No slip found with that ID.")
                        );
            }
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllPayslipsSummary() {
        System.out.println("\n--- All Payslips (Summary) ---");
        printSlipSummaryTable(payrollService.getAllPayslips());
    }

    private static void printSlipSummaryTable(List<SalarySlip> slips) {
        if (slips.isEmpty()) {
            System.out.println("No payslips to display.");
            return;
        }
        System.out.printf("%-6s %-6s %-10s %-6s %-12s %-12s %-12s%n",
                "SlipID", "EmpID", "Month", "Year", "Gross", "Deductions", "Net");
        for (SalarySlip s : slips) {
            System.out.println(s);
        }
    }

    // ---------- Sample data ----------

    private static void seedSampleData() {
        try {
            payrollService.addEmployee("Prathmesh Pawar", "prathmesh@example.com",
                    "Java Backend Developer", Department.ENGINEERING, 45000.0);
            payrollService.addEmployee("Anita Sharma", "anita@example.com",
                    "HR Executive", Department.HUMAN_RESOURCES, 32000.0);
        } catch (InvalidSalaryException e) {
            // Won't happen with these seed values; kept for completeness.
        }
    }

    // ---------- Input helpers ----------

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static Month readMonth(String prompt) {
        while (true) {
            int m = readInt(prompt);
            if (m >= 1 && m <= 12) {
                return Month.of(m);
            }
            System.out.println("Please enter a number between 1 and 12.");
        }
    }
}
