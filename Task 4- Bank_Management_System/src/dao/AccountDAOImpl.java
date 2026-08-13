package dao;

import model.Account;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of AccountDAO using a HashMap keyed by account number.
 * Swappable later for a JDBC/Spring Data JPA implementation without touching
 * the service layer, since it depends only on the AccountDAO interface.
 */
public class AccountDAOImpl implements AccountDAO {

    private final Map<Integer, Account> accountStore = new LinkedHashMap<>();

    @Override
    public void addAccount(Account account) {
        accountStore.put(account.getAccountNumber(), account);
    }

    @Override
    public Account getAccountByNumber(int accountNumber) {
        return accountStore.get(accountNumber);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountStore.values());
    }

    @Override
    public void updateAccount(Account account) {
        accountStore.put(account.getAccountNumber(), account);
    }
}
