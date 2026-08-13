import java.util.List;
import java.util.Scanner;

/**
 * Entry point. Provides a console menu to select category and difficulty,
 * runs the quiz, displays results, and offers a score-history view.
 *
 * Application design notes (for internship writeup):
 *  - Question       : immutable data model for one MCQ
 *  - QuestionBank    : repository / data-access layer
 *  - QuizEngine      : business logic — presenting questions & scoring
 *  - Result          : value object summarizing an attempt + grading rules
 *  - ScoreHistory    : simple file-based persistence layer
 *  - QuizApplication : presentation layer / controller (this class)
 * This separation (data / logic / persistence / UI) is the core "advanced"
 * design idea the task is asking you to demonstrate.
 */
public class QuizApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuestionBank bank = new QuestionBank();
        ScoreHistory history = new ScoreHistory();

        printBanner();

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Guest";
        }

        boolean playAgain = true;
        while (playAgain) {
            Category category = chooseCategory(scanner);
            Difficulty difficulty = chooseDifficulty(scanner);
            int questionCount = chooseQuestionCount(scanner, bank, category, difficulty);

            List<Question> questions = bank.getQuestions(category, difficulty, questionCount);

            if (questions.isEmpty()) {
                System.out.println("No questions available for that combination. Try again.");
            } else {
                QuizEngine engine = new QuizEngine(scanner);
                Result result = engine.runQuiz(playerName, category, questions);

                System.out.println(result);
                history.save(result);
            }

            System.out.print("\nView score history? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                history.printAll();
            }

            System.out.print("\nPlay another quiz? (y/n): ");
            playAgain = scanner.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.println("\nThanks for playing, " + playerName + "! Goodbye.");
        scanner.close();
    }

    private static void printBanner() {
        System.out.println("==================================================");
        System.out.println("           JAVA ONLINE QUIZ APPLICATION          ");
        System.out.println("==================================================");
    }

    private static Category chooseCategory(Scanner scanner) {
        Category[] categories = Category.values();
        while (true) {
            System.out.println("\nSelect a category:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println("  " + (i + 1) + ") " + categories[i].getDisplayName());
            }
            System.out.print("Enter choice number: ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= categories.length) {
                    return categories[choice - 1];
                }
            } catch (NumberFormatException ignored) {
                // fall through to error message below
            }
            System.out.println("Invalid choice, please try again.");
        }
    }

    private static Difficulty chooseDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("\nSelect difficulty:");
            System.out.println("  1) Easy");
            System.out.println("  2) Medium");
            System.out.println("  3) Hard");
            System.out.println("  4) All / Mixed");
            System.out.print("Enter choice number: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": return Difficulty.EASY;
                case "2": return Difficulty.MEDIUM;
                case "3": return Difficulty.HARD;
                case "4": return Difficulty.ALL;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static int chooseQuestionCount(Scanner scanner, QuestionBank bank,
                                            Category category, Difficulty difficulty) {
        int available = bank.getQuestions(category, difficulty, 0).size();
        if (available == 0) {
            return 0;
        }
        while (true) {
            System.out.print("\nHow many questions would you like (1-" + available + ")? ");
            String input = scanner.nextLine().trim();
            try {
                int count = Integer.parseInt(input);
                if (count >= 1 && count <= available) {
                    return count;
                }
            } catch (NumberFormatException ignored) {
                // fall through
            }
            System.out.println("Invalid number, please try again.");
        }
    }
}
