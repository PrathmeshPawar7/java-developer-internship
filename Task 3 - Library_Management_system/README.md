# Library Management System (Java, Console-Based)

A layered, console-based Library Management System built in core Java —
designed as an interview-ready internship project that demonstrates OOP,
exception handling, collections, and clean architecture principles that
map directly onto Spring Boot's Controller → Service → Repository pattern.

## Features

- **Book management** — add, remove, list, and update books with total/available
  copy tracking.
- **Search** — search books by title (partial match), author (partial match), or ID.
- **Member management** — register and list library members.
- **Issue / Return system** — issue a book to a member (blocks if no copies are
  available), return a book, and automatically calculate a late fine
  (₹5/day, 14-day loan period).
- **Overdue tracking** — list all currently overdue issue records.
- **Borrow history** — view a member's full issue/return history.
- **Custom checked exceptions** — `BookNotFoundException`,
  `BookNotAvailableException`, `MemberNotFoundException`,
  `InvalidRecordException` for clean, explicit error handling instead of
  null checks or generic exceptions.

## Architecture

```
Main.java              -> Console UI / entry point (dependency wiring)
model/                 -> Book, Member, IssueRecord, IssueStatus (POJOs)
dao/                    -> BookDAO, MemberDAO, IssueRecordDAO (interfaces)
                           + in-memory Impl classes (HashMap-backed)
service/                -> LibraryService (interface) + LibraryServiceImpl
                           (business rules: availability checks, fines)
exception/              -> Custom checked exceptions
util/                   -> IdGenerator (sequential ID generation)
```

This mirrors a real-world layered enterprise design:

- **Model layer** — plain data objects, no business logic.
- **DAO layer** — persistence abstraction. The in-memory `HashMap` stores can
  be swapped for JDBC/Spring Data JPA repositories later without touching
  the service or UI layers, because everything is coded against interfaces.
- **Service layer** — all business rules live here (copy availability checks,
  fine calculation, overdue detection) — the same separation of concerns
  you'd use in a Spring `@Service` class.
- **UI layer** (`Main`) — purely responsible for I/O and delegates all logic
  to `LibraryService`.

## How to Run

```bash
cd src
javac Main.java model/*.java dao/*.java service/*.java exception/*.java util/*.java -d ../out
java -cp ../out Main
```

The app seeds a few sample books and one sample member on startup so you can
try issuing/returning books immediately.

## Possible Extensions (great talking points in an interview)

- Swap the in-memory DAO implementations for Spring Data JPA + MySQL.
- Expose the same service layer via REST controllers (Spring Boot).
- Add Spring Security + JWT for librarian/admin login.
- Add pagination and sorting to book search results.
- Persist data to a file (CSV/JSON) for durability without a full database.
