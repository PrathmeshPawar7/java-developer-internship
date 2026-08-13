package exception;

/**
 * Thrown when a requested employee ID does not exist.
 */
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
