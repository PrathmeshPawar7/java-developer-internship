package util;

/**
 * Simple thread-unsafe sequential ID generator for demo/console purposes.
 * In a real system this responsibility belongs to the database
 * (auto-increment / sequence).
 */
public class IdGenerator {

    private int nextEmployeeId = 1001;
    private int nextSlipId = 1;

    public int nextEmployeeId() {
        return nextEmployeeId++;
    }

    public int nextSlipId() {
        return nextSlipId++;
    }
}
