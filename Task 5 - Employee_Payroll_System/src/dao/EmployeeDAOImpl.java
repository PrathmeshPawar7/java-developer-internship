package dao;

import model.Employee;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of EmployeeDAO using a HashMap keyed by employeeId.
 * Swappable later for a JDBC/Spring Data JPA implementation without touching
 * the service layer, since it depends only on the EmployeeDAO interface.
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private final Map<Integer, Employee> employeeStore = new LinkedHashMap<>();

    @Override
    public void addEmployee(Employee employee) {
        employeeStore.put(employee.getEmployeeId(), employee);
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeStore.get(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeStore.put(employee.getEmployeeId(), employee);
    }

    @Override
    public boolean removeEmployee(int employeeId) {
        return employeeStore.remove(employeeId) != null;
    }
}
