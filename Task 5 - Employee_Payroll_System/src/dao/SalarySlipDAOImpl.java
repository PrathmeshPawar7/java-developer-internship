package dao;

import model.SalarySlip;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of SalarySlipDAO using a HashMap keyed by slipId.
 * Preserves insertion order via LinkedHashMap so payroll history displays chronologically.
 */
public class SalarySlipDAOImpl implements SalarySlipDAO {

    private final Map<Integer, SalarySlip> slipStore = new LinkedHashMap<>();

    @Override
    public void addSlip(SalarySlip slip) {
        slipStore.put(slip.getSlipId(), slip);
    }

    @Override
    public List<SalarySlip> getAllSlips() {
        return new ArrayList<>(slipStore.values());
    }

    @Override
    public List<SalarySlip> getSlipsByEmployee(int employeeId) {
        List<SalarySlip> result = new ArrayList<>();
        for (SalarySlip s : slipStore.values()) {
            if (s.getEmployeeId() == employeeId) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public boolean slipExists(int employeeId, Month month, int year) {
        for (SalarySlip s : slipStore.values()) {
            if (s.getEmployeeId() == employeeId && s.getMonth() == month && s.getYear() == year) {
                return true;
            }
        }
        return false;
    }
}
