package dao;

import model.IssueRecord;
import model.IssueStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of IssueRecordDAO using a HashMap keyed by recordId.
 */
public class IssueRecordDAOImpl implements IssueRecordDAO {

    private final Map<Integer, IssueRecord> recordStore = new LinkedHashMap<>();

    @Override
    public void addRecord(IssueRecord record) {
        recordStore.put(record.getRecordId(), record);
    }

    @Override
    public IssueRecord getRecordById(int recordId) {
        return recordStore.get(recordId);
    }

    @Override
    public List<IssueRecord> getAllRecords() {
        return new ArrayList<>(recordStore.values());
    }

    @Override
    public List<IssueRecord> getRecordsByMember(int memberId) {
        List<IssueRecord> result = new ArrayList<>();
        for (IssueRecord r : recordStore.values()) {
            if (r.getMemberId() == memberId) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public IssueRecord getActiveRecordForBook(int bookId) {
        for (IssueRecord r : recordStore.values()) {
            if (r.getBookId() == bookId && r.getStatus() == IssueStatus.ISSUED) {
                return r;
            }
        }
        return null;
    }
}
