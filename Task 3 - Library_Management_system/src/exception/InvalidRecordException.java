package exception;

/**
 * Thrown when an issue/return operation is attempted on an invalid or
 * already-closed record.
 */
public class InvalidRecordException extends Exception {
    public InvalidRecordException(String message) {
        super(message);
    }
}
