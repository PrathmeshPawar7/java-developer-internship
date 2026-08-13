package util;

/**
 * Central place for all payroll percentages and thresholds so the
 * calculation logic in the service layer doesn't scatter magic numbers.
 * Changing the salary structure or deduction rules means editing this
 * one file only.
 */
public final class PayrollConstants {

    private PayrollConstants() {
        // utility class - no instances
    }

    // Allowances, expressed as a percentage of basic salary
    public static final double HRA_PERCENT_OF_BASIC = 0.40;
    public static final double DA_PERCENT_OF_BASIC = 0.10;
    public static final double SPECIAL_ALLOWANCE_PERCENT_OF_BASIC = 0.15;

    // Provident Fund: 12% of basic, capped per EPFO wage-ceiling rules
    public static final double PF_PERCENT_OF_BASIC = 0.12;
    public static final double PF_MONTHLY_CAP = 1800.0;

    // Professional tax (flat, state-level slab simplified to one tier)
    public static final double PROFESSIONAL_TAX_MONTHLY = 200.0;
    public static final double PROFESSIONAL_TAX_EXEMPT_GROSS_THRESHOLD = 15000.0;
}
