import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Persists quiz results to a local text file so scores survive between runs.
 * Demonstrates basic file I/O — a common "advanced" ask for internship projects.
 */
public class ScoreHistory {

    private static final String HISTORY_FILE = "quiz_score_history.txt";

    public void save(Result result) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.println(result.toLogLine());
        } catch (IOException e) {
            System.out.println("Could not save score history: " + e.getMessage());
        }
    }

    public void printAll() {
        java.io.File file = new java.io.File(HISTORY_FILE);
        if (!file.exists()) {
            System.out.println("No past attempts recorded yet.");
            return;
        }
        System.out.println("\n--------- SCORE HISTORY ---------");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean any = false;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                any = true;
            }
            if (!any) {
                System.out.println("No past attempts recorded yet.");
            }
        } catch (IOException e) {
            System.out.println("Could not read score history: " + e.getMessage());
        }
        System.out.println("----------------------------------");
    }
}
