package exception;

/**
 * Thrown when a salary value is zero, negative, or otherwise invalid.
 */
public class InvalidSalaryException extends Exception {
    public InvalidSalaryException(String message) {
        super(message);
    }
}
