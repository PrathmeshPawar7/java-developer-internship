# Number Guessing Game (Java Console App)

A console-based Number Guessing Game built for the internship task.

## Features
- Random number generation (`java.util.Random`)
- 3 difficulty levels — Easy / Medium / Hard (different range & attempt limits)
- Higher/Lower hints after every guess
- Attempt counter
- Score calculation per round + session high score
- Play again loop
- Input validation (won't crash on non-numeric input)

## Concepts demonstrated
Loops (`while`), conditionals (`if/else`, `switch`), methods, `Scanner`,
`Random`, basic scoring logic — good talking points for an interview.

---

## How to Run

### Option 1: IntelliJ IDEA
1. Open IntelliJ → **File > New > Project from Existing Sources**.
2. Select the `NumberGuessingGame` folder (containing `NumberGuessingGame.java`).
3. Choose **Create project from existing sources**, keep defaults, click **Finish**.
4. In the Project pane, right-click `NumberGuessingGame.java` → **Run 'NumberGuessingGame.main()'**.

### Option 2: Eclipse
1. **File > New > Java Project** → name it (e.g. `NumberGuessingGame`), click **Finish**.
2. Right-click the `src` folder → **Import > File System** → point to this folder and import `NumberGuessingGame.java`.
   (Or just drag-and-drop the `.java` file into `src` in the Package Explorer.)
3. Right-click `NumberGuessingGame.java` → **Run As > Java Application**.

### Option 3: Command Line (no IDE needed)
Make sure Java (JDK) is installed — check with:
```
java -version
javac -version
```
Then, from inside the `NumberGuessingGame` folder:
```
javac NumberGuessingGame.java
java NumberGuessingGame
```

---

## Sample Output
```
=========================================
      WELCOME TO NUMBER GUESSING GAME
=========================================
Choose a difficulty level:
1. Easy   (1-50,  10 attempts)
2. Medium (1-100,  7 attempts)
3. Hard   (1-200,  5 attempts)
Enter choice (1-3): 2

I'm thinking of a number between 1 and 100.
You have 7 attempts. Good luck!

Attempt 1/7 - Enter your guess: 50
Too HIGH. Try a lower number. (6 attempt(s) left)

Attempt 2/7 - Enter your guess: 25
Correct! You guessed it in 2 attempt(s).

---------- RESULT ----------
You WON! The number was 25.
Attempts used: 2/7
Score for this round: 86 points
New session high score!
-----------------------------

Play again? (y/n):
```


