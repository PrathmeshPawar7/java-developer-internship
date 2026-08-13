package exception;

/**
 * Thrown when a book exists but has no available copies to issue.
 */
public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
