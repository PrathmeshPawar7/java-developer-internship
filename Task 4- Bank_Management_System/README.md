# Bank Management System (Java, Console-Based)

A layered, console-based Bank Management System built in core Java —
an interview-ready internship project demonstrating OOP, custom exception
handling, collections, and clean architecture that maps directly onto
Spring Boot's Controller -> Service -> Repository pattern.

## Features

- **Account creation** — open a SAVINGS (Rs.500 minimum balance) or CURRENT
  (no minimum) account with holder details and an opening deposit.
- **Deposit / Withdraw** — fully validated: amounts must be positive,
  withdrawals can never drop a SAVINGS account below its minimum balance,
  and transactions are blocked on closed accounts.
- **Balance check** — instant lookup by account number.
- **Account details & listing** — view a single account or all accounts.
- **Transaction history** — full chronological history per account, plus a
  bank-wide transaction log.
- **Close account** — deactivates an account; further transactions are
  rejected with a clear error.
- **Custom checked exceptions** — `AccountNotFoundException`,
  `InsufficientBalanceException`, `InvalidAmountException`,
  `AccountInactiveException` for explicit, testable error handling instead
  of null checks or generic exceptions.

## Architecture

```
Main.java              -> Console UI / entry point (dependency wiring)
model/                 -> Account, AccountType, Transaction, TransactionType (POJOs)
dao/                    -> AccountDAO, TransactionDAO (interfaces)
                           + in-memory Impl classes (HashMap-backed)
service/                -> BankService (interface) + BankServiceImpl
                           (business rules: minimum balance, validation)
exception/              -> Custom checked exceptions
util/                   -> IdGenerator (sequential account/transaction IDs)
```

This mirrors a real-world layered enterprise design:

- **Model layer** — plain data objects, no business logic. `Transaction` is
  immutable once created, matching how ledger entries should behave.
- **DAO layer** — persistence abstraction. The in-memory `HashMap` stores
  can be swapped for JDBC/Spring Data JPA repositories later without
  touching the service or UI layers, since everything is coded against
  interfaces.
- **Service layer** — all banking rules live here (minimum balance
  enforcement, positive-amount validation, closed-account guarding) — the
  same separation of concerns you'd use in a Spring `@Service` class.
- **UI layer** (`Main`) — purely responsible for I/O and delegates all
  logic to `BankService`.

## How to Run

```bash
cd src
javac Main.java model/*.java dao/*.java service/*.java exception/*.java util/*.java -d ../out
java -cp ../out Main
```

The app seeds two sample accounts (one SAVINGS, one CURRENT) on startup so
you can try deposits/withdrawals immediately.

## Sample Run

```
Enter your choice: 6

--- All Accounts ---
AccNo        Holder               Type       Balance      Status     Opened On   
100001       Prathmesh Pawar      SAVINGS    5000.00      ACTIVE     2026-08-13  
100002       Anita Sharma         CURRENT    1000.00      ACTIVE     2026-08-13  

Enter your choice: 3
--- Withdraw ---
Enter Account Number: 100001
Enter amount to withdraw: 4800
Error: Withdrawal denied. Balance cannot fall below the minimum of 500.00 for a SAVINGS account. Current balance: 5000.00
```

## Possible Extensions (great talking points in an interview)

- Swap the in-memory DAO implementations for Spring Data JPA + MySQL.
- Expose the same service layer via REST controllers (Spring Boot).
- Add Spring Security + JWT for customer login and account-owner checks.
- Add fund transfer between two accounts (atomic debit + credit).
- Add interest calculation for SAVINGS accounts (monthly job).
- Persist data to a file (CSV/JSON) for durability without a full database.
