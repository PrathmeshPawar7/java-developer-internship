package service;

import exception.AccountInactiveException;
import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.Account;
import model.AccountType;
import model.Transaction;

import java.util.List;

/**
 * Business-logic contract for the Bank Management System.
 * The console UI (Main) depends only on this interface, not on DAO details.
 */
public interface BankService {

    Account openAccount(String holderName, String email, String phone,
                         AccountType type, double openingBalance) throws InvalidAmountException;

    Account getAccount(int accountNumber) throws AccountNotFoundException;

    List<Account> getAllAccounts();

    double checkBalance(int accountNumber) throws AccountNotFoundException;

    Transaction deposit(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, AccountInactiveException;

    Transaction withdraw(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException,
            InsufficientBalanceException, AccountInactiveException;

    boolean closeAccount(int accountNumber) throws AccountNotFoundException;

    List<Transaction> getTransactionHistory(int accountNumber) throws AccountNotFoundException;

    List<Transaction> getAllTransactions();
}
