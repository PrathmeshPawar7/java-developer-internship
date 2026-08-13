package util;

/**
 * Simple thread-unsafe sequential ID generator for demo/console purposes.
 * In a real system this responsibility belongs to the database (auto-increment / sequence).
 */
public class IdGenerator {

    private int nextBookId = 1;
    private int nextMemberId = 1;
    private int nextRecordId = 1;

    public int nextBookId() {
        return nextBookId++;
    }

    public int nextMemberId() {
        return nextMemberId++;
    }

    public int nextRecordId() {
        return nextRecordId++;
    }
}
