package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import exception.AccountInactiveException;
import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import model.Account;
import model.AccountType;
import model.Transaction;
import model.TransactionType;
import util.IdGenerator;

import java.util.List;

/**
 * Concrete business-logic implementation of BankService.
 * Coordinates between AccountDAO and TransactionDAO and enforces all
 * banking rules: positive-amount validation, minimum-balance protection,
 * and closed-account guarding.
 */
public class BankServiceImpl implements BankService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final IdGenerator idGenerator;

    public BankServiceImpl(AccountDAO accountDAO, TransactionDAO transactionDAO, IdGenerator idGenerator) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.idGenerator = idGenerator;
    }

    @Override
    public Account openAccount(String holderName, String email, String phone,
                                AccountType type, double openingBalance) throws InvalidAmountException {
        if (openingBalance < type.getMinimumBalance()) {
            throw new InvalidAmountException(
                    String.format("Opening balance must be at least %.2f for a %s account.",
                            type.getMinimumBalance(), type));
        }
        Account account = new Account(idGenerator.nextAccountNumber(), holderName, email, phone,
                type, openingBalance);
        accountDAO.addAccount(account);
        return account;
    }

    @Override
    public Account getAccount(int accountNumber) throws AccountNotFoundException {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("No account found with number: " + accountNumber);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @Override
    public double checkBalance(int accountNumber) throws AccountNotFoundException {
        return getAccount(accountNumber).getBalance();
    }

    @Override
    public Transaction deposit(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, AccountInactiveException {

        Account account = getAccount(accountNumber);
        ensureActive(account);
        validateAmount(amount);

        account.setBalance(account.getBalance() + amount);
        accountDAO.updateAccount(account);

        Transaction txn = new Transaction(idGenerator.nextTransactionId(), accountNumber,
                TransactionType.DEPOSIT, amount, account.getBalance());
        transactionDAO.addTransaction(txn);
        return txn;
    }

    @Override
    public Transaction withdraw(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException,
            InsufficientBalanceException, AccountInactiveException {

        Account account = getAccount(accountNumber);
        ensureActive(account);
        validateAmount(amount);

        double balanceAfter = account.getBalance() - amount;
        if (balanceAfter < account.getAccountType().getMinimumBalance()) {
            throw new InsufficientBalanceException(String.format(
                    "Withdrawal denied. Balance cannot fall below the minimum of %.2f for a %s account. "
                            + "Current balance: %.2f",
                    account.getAccountType().getMinimumBalance(), account.getAccountType(), account.getBalance()));
        }

        account.setBalance(balanceAfter);
        accountDAO.updateAccount(account);

        Transaction txn = new Transaction(idGenerator.nextTransactionId(), accountNumber,
                TransactionType.WITHDRAWAL, amount, account.getBalance());
        transactionDAO.addTransaction(txn);
        return txn;
    }

    @Override
    public boolean closeAccount(int accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.setActive(false);
        accountDAO.updateAccount(account);
        return true;
    }

    @Override
    public List<Transaction> getTransactionHistory(int accountNumber) throws AccountNotFoundException {
        getAccount(accountNumber); // validates existence
        return transactionDAO.getTransactionsByAccount(accountNumber);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    // ---------- internal validation helpers ----------

    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Transaction amount must be greater than zero.");
        }
    }

    private void ensureActive(Account account) throws AccountInactiveException {
        if (!account.isActive()) {
            throw new AccountInactiveException(
                    "Account " + account.getAccountNumber() + " is closed and cannot process transactions.");
        }
    }
}
