package service;

import dao.BookDAO;
import dao.IssueRecordDAO;
import dao.MemberDAO;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.InvalidRecordException;
import exception.MemberNotFoundException;
import model.Book;
import model.IssueRecord;
import model.IssueStatus;
import model.Member;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete business-logic implementation of LibraryService.
 * Coordinates between BookDAO, MemberDAO and IssueRecordDAO and enforces
 * library rules (availability checks, fine calculation, etc.).
 */
public class LibraryServiceImpl implements LibraryService {

    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final IssueRecordDAO issueRecordDAO;
    private final IdGenerator idGenerator;

    public LibraryServiceImpl(BookDAO bookDAO, MemberDAO memberDAO,
                               IssueRecordDAO issueRecordDAO, IdGenerator idGenerator) {
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
        this.issueRecordDAO = issueRecordDAO;
        this.idGenerator = idGenerator;
    }

    @Override
    public Book addBook(String title, String author, String isbn, String category, int copies) {
        Book book = new Book(idGenerator.nextBookId(), title, author, isbn, category, copies);
        bookDAO.addBook(book);
        return book;
    }

    @Override
    public boolean removeBook(int bookId) throws BookNotFoundException {
        if (bookDAO.getBookById(bookId) == null) {
            throw new BookNotFoundException("No book found with ID: " + bookId);
        }
        return bookDAO.removeBook(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book b : bookDAO.getAllBooks()) {
            if (b.isAvailable()) {
                available.add(b);
            }
        }
        return available;
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookDAO.searchByTitle(title);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return bookDAO.searchByAuthor(author);
    }

    @Override
    public Book getBookById(int bookId) throws BookNotFoundException {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("No book found with ID: " + bookId);
        }
        return book;
    }

    @Override
    public Member addMember(String name, String email, String phone) {
        Member member = new Member(idGenerator.nextMemberId(), name, email, phone);
        memberDAO.addMember(member);
        return member;
    }

    @Override
    public boolean removeMember(int memberId) throws MemberNotFoundException {
        if (memberDAO.getMemberById(memberId) == null) {
            throw new MemberNotFoundException("No member found with ID: " + memberId);
        }
        return memberDAO.removeMember(memberId);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    @Override
    public IssueRecord issueBook(int bookId, int memberId)
            throws BookNotFoundException, BookNotAvailableException, MemberNotFoundException {

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("No book found with ID: " + bookId);
        }
        if (memberDAO.getMemberById(memberId) == null) {
            throw new MemberNotFoundException("No member found with ID: " + memberId);
        }
        if (!book.isAvailable()) {
            throw new BookNotAvailableException("\"" + book.getTitle() + "\" has no copies available right now.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDAO.updateBook(book);

        IssueRecord record = new IssueRecord(idGenerator.nextRecordId(), bookId, memberId);
        issueRecordDAO.addRecord(record);
        return record;
    }

    @Override
    public double returnBook(int bookId) throws InvalidRecordException, BookNotFoundException {
        IssueRecord record = issueRecordDAO.getActiveRecordForBook(bookId);
        if (record == null) {
            throw new InvalidRecordException("No active issue record found for book ID: " + bookId);
        }

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("No book found with ID: " + bookId);
        }

        double fine = record.markReturned();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookDAO.updateBook(book);
        return fine;
    }

    @Override
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordDAO.getAllRecords();
    }

    @Override
    public List<IssueRecord> getOverdueRecords() {
        List<IssueRecord> overdue = new ArrayList<>();
        for (IssueRecord r : issueRecordDAO.getAllRecords()) {
            if (r.isOverdue()) {
                r.setStatus(IssueStatus.OVERDUE);
                overdue.add(r);
            }
        }
        return overdue;
    }

    @Override
    public List<IssueRecord> getRecordsByMember(int memberId) {
        return issueRecordDAO.getRecordsByMember(memberId);
    }
}
