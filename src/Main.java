import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 * ---------
 * Entry point of the Student Management System.
 * Provides a menu-driven console interface for the user to:
 *   1. Add Student
 *   2. View All Students
 *   3. Search Student by ID
 *   4. Update Student
 *   5. Delete Student
 *   6. Exit
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("     STUDENT MANAGEMENT SYSTEM");
        System.out.println("=========================================");

        // Preload a couple of sample records (optional, easy to remove)
        studentService.addStudent(new Student(101, "Aarav Sharma", 88.5));
        studentService.addStudent(new Student(102, "Priya Patel", 92.0));

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting... Thank you for using Student Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n----------------- MENU -----------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.println("------------------------------------------");
    }

    // ----- Feature 1: Add Student -----
    private static void addStudent() {
        System.out.println("\n-- Add New Student --");
        int id = readInt("Enter Student ID: ");
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        double marks = readDouble("Enter Marks (0-100): ");

        Student student = new Student(id, name, marks);
        boolean added = studentService.addStudent(student);

        if (added) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Error: A student with ID " + id + " already exists.");
        }
    }

    // ----- Feature 2: View All Students -----
    private static void viewStudents() {
        System.out.println("\n-- All Students --");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.printf("%-6s %-20s %-10s %-5s%n", "ID", "Name", "Marks", "Grade");
        System.out.println("--------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Total Students: " + studentService.getTotalStudents());
    }

    // ----- Feature 3: Search Student by ID -----
    private static void searchStudent() {
        System.out.println("\n-- Search Student --");
        int id = readInt("Enter Student ID to search: ");
        Student student = studentService.findById(id);

        if (student != null) {
            System.out.printf("%-6s %-20s %-10s %-5s%n", "ID", "Name", "Marks", "Grade");
            System.out.println(student);
        } else {
            System.out.println("No student found with ID " + id);
        }
    }

    // ----- Feature 4: Update Student (bonus, beyond original scope) -----
    private static void updateStudent() {
        System.out.println("\n-- Update Student --");
        int id = readInt("Enter Student ID to update: ");

        if (studentService.findById(id) == null) {
            System.out.println("No student found with ID " + id);
            return;
        }

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        double marks = readDouble("Enter New Marks: ");

        studentService.updateStudent(id, name, marks);
        System.out.println("Student updated successfully!");
    }

    // ----- Feature 5: Delete Student (bonus, beyond original scope) -----
    private static void deleteStudent() {
        System.out.println("\n-- Delete Student --");
        int id = readInt("Enter Student ID to delete: ");
        boolean deleted = studentService.deleteById(id);

        if (deleted) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("No student found with ID " + id);
        }
    }

    // ----- Helper methods for safe input reading -----
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume leftover newline
        return value;
    }
}
