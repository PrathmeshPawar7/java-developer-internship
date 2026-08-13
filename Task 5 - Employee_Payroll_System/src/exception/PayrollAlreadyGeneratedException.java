package exception;

/**
 * Thrown when a payslip is requested for an employee/month/year combination
 * that has already been generated, preventing accidental double-payment records.
 */
public class PayrollAlreadyGeneratedException extends Exception {
    public PayrollAlreadyGeneratedException(String message) {
        super(message);
    }
}
