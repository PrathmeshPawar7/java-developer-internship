package dao;

import model.Transaction;

import java.util.List;

/**
 * Data access contract for Transaction (deposit/withdrawal) persistence.
 */
public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByAccount(int accountNumber);
}
