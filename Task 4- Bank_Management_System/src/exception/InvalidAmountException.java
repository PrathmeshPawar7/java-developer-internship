package exception;

/**
 * Thrown when a deposit/withdrawal amount is zero, negative, or otherwise invalid.
 */
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}
