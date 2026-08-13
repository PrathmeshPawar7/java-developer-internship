package dao;

import model.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of TransactionDAO using a HashMap keyed by transactionId.
 * Preserves insertion order via LinkedHashMap so history displays chronologically.
 */
public class TransactionDAOImpl implements TransactionDAO {

    private final Map<Integer, Transaction> transactionStore = new LinkedHashMap<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactionStore.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionStore.values());
    }

    @Override
    public List<Transaction> getTransactionsByAccount(int accountNumber) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactionStore.values()) {
            if (t.getAccountNumber() == accountNumber) {
                result.add(t);
            }
        }
        return result;
    }
}
