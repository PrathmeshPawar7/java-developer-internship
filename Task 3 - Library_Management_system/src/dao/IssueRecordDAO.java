package dao;

import model.IssueRecord;

import java.util.List;

/**
 * Data access contract for IssueRecord (issue/return transaction) persistence.
 */
public interface IssueRecordDAO {
    void addRecord(IssueRecord record);
    IssueRecord getRecordById(int recordId);
    List<IssueRecord> getAllRecords();
    List<IssueRecord> getRecordsByMember(int memberId);
    IssueRecord getActiveRecordForBook(int bookId);
}
