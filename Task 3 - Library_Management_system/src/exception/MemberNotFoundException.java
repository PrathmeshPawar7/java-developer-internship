package exception;

/**
 * Thrown when a requested member does not exist.
 */
public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
