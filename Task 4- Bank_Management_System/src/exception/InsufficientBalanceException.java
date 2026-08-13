package exception;

/**
 * Thrown when a withdrawal would take the balance below the account's
 * minimum-balance requirement (or below zero for current accounts).
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
