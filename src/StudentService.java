import java.util.ArrayList;
import java.util.List;

/**
 * StudentService.java
 * --------------------
 * Service / DAO-style layer that manages the actual student data.
 * Demonstrates use of the Collections Framework (ArrayList).
 * Separating this logic from the Main class also shows good
 * layered design, which interviewers like to see.
 */
public class StudentService {

    // In-memory data store using ArrayList (Collections Framework)
    private List<Student> studentList = new ArrayList<>();

    /**
     * Adds a new student to the list.
     * Prevents duplicate IDs.
     */
    public boolean addStudent(Student student) {
        if (findById(student.getId()) != null) {
            return false; // Duplicate ID found
        }
        studentList.add(student);
        return true;
    }

    /**
     * Returns the full list of students.
     */
    public List<Student> getAllStudents() {
        return studentList;
    }

    /**
     * Searches for a student by ID.
     * Returns the Student object if found, otherwise null.
     */
    public Student findById(int id) {
        for (Student s : studentList) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Deletes a student by ID.
     * Returns true if deletion was successful.
     */
    public boolean deleteById(int id) {
        Student s = findById(id);
        if (s != null) {
            studentList.remove(s);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing student's name and marks.
     */
    public boolean updateStudent(int id, String newName, double newMarks) {
        Student s = findById(id);
        if (s != null) {
            s.setName(newName);
            s.setMarks(newMarks);
            return true;
        }
        return false;
    }

    public int getTotalStudents() {
        return studentList.size();
    }
}

