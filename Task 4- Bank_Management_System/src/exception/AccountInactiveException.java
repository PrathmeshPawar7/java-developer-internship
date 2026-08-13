package exception;

/**
 * Thrown when a transaction is attempted on an account that has been closed.
 */
public class AccountInactiveException extends Exception {
    public AccountInactiveException(String message) {
        super(message);
    }
}
