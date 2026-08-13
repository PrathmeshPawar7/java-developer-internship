/**
 * Represents a single multiple-choice question.
 * Immutable value object — once built, a Question cannot be altered,
 * which keeps the question bank safe to share across quiz sessions.
 */
public class Question {

    private final String questionText;
    private final String[] options;      // exactly 4 options: A, B, C, D
    private final int correctOptionIndex; // 0-based index into options[]
    private final Category category;
    private final Difficulty difficulty;

    public Question(String questionText, String[] options, int correctOptionIndex,
                     Category category, Difficulty difficulty) {
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("Each question must have exactly 4 options.");
        }
        if (correctOptionIndex < 0 || correctOptionIndex > 3) {
            throw new IllegalArgumentException("correctOptionIndex must be between 0 and 3.");
        }
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectOptionLetter() {
        return (char) ('A' + correctOptionIndex);
    }

    public boolean isCorrect(int chosenIndex) {
        return chosenIndex == correctOptionIndex;
    }

    public Category getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    /** Points awarded for a correct answer — harder questions are worth more. */
    public int getPoints() {
        switch (difficulty) {
            case EASY:   return 5;
            case MEDIUM: return 10;
            case HARD:   return 15;
            default:     return 5;
        }
    }
}
