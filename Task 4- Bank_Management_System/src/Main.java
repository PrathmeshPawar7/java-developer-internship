import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.TransactionDAO;
import dao.TransactionDAOImpl;
import exception.AccountInactiveException;
import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.Account;
import model.AccountType;
import model.Transaction;
import service.BankService;
import service.BankServiceImpl;
import util.IdGenerator;

import java.util.List;
import java.util.Scanner;

/**
 * Console entry point for the Bank Management System.
 * Wires up the DAO and Service layers, then drives a menu-based UI.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static BankService bankService;

    public static void main(String[] args) {
        // Manual dependency wiring (constructor injection) - mirrors how
        // Spring would wire @Repository/@Service beans, without the framework.
        AccountDAO accountDAO = new AccountDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        IdGenerator idGenerator = new IdGenerator();
        bankService = new BankServiceImpl(accountDAO, transactionDAO, idGenerator);

        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> openAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> checkBalance();
                case 5 -> showAccountDetails();
                case 6 -> displayAllAccounts();
                case 7 -> showTransactionHistory();
                case 8 -> showAllTransactions();
                case 9 -> closeAccount();
                case 0 -> {
                    running = false;
                    System.out.println("Thank you for banking with us. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
        System.out.println("1. Open New Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. Show Account Details");
        System.out.println("6. Display All Accounts");
        System.out.println("7. Show Transaction History (single account)");
        System.out.println("8. Show All Transactions (bank-wide)");
        System.out.println("9. Close Account");
        System.out.println("0. Exit");
        System.out.println("===================================");
    }

    // ---------- Account operations ----------

    private static void openAccount() {
        System.out.println("\n--- Open New Account ---");
        String name = readString("Account Holder Name: ");
        String email = readString("Email: ");
        String phone = readString("Phone: ");

        System.out.println("Account Type: 1. SAVINGS (min balance Rs.500)  2. CURRENT (no minimum)");
        int typeChoice = readInt("Choose type: ");
        AccountType type = (typeChoice == 2) ? AccountType.CURRENT : AccountType.SAVINGS;

        double openingBalance = readDouble("Opening balance: ");

        try {
            Account account = bankService.openAccount(name, email, phone, type, openingBalance);
            System.out.println("Account opened successfully! Account Number: " + account.getAccountNumber());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deposit() {
        System.out.println("\n--- Deposit ---");
        int accNo = readInt("Enter Account Number: ");
        double amount = readDouble("Enter amount to deposit: ");
        try {
            Transaction txn = bankService.deposit(accNo, amount);
            System.out.printf("Deposit successful. New balance: Rs. %.2f (Txn ID: %d)%n",
                    txn.getBalanceAfter(), txn.getTransactionId());
        } catch (AccountNotFoundException | InvalidAmountException | AccountInactiveException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw() {
        System.out.println("\n--- Withdraw ---");
        int accNo = readInt("Enter Account Number: ");
        double amount = readDouble("Enter amount to withdraw: ");
        try {
            Transaction txn = bankService.withdraw(accNo, amount);
            System.out.printf("Withdrawal successful. New balance: Rs. %.2f (Txn ID: %d)%n",
                    txn.getBalanceAfter(), txn.getTransactionId());
        } catch (AccountNotFoundException | InvalidAmountException
                 | InsufficientBalanceException | AccountInactiveException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        int accNo = readInt("\nEnter Account Number: ");
        try {
            double balance = bankService.checkBalance(accNo);
            System.out.printf("Current balance: Rs. %.2f%n", balance);
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showAccountDetails() {
        int accNo = readInt("\nEnter Account Number: ");
        try {
            Account account = bankService.getAccount(accNo);
            System.out.println("--- Account Details ---");
            printAccountTable(List.of(account));
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        printAccountTable(bankService.getAllAccounts());
    }

    private static void printAccountTable(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts to display.");
            return;
        }
        System.out.printf("%-12s %-20s %-10s %-12s %-10s %-12s%n",
                "AccNo", "Holder", "Type", "Balance", "Status", "Opened On");
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

    // ---------- Transaction history ----------

    private static void showTransactionHistory() {
        int accNo = readInt("\nEnter Account Number: ");
        try {
            List<Transaction> history = bankService.getTransactionHistory(accNo);
            System.out.println("--- Transaction History for Account " + accNo + " ---");
            printTransactionTable(history);
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showAllTransactions() {
        System.out.println("\n--- All Transactions (Bank-wide) ---");
        printTransactionTable(bankService.getAllTransactions());
    }

    private static void printTransactionTable(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }
        System.out.printf("%-8s %-12s %-12s %-12s %-14s %-20s%n",
                "TxnID", "AccNo", "Type", "Amount", "BalanceAfter", "Timestamp");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // ---------- Close account ----------

    private static void closeAccount() {
        int accNo = readInt("\nEnter Account Number to close: ");
        try {
            bankService.closeAccount(accNo);
            System.out.println("Account " + accNo + " has been closed.");
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------- Sample data ----------

    private static void seedSampleData() {
        try {
            bankService.openAccount("Prathmesh Pawar", "prathmesh@example.com", "9999999999",
                    AccountType.SAVINGS, 5000.0);
            bankService.openAccount("Anita Sharma", "anita@example.com", "8888888888",
                    AccountType.CURRENT, 1000.0);
        } catch (InvalidAmountException e) {
            // Won't happen with these seed values; kept for completeness.
        }
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
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
