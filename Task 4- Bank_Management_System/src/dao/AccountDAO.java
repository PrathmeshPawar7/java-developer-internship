package dao;

import model.Account;

import java.util.List;

/**
 * Data access contract for Account persistence operations.
 */
public interface AccountDAO {
    void addAccount(Account account);
    Account getAccountByNumber(int accountNumber);
    List<Account> getAllAccounts();
    void updateAccount(Account account);
}
