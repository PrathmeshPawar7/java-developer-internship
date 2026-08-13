package util;

/**
 * Calculates income tax (TDS) using a simplified progressive slab system,
 * modeled on India's new tax regime slabs. Kept as its own class (rather
 * than inline in the service) so the slab table can be unit-tested and
 * updated independently of payroll orchestration logic.
 */
public final class TaxCalculator {

    private TaxCalculator() {
        // utility class - no instances
    }

    // Annual income slabs and their marginal rates.
    private static final double[] SLAB_LIMITS = {
            300_000, 600_000, 900_000, 1_200_000, 1_500_000
    };
    private static final double[] SLAB_RATES = {
            0.00, 0.05, 0.10, 0.15, 0.20, 0.30
    };

    /**
     * Computes annual income tax on the given annual taxable income using
     * progressive slabs (each slab's rate applies only to the portion of
     * income within that slab, not the whole amount).
     */
    public static double calculateAnnualTax(double annualIncome) {
        if (annualIncome <= 0) {
            return 0.0;
        }

        double tax = 0.0;
        double previousLimit = 0.0;

        for (int i = 0; i < SLAB_LIMITS.length; i++) {
            if (annualIncome > SLAB_LIMITS[i]) {
                tax += (SLAB_LIMITS[i] - previousLimit) * SLAB_RATES[i];
                previousLimit = SLAB_LIMITS[i];
            } else {
                tax += (annualIncome - previousLimit) * SLAB_RATES[i];
                return round(tax);
            }
        }
        // Income exceeds the highest defined slab limit - remainder taxed at top rate
        tax += (annualIncome - previousLimit) * SLAB_RATES[SLAB_RATES.length - 1];
        return round(tax);
    }

    /**
     * Computes the monthly TDS deduction by annualizing the monthly gross
     * salary, calculating annual tax, and dividing by 12.
     */
    public static double calculateMonthlyTds(double monthlyGrossSalary) {
        double annualIncome = monthlyGrossSalary * 12;
        double annualTax = calculateAnnualTax(annualIncome);
        return round(annualTax / 12);
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
