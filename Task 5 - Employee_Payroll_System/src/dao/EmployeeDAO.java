package dao;

import model.Employee;

import java.util.List;

/**
 * Data access contract for Employee persistence operations.
 */
public interface EmployeeDAO {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    boolean removeEmployee(int employeeId);
}
