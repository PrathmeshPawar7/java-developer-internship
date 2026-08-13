import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures the outcome of one quiz attempt: raw score, percentage,
 * letter grade, and a timestamp so it can be logged to score history.
 */
public class Result {

    private final String playerName;
    private final Category category;
    private final int totalQuestions;
    private final int correctAnswers;
    private final int totalPointsEarned;
    private final int totalPointsPossible;
    private final LocalDateTime attemptedOn;

    public Result(String playerName, Category category, int totalQuestions, int correctAnswers,
                  int totalPointsEarned, int totalPointsPossible) {
        this.playerName = playerName;
        this.category = category;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.totalPointsEarned = totalPointsEarned;
        this.totalPointsPossible = totalPointsPossible;
        this.attemptedOn = LocalDateTime.now();
    }

    public double getPercentage() {
        if (totalPointsPossible == 0) return 0.0;
        return (totalPointsEarned * 100.0) / totalPointsPossible;
    }

    /** Standard grade bands — easy to tune without touching calling code. */
    public String getGrade() {
        double pct = getPercentage();
        if (pct >= 90) return "A+ (Outstanding)";
        if (pct >= 75) return "A (Excellent)";
        if (pct >= 60) return "B (Good)";
        if (pct >= 45) return "C (Fair)";
        if (pct >= 30) return "D (Needs Improvement)";
        return "F (Fail)";
    }

    public String getPlayerName() {
        return playerName;
    }

    public Category getCategory() {
        return category;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public int getTotalPointsPossible() {
        return totalPointsPossible;
    }

    /** Formats a single line suitable for appending to the score-history log file. */
    public String toLogLine() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %-15s | %-20s | %2d/%2d correct | %3d/%3d pts | %5.1f%% | %s",
                attemptedOn.format(fmt), playerName, category.getDisplayName(),
                correctAnswers, totalQuestions, totalPointsEarned, totalPointsPossible,
                getPercentage(), getGrade());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== QUIZ RESULT ==========\n");
        sb.append("Player       : ").append(playerName).append("\n");
        sb.append("Category     : ").append(category.getDisplayName()).append("\n");
        sb.append("Correct      : ").append(correctAnswers).append(" / ").append(totalQuestions).append("\n");
        sb.append("Score        : ").append(totalPointsEarned).append(" / ").append(totalPointsPossible).append(" points\n");
        sb.append(String.format("Percentage   : %.1f%%%n", getPercentage()));
        sb.append("Grade        : ").append(getGrade()).append("\n");
        sb.append("==================================\n");
        return sb.toString();
    }
}
