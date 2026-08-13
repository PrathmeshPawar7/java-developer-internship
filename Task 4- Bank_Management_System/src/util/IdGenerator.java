package util;

/**
 * Simple thread-unsafe sequential ID generator for demo/console purposes.
 * In a real system this responsibility belongs to the database
 * (auto-increment / sequence), which is why it's isolated here behind
 * its own small class rather than scattered as static counters.
 */
public class IdGenerator {

    private int nextAccountNumber = 100001;
    private int nextTransactionId = 1;

    public int nextAccountNumber() {
        return nextAccountNumber++;
    }

    public int nextTransactionId() {
        return nextTransactionId++;
    }
}
