import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central repository of all available questions.
 * In a production system this would load from a database or JSON/CSV file;
 * here it is seeded in-memory so the project runs with zero external setup.
 */
public class QuestionBank {

    private final List<Question> allQuestions;

    public QuestionBank() {
        allQuestions = new ArrayList<>();
        seedJavaQuestions();
        seedComputerScienceQuestions();
        seedGeneralKnowledgeQuestions();
    }

    private void seedJavaQuestions() {
        allQuestions.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"},
                1, Category.JAVA_PROGRAMMING, Difficulty.EASY));

        allQuestions.add(new Question(
                "Which collection class allows duplicate elements and maintains insertion order?",
                new String[]{"HashSet", "TreeSet", "ArrayList", "HashMap"},
                2, Category.JAVA_PROGRAMMING, Difficulty.EASY));

        allQuestions.add(new Question(
                "What is the default value of a boolean instance variable in Java?",
                new String[]{"true", "false", "0", "null"},
                1, Category.JAVA_PROGRAMMING, Difficulty.EASY));

        allQuestions.add(new Question(
                "Which of these is NOT a valid access modifier in Java?",
                new String[]{"private", "protected", "public", "internal"},
                3, Category.JAVA_PROGRAMMING, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "What does the 'final' keyword do when applied to a variable?",
                new String[]{"Makes it static", "Prevents reassignment", "Deletes it after use", "Makes it thread-local"},
                1, Category.JAVA_PROGRAMMING, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "Which interface must a class implement to be used with Java's enhanced for-loop?",
                new String[]{"Comparable", "Iterable", "Serializable", "Cloneable"},
                1, Category.JAVA_PROGRAMMING, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "In Java, what happens when a checked exception is neither caught nor declared?",
                new String[]{"Runtime warning only", "Compile-time error", "Silently ignored", "Auto-caught by JVM"},
                1, Category.JAVA_PROGRAMMING, Difficulty.HARD));

        allQuestions.add(new Question(
                "Which of the following best describes Java's garbage collection?",
                new String[]{"Manual memory deallocation", "Automatic reclamation of unreachable objects",
                        "A compiler optimization only", "A way to delete files"},
                1, Category.JAVA_PROGRAMMING, Difficulty.HARD));
    }

    private void seedComputerScienceQuestions() {
        allQuestions.add(new Question(
                "What is the time complexity of binary search on a sorted array?",
                new String[]{"O(n)", "O(log n)", "O(n log n)", "O(1)"},
                1, Category.COMPUTER_SCIENCE, Difficulty.EASY));

        allQuestions.add(new Question(
                "Which data structure uses LIFO (Last In, First Out) ordering?",
                new String[]{"Queue", "Stack", "Linked List", "Graph"},
                1, Category.COMPUTER_SCIENCE, Difficulty.EASY));

        allQuestions.add(new Question(
                "Which sorting algorithm has the best average-case time complexity?",
                new String[]{"Bubble Sort", "Selection Sort", "Quick Sort", "Insertion Sort"},
                2, Category.COMPUTER_SCIENCE, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "What does 'ACID' stand for in database transactions?",
                new String[]{"Atomicity, Consistency, Isolation, Durability",
                        "Accuracy, Control, Integrity, Delivery",
                        "Atomic, Concurrent, Indexed, Distributed",
                        "Access, Compile, Index, Deploy"},
                0, Category.COMPUTER_SCIENCE, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "Which of these is a NoSQL database?",
                new String[]{"MySQL", "PostgreSQL", "MongoDB", "Oracle DB"},
                2, Category.COMPUTER_SCIENCE, Difficulty.EASY));

        allQuestions.add(new Question(
                "What is the worst-case time complexity of Quick Sort?",
                new String[]{"O(n log n)", "O(n^2)", "O(log n)", "O(n)"},
                1, Category.COMPUTER_SCIENCE, Difficulty.HARD));
    }

    private void seedGeneralKnowledgeQuestions() {
        allQuestions.add(new Question(
                "Which is the largest planet in our solar system?",
                new String[]{"Earth", "Saturn", "Jupiter", "Neptune"},
                2, Category.GENERAL_KNOWLEDGE, Difficulty.EASY));

        allQuestions.add(new Question(
                "Who is credited with founding the World Wide Web?",
                new String[]{"Bill Gates", "Tim Berners-Lee", "Steve Jobs", "Alan Turing"},
                1, Category.GENERAL_KNOWLEDGE, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "What is the capital city of Australia?",
                new String[]{"Sydney", "Melbourne", "Canberra", "Perth"},
                2, Category.GENERAL_KNOWLEDGE, Difficulty.MEDIUM));

        allQuestions.add(new Question(
                "Which gas do plants primarily absorb from the atmosphere for photosynthesis?",
                new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"},
                2, Category.GENERAL_KNOWLEDGE, Difficulty.EASY));
    }

    /** Returns questions filtered by category and difficulty (ALL = no difficulty filter), shuffled. */
    public List<Question> getQuestions(Category category, Difficulty difficulty, int limit) {
        List<Question> filtered = new ArrayList<>();
        for (Question q : allQuestions) {
            boolean categoryMatch = (category == null) || q.getCategory() == category;
            boolean difficultyMatch = (difficulty == null) || difficulty == Difficulty.ALL
                    || q.getDifficulty() == difficulty;
            if (categoryMatch && difficultyMatch) {
                filtered.add(q);
            }
        }
        Collections.shuffle(filtered);
        if (limit > 0 && limit < filtered.size()) {
            return new ArrayList<>(filtered.subList(0, limit));
        }
        return filtered;
    }

    public int totalQuestionCount() {
        return allQuestions.size();
    }
}
