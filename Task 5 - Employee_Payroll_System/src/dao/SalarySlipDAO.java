package dao;

import model.SalarySlip;

import java.time.Month;
import java.util.List;

/**
 * Data access contract for SalarySlip (payroll record) persistence.
 */
public interface SalarySlipDAO {
    void addSlip(SalarySlip slip);
    List<SalarySlip> getAllSlips();
    List<SalarySlip> getSlipsByEmployee(int employeeId);
    boolean slipExists(int employeeId, Month month, int year);
}
