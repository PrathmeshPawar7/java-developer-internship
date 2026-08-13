# Java Online Quiz Application (Internship Project)

A console-based, object-oriented quiz system in Java demonstrating MCQ delivery,
weighted score calculation, result evaluation, and persistent score history.

## How to Run

You need a JDK (Java 8+) installed — not just a JRE.

```bash
cd src
javac *.java
java QuizApplication
```

## Project Structure & Design

| Class             | Responsibility                                                        |
|--------------------|------------------------------------------------------------------------|
| `Question.java`    | Immutable data model for one MCQ (text, 4 options, correct answer)    |
| `Category.java`    | Enum of quiz categories (Java, CS, General Knowledge)                 |
| `Difficulty.java`  | Enum of difficulty levels (EASY / MEDIUM / HARD / ALL)                |
| `QuestionBank.java`| Repository: seeded question data + filtering by category/difficulty   |
| `QuizEngine.java`  | Core logic: presents questions, validates input, tallies score        |
| `Result.java`      | Value object: score, percentage, letter grade, log formatting         |
| `ScoreHistory.java`| Simple file-based persistence (`quiz_score_history.txt`)              |
| `QuizApplication.java` | Main class / console UI — menus for name, category, difficulty, count |

This separation into **data model → repository → business logic → persistence →
UI/controller** is the key "advanced" design idea the task asks you to show —
it's the same layering used in real applications, just without a database or
web framework.

## Key Features Implemented

- **MCQ system** — 4-option multiple choice questions across 3 categories and
  3 difficulty levels (18 seed questions total, easy to extend).
- **Score calculation** — points are weighted by difficulty (Easy=5, Medium=10,
  Hard=15), so harder questions count for more, not just a flat "1 point each".
- **Result evaluation** — percentage score mapped to a letter grade (A+ through F).
- **Input validation** — invalid keystrokes (e.g. typing "Z") re-prompt instead
  of crashing; users can also skip a question.
- **Score history (bonus)** — every attempt is appended to a local text file
  and can be viewed from the menu, so progress persists across runs.
- **Randomization** — questions are shuffled each run via `Collections.shuffle`.

## Extending the Project

- Swap `QuestionBank`'s hardcoded list for reading from a CSV/JSON file or a
  real database — no other class needs to change (this is why the repository
  layer exists).
- Add a timer per question by wrapping `captureAnswer()` in `QuizEngine` with
  a `Scanner` read on a separate thread and a deadline.
- Add a GUI (JavaFX/Swing) by replacing `QuizApplication`'s console I/O with UI
  event handlers — `QuizEngine`, `Question`, `Result`, and `QuestionBank` need
  no changes at all, since they don't know about the console.

## Skills Demonstrated

- OOP design: encapsulation, immutability, single-responsibility classes
- Enums for fixed sets of constants (`Category`, `Difficulty`)
- Collections (`ArrayList`, `List`, `Collections.shuffle`)
- File I/O (`FileWriter`, `BufferedReader`) for persistence
- Defensive input validation with loops instead of assuming clean input
- `LocalDateTime` / `DateTimeFormatter` for timestamped logs
