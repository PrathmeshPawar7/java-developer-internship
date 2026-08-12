import java.util.Random;
import java.util.Scanner;

/**
 * Number Guessing Game
 * -----------------------------------------
 * An interactive console-based game where the user tries to guess
 * a randomly generated number within a limited number of attempts.
 *
 * Features:
 *  - Random number generation (java.util.Random)
 *  - Difficulty levels (Easy / Medium / Hard) -> changes range & attempts
 *  - Higher/Lower hints after every guess
 *  - Attempt counter and score calculation
 *  - Play again loop with a running high score for the session
 *
 * Concepts used: loops, conditionals, methods, Scanner, Random, switch-case
 *
 * Author: Prathmesh Pawar
 */
public class NumberGuessingGame {

    // Scanner is created once and reused for the whole program
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    // Tracks the best (lowest) score achieved in this session
    private static int highScore = 0;
    private static boolean highScoreSet = false;

    public static void main(String[] args) {
        displayWelcome();

        boolean playAgain = true;
        int roundsPlayed = 0;

        while (playAgain) {
            roundsPlayed++;
            playRound(roundsPlayed);
            playAgain = askPlayAgain();
        }

        displayFinalSummary(roundsPlayed);
        scanner.close();
    }

    /** Prints a welcome banner. */
    private static void displayWelcome() {
        System.out.println("=========================================");
        System.out.println("      WELCOME TO NUMBER GUESSING GAME    ");
        System.out.println("=========================================");
        System.out.println("Try to guess the secret number.");
        System.out.println("After every guess I will tell you if the");
        System.out.println("secret number is HIGHER or LOWER.\n");
    }

    /** Plays a single round: difficulty selection -> guessing loop -> result. */
    private static void playRound(int roundNumber) {
        System.out.println("---------- ROUND " + roundNumber + " ----------");

        int[] settings = selectDifficulty(); // [0] = maxRange, [1] = maxAttempts
        int maxRange = settings[0];
        int maxAttempts = settings[1];

        int secretNumber = random.nextInt(maxRange) + 1; // 1 to maxRange
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("\nI'm thinking of a number between 1 and " + maxRange + ".");
        System.out.println("You have " + maxAttempts + " attempts. Good luck!\n");

        while (attempts < maxAttempts && !guessedCorrectly) {
            int remaining = maxAttempts - attempts;
            System.out.print("Attempt " + (attempts + 1) + "/" + maxAttempts
                    + " - Enter your guess: ");

            int guess = readValidInt();
            attempts++;

            if (guess == secretNumber) {
                guessedCorrectly = true;
                System.out.println("Correct! You guessed it in " + attempts + " attempt(s).\n");
            } else if (guess < secretNumber) {
                System.out.println("Too LOW. Try a higher number. ("
                        + (remaining - 1) + " attempt(s) left)\n");
            } else {
                System.out.println("Too HIGH. Try a lower number. ("
                        + (remaining - 1) + " attempt(s) left)\n");
            }
        }

        displayRoundResult(guessedCorrectly, secretNumber, attempts, maxAttempts);
    }

    /** Lets the user pick a difficulty and returns {range, attempts}. */
    private static int[] selectDifficulty() {
        System.out.println("Choose a difficulty level:");
        System.out.println("1. Easy   (1-50,  10 attempts)");
        System.out.println("2. Medium (1-100,  7 attempts)");
        System.out.println("3. Hard   (1-200,  5 attempts)");
        System.out.print("Enter choice (1-3): ");

        int choice = readValidInt();

        switch (choice) {
            case 1:
                return new int[]{50, 10};
            case 3:
                return new int[]{200, 5};
            case 2:
            default:
                if (choice != 2) {
                    System.out.println("Invalid choice, defaulting to Medium.\n");
                }
                return new int[]{100, 7};
        }
    }

    /** Reads an integer safely, re-prompting on invalid (non-numeric) input. */
    private static int readValidInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid whole number: ");
            scanner.next(); // discard invalid token
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear the trailing newline
        return value;
    }

    /** Shows the outcome of the round and updates the score/high score. */
    private static void displayRoundResult(boolean won, int secretNumber,
                                            int attempts, int maxAttempts) {
        System.out.println("---------- RESULT ----------");
        if (won) {
            int score = calculateScore(attempts, maxAttempts);
            System.out.println("You WON! The number was " + secretNumber + ".");
            System.out.println("Attempts used: " + attempts + "/" + maxAttempts);
            System.out.println("Score for this round: " + score + " points");

            if (!highScoreSet || score > highScore) {
                highScore = score;
                highScoreSet = true;
                System.out.println("New session high score!");
            }
        } else {
            System.out.println("You LOST. You've used all " + maxAttempts + " attempts.");
            System.out.println("The secret number was: " + secretNumber);
        }
        System.out.println("-----------------------------\n");
    }

    /**
     * Simple scoring formula: fewer attempts relative to the limit = higher score.
     * Max possible score is 100 (achieved by guessing correctly on the first try).
     */
    private static int calculateScore(int attempts, int maxAttempts) {
        double efficiency = 1.0 - ((double) (attempts - 1) / maxAttempts);
        return (int) Math.round(efficiency * 100);
    }

    /** Asks whether the user wants to play another round. */
    private static boolean askPlayAgain() {
        System.out.print("Play again? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.startsWith("y");
    }

    /** Prints a summary once the user chooses to stop playing. */
    private static void displayFinalSummary(int roundsPlayed) {
        System.out.println("=========================================");
        System.out.println("Thanks for playing! Rounds played: " + roundsPlayed);
        if (highScoreSet) {
            System.out.println("Your best score this session: " + highScore + " points");
        }
        System.out.println("=========================================");
    }
}
