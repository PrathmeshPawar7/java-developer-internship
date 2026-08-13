package dao;

import model.Book;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of BookDAO using a HashMap keyed by bookId.
 * Swappable later for a JDBC/JPA-backed implementation without touching
 * the service layer, since it depends only on the BookDAO interface.
 */
public class BookDAOImpl implements BookDAO {

    private final Map<Integer, Book> bookStore = new LinkedHashMap<>();

    @Override
    public void addBook(Book book) {
        bookStore.put(book.getBookId(), book);
    }

    @Override
    public boolean removeBook(int bookId) {
        return bookStore.remove(bookId) != null;
    }

    @Override
    public Book getBookById(int bookId) {
        return bookStore.get(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookStore.values());
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        String lower = title.toLowerCase();
        for (Book b : bookStore.values()) {
            if (b.getTitle().toLowerCase().contains(lower)) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        String lower = author.toLowerCase();
        for (Book b : bookStore.values()) {
            if (b.getAuthor().toLowerCase().contains(lower)) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public void updateBook(Book book) {
        bookStore.put(book.getBookId(), book);
    }
}
