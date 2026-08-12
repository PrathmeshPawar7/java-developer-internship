
    /**
     * Student.java
     * ------------
     * Model class representing a Student entity.
     * Demonstrates core OOP concepts: Encapsulation (private fields + getters/setters),
     * Constructors, and method overriding (toString, equals).
     */
    public class Student {

        // ----- Attributes (private for Encapsulation) -----
        private int id;
        private String name;
        private double marks;

        // ----- Constructor -----
        public Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }

        // ----- Getters and Setters -----
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getMarks() {
            return marks;
        }

        public void setMarks(double marks) {
            this.marks = marks;
        }

        // ----- Grade calculation (basic business logic) -----
        public String getGrade() {
            if (marks >= 90) return "A+";
            else if (marks >= 80) return "A";
            else if (marks >= 70) return "B";
            else if (marks >= 60) return "C";
            else if (marks >= 50) return "D";
            else return "F";
        }

        // ----- toString() override for clean display -----
        @Override
        public String toString() {
            return String.format("%-6d %-20s %-10.2f %-5s", id, name, marks, getGrade());
        }
    }


