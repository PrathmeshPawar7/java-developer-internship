package exception;

/**
 * Thrown when a requested book does not exist in the catalog.
 */
public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
