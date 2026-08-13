package model;

import java.time.LocalDate;

/**
 * Represents a bank account and its current balance.
 */
public class Account {

    private final int accountNumber;
    private String holderName;
    private String email;
    private String phone;
    private AccountType accountType;
    private double balance;
    private final LocalDate openedOn;
    private boolean active;

    public Account(int accountNumber, String holderName, String email, String phone,
                    AccountType accountType, double openingBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
        this.balance = openingBalance;
        this.openedOn = LocalDate.now();
        this.active = true;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getOpenedOn() {
        return openedOn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format(
                "%-12d %-20s %-10s %-12.2f %-10s %-12s",
                accountNumber, holderName, accountType, balance,
                active ? "ACTIVE" : "CLOSED", openedOn
        );
    }
}
