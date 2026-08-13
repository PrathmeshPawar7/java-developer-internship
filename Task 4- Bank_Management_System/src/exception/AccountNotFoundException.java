package exception;

/**
 * Thrown when a requested account number does not exist.
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
