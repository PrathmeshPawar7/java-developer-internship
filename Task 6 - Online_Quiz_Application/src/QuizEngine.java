import java.util.List;
import java.util.Scanner;

/**
 * Runs a single quiz session: presents each question, captures the user's
 * answer, validates input, and tallies score. Keeping this separate from
 * Question/Result follows single-responsibility — this class only orchestrates.
 */
public class QuizEngine {

    private final Scanner scanner;

    public QuizEngine(Scanner scanner) {
        this.scanner = scanner;
    }

    public Result runQuiz(String playerName, Category category, List<Question> questions) {
        int correctCount = 0;
        int pointsEarned = 0;
        int pointsPossible = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            pointsPossible += q.getPoints();

            System.out.println("\nQuestion " + (i + 1) + " of " + questions.size()
                    + "  [" + q.getDifficulty() + ", " + q.getPoints() + " pts]");
            System.out.println(q.getQuestionText());

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println("  " + (char) ('A' + j) + ") " + options[j]);
            }

            int chosenIndex = captureAnswer();

            if (chosenIndex == -1) {
                System.out.println("Skipped. Correct answer was: " + q.getCorrectOptionLetter());
                continue;
            }

            if (q.isCorrect(chosenIndex)) {
                System.out.println("Correct!");
                correctCount++;
                pointsEarned += q.getPoints();
            } else {
                System.out.println("Incorrect. Correct answer was: " + q.getCorrectOptionLetter());
            }
        }

        return new Result(playerName, category, questions.size(), correctCount, pointsEarned, pointsPossible);
    }

    /**
     * Reads and validates the user's option choice.
     * Accepts A-D (or a-d) and loops on invalid input so a bad keystroke
     * never crashes the quiz. Typing 'S' skips the question.
     * Returns 0-3 for a valid choice, or -1 if skipped.
     */
    private int captureAnswer() {
        while (true) {
            System.out.print("Your answer (A/B/C/D, or S to skip): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("S")) {
                return -1;
            }
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                return input.charAt(0) - 'A';
            }
            System.out.println("Invalid input. Please enter A, B, C, D, or S to skip.");
        }
    }
}
