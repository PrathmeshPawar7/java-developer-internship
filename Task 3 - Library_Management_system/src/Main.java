import dao.BookDAO;
import dao.BookDAOImpl;
import dao.IssueRecordDAO;
import dao.IssueRecordDAOImpl;
import dao.MemberDAO;
import dao.MemberDAOImpl;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.InvalidRecordException;
import exception.MemberNotFoundException;
import model.Book;
import model.IssueRecord;
import model.Member;
import service.LibraryService;
import service.LibraryServiceImpl;
import util.IdGenerator;

import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Library Management System.
 * Wires up the DAO and Service layers, then drives a menu-based UI.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static LibraryService libraryService;

    public static void main(String[] args) {
        // Manual dependency wiring (constructor injection) - mirrors how
        // Spring would wire @Repository/@Service beans, without the framework.
        BookDAO bookDAO = new BookDAOImpl();
        MemberDAO memberDAO = new MemberDAOImpl();
        IssueRecordDAO issueRecordDAO = new IssueRecordDAOImpl();
        IdGenerator idGenerator = new IdGenerator();
        libraryService = new LibraryServiceImpl(bookDAO, memberDAO, issueRecordDAO, idGenerator);

        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> displayAllBooks();
                case 4 -> displayAvailableBooks();
                case 5 -> searchBooks();
                case 6 -> addMember();
                case 7 -> displayAllMembers();
                case 8 -> issueBook();
                case 9 -> returnBook();
                case 10 -> displayAllIssueRecords();
                case 11 -> displayOverdueBooks();
                case 12 -> displayMemberHistory();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println(" 1. Add Book");
        System.out.println(" 2. Remove Book");
        System.out.println(" 3. Display All Books");
        System.out.println(" 4. Display Available Books");
        System.out.println(" 5. Search Books (Title / Author)");
        System.out.println(" 6. Add Member");
        System.out.println(" 7. Display All Members");
        System.out.println(" 8. Issue Book");
        System.out.println(" 9. Return Book");
        System.out.println("10. Display All Issue Records");
        System.out.println("11. Display Overdue Books");
        System.out.println("12. Display Member Borrow History");
        System.out.println(" 0. Exit");
        System.out.println("======================================");
    }

    // ---------- Book operations ----------

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        String title = readString("Title: ");
        String author = readString("Author: ");
        String isbn = readString("ISBN: ");
        String category = readString("Category: ");
        int copies = readInt("Number of copies: ");

        Book book = libraryService.addBook(title, author, isbn, category, copies);
        System.out.println("Book added successfully with ID: " + book.getBookId());
    }

    private static void removeBook() {
        int bookId = readInt("\nEnter Book ID to remove: ");
        try {
            libraryService.removeBook(bookId);
            System.out.println("Book removed successfully.");
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllBooks() {
        System.out.println("\n--- All Books ---");
        printBookTable(libraryService.getAllBooks());
    }

    private static void displayAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        printBookTable(libraryService.getAvailableBooks());
    }

    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. By Title  2. By Author  3. By ID");
        int option = readInt("Choose search type: ");

        List<Book> results;
        switch (option) {
            case 1 -> {
                String title = readString("Enter title keyword: ");
                results = libraryService.searchBooksByTitle(title);
                printBookTable(results);
            }
            case 2 -> {
                String author = readString("Enter author keyword: ");
                results = libraryService.searchBooksByAuthor(author);
                printBookTable(results);
            }
            case 3 -> {
                int id = readInt("Enter Book ID: ");
                try {
                    Book book = libraryService.getBookById(id);
                    printBookTable(List.of(book));
                } catch (BookNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void printBookTable(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books to display.");
            return;
        }
        System.out.printf("%-5s %-30s %-20s %-15s %-12s %-10s %-10s%n",
                "ID", "Title", "Author", "ISBN", "Category", "Total", "Available");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    // ---------- Member operations ----------

    private static void addMember() {
        System.out.println("\n--- Add New Member ---");
        String name = readString("Name: ");
        String email = readString("Email: ");
        String phone = readString("Phone: ");
        Member member = libraryService.addMember(name, email, phone);
        System.out.println("Member registered successfully with ID: " + member.getMemberId());
    }

    private static void displayAllMembers() {
        System.out.println("\n--- All Members ---");
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
            return;
        }
        System.out.printf("%-5s %-20s %-25s %-15s %-12s%n",
                "ID", "Name", "Email", "Phone", "Joined On");
        for (Member m : members) {
            System.out.println(m);
        }
    }

    // ---------- Issue / Return operations ----------

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        int bookId = readInt("Enter Book ID: ");
        int memberId = readInt("Enter Member ID: ");
        try {
            IssueRecord record = libraryService.issueBook(bookId, memberId);
            System.out.println("Book issued successfully. Record ID: " + record.getRecordId()
                    + " | Due date: " + record.getDueDate());
        } catch (BookNotFoundException | MemberNotFoundException | BookNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        int bookId = readInt("Enter Book ID: ");
        try {
            double fine = libraryService.returnBook(bookId);
            if (fine > 0) {
                System.out.printf("Book returned late. Fine due: Rs. %.2f%n", fine);
            } else {
                System.out.println("Book returned successfully. No fine due.");
            }
        } catch (InvalidRecordException | BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllIssueRecords() {
        System.out.println("\n--- All Issue Records ---");
        printRecordTable(libraryService.getAllIssueRecords());
    }

    private static void displayOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");
        printRecordTable(libraryService.getOverdueRecords());
    }

    private static void displayMemberHistory() {
        int memberId = readInt("\nEnter Member ID: ");
        System.out.println("--- Borrow History for Member " + memberId + " ---");
        printRecordTable(libraryService.getRecordsByMember(memberId));
    }

    private static void printRecordTable(List<IssueRecord> records) {
        if (records.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }
        System.out.printf("%-5s %-10s %-10s %-12s %-12s %-12s %-10s%n",
                "RecID", "BookID", "MemID", "IssueDate", "DueDate", "ReturnDate", "Status");
        for (IssueRecord r : records) {
            System.out.println(r);
        }
    }

    // ---------- Sample data ----------

    private static void seedSampleData() {
        libraryService.addBook("Clean Code", "Robert C. Martin", "9780132350884", "Software Engineering", 3);
        libraryService.addBook("Effective Java", "Joshua Bloch", "9780134685991", "Java", 2);
        libraryService.addBook("Design Patterns", "Erich Gamma", "9780201633610", "Software Engineering", 1);
        libraryService.addMember("Prathmesh Pawar", "prathmesh@example.com", "9999999999");
    }

    // ---------- Input helpers ----------

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
