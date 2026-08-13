package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a single issue/return transaction linking a Book and a Member.
 */
public class IssueRecord {

    public static final int LOAN_PERIOD_DAYS = 14;
    public static final double FINE_PER_DAY = 5.0;

    private int recordId;
    private int bookId;
    private int memberId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private IssueStatus status;

    public IssueRecord(int recordId, int bookId, int memberId) {
        this.recordId = recordId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.status = IssueStatus.ISSUED;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    /**
     * Marks this record as returned today and calculates any applicable fine.
     * @return the fine amount (0 if returned on/before due date)
     */
    public double markReturned() {
        this.returnDate = LocalDate.now();
        this.status = IssueStatus.RETURNED;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate > 0 ? daysLate * FINE_PER_DAY : 0.0;
    }

    public boolean isOverdue() {
        return status == IssueStatus.ISSUED && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return String.format(
                "%-5d %-10d %-10d %-12s %-12s %-12s %-10s",
                recordId, bookId, memberId, issueDate, dueDate,
                returnDate == null ? "-" : returnDate, status
        );
    }
}
