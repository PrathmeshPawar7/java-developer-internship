package dao;

import model.Book;

import java.util.List;

/**
 * Data access contract for Book persistence operations.
 */
public interface BookDAO {
    void addBook(Book book);
    boolean removeBook(int bookId);
    Book getBookById(int bookId);
    List<Book> getAllBooks();
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    void updateBook(Book book);
}
