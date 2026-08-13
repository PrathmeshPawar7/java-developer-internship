package service;

import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.InvalidRecordException;
import exception.MemberNotFoundException;
import model.Book;
import model.IssueRecord;
import model.Member;

import java.util.List;

/**
 * Business-logic contract for the Library Management System.
 * The console UI (Main) depends only on this interface, not on DAO details.
 */
public interface LibraryService {

    // Book operations
    Book addBook(String title, String author, String isbn, String category, int copies);
    boolean removeBook(int bookId) throws BookNotFoundException;
    List<Book> getAllBooks();
    List<Book> getAvailableBooks();
    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByAuthor(String author);
    Book getBookById(int bookId) throws BookNotFoundException;

    // Member operations
    Member addMember(String name, String email, String phone);
    boolean removeMember(int memberId) throws MemberNotFoundException;
    List<Member> getAllMembers();

    // Issue / return operations
    IssueRecord issueBook(int bookId, int memberId)
            throws BookNotFoundException, BookNotAvailableException, MemberNotFoundException;
    double returnBook(int bookId) throws InvalidRecordException, BookNotFoundException;
    List<IssueRecord> getAllIssueRecords();
    List<IssueRecord> getOverdueRecords();
    List<IssueRecord> getRecordsByMember(int memberId);
}
