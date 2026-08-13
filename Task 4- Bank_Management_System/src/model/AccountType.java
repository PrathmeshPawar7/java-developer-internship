package model;

/**
 * Type of bank account. Each type carries its own minimum-balance rule,
 * enforced in the service layer.
 */
public enum AccountType {
    SAVINGS(500.0),
    CURRENT(0.0);

    private final double minimumBalance;

    AccountType(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}
