package GitHub;

import java.util.*;
import java.io.*;

public class GradeTracker {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile();

        int choice;

        do {
            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Calculate Class Average");
            System.out.println("5. Find Highest Score");
            System.out.println("6. Save Records to File");
            System.out.println("7. Edit Student Marks");
            System.out.println("8. Delete Student");
            System.out.println("9. Exit");
            choice = readInt("Enter your choice: ");

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
                    calculateAverage();
                    break;
                case 5:
                    highestScore();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    editStudent();
                    break;

                case 8:
                    deleteStudent();
                    break;

                case 9:
                    System.out.println("Thank you for using Grade Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 9);

        input.close();
    }

    // Add a new student
    public static void addStudent() {

        System.out.print("Enter student name: ");
        String name = input.nextLine().trim();

        if (name.isEmpty() || name.contains(",")) {
            System.out.println("Invalid student name!");
            return;
        }

        // Check if student already exists
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println("Student already exists!");
                return;
            }
        }

        System.out.println("Enter marks for " + name + ":");
        double math = readMarks("Mathematics");
        double physics = readMarks("Physics");
        double english = readMarks("English");
        double cs = readMarks("Computer Science");
        // Create a Student object
        Student student = new Student(
                name, math, physics, english, cs
        );

        // Add student to ArrayList
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully!");
    }
    // Search for a student
    public static void searchStudent() {

        System.out.print("Enter student name to search: ");
        String name = input.nextLine().trim();

        boolean found = false;

        for (Student student : students) {

            if (student.getName().equalsIgnoreCase(name)) {

                System.out.println("\nStudent Found!");

                System.out.printf(
                        "%-15s %-8s %-8s %-8s %-8s %-8s %-5s%n",
                        "Name", "Math", "Physics",
                        "English", "CS", "Average", "Grade"
                );

                student.displayDetails();

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }

    public static void loadFromFile() {

        File file = new File("grades.txt");

        if (!file.exists()) return;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            students.clear();

            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length != 5) continue;

                try {
                    String name = data[0].trim();
                    double math = Double.parseDouble(data[1].trim());
                    double physics = Double.parseDouble(data[2].trim());
                    double english = Double.parseDouble(data[3].trim());
                    double cs = Double.parseDouble(data[4].trim());

                    if (name.isEmpty() || name.contains(",") ||
                            !validMarks(math) || !validMarks(physics) ||
                            !validMarks(english) || !validMarks(cs)) {
                        continue;
                    }
                    boolean exists = false;
                    for (Student student : students) {
                        if (student.getName().equalsIgnoreCase(name)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        students.add(new Student(
                                name, math, physics, english, cs));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid record.");
                }
            }

            System.out.println("Saved records loaded!");

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public static boolean validMarks(double marks) {
        return Double.isFinite(marks)
                && marks >= 0 && marks <= 100;
    }

    // Read a valid integer
    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Read valid marks

    public static double readMarks(String subject) {
        while (true) {
            System.out.print("Enter " + subject + " marks (0-100): ");

            try {
                double marks = Double.parseDouble(
                        input.nextLine().trim());

                if (validMarks(marks)) {
                    return marks;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                continue;
            }

            System.out.println("Marks must be between 0 and 100.");
        }
    }

    // Add a new student
    public static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.printf(
                "%-20s %-10s %-10s %-10s %-10s %-10s %-5s%n",
                "Name", "Math", "Physics", "English",
                "CS", "Average", "Grade"
        );
        for (Student student : students) {
            student.displayDetails();
        }
    }

    public static void calculateAverage() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        double total = 0;

        for (Student student : students) {
            total += student.getAverage();
        }

        System.out.printf("Class Average: %.2f%n",
                total / students.size());
    }

    public static void highestScore() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        Student highest = students.get(0);

        for (Student student : students) {
            if (student.getAverage() > highest.getAverage()) {
                highest = student;
            }
        }

        System.out.println("Highest Scoring Student:");
        highest.displayDetails();
    }

    public static void editStudent() {
        System.out.print("Enter student name: ");
        String name = input.nextLine().trim();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {

                System.out.println("Enter updated marks:");
                double math = readMarks("Mathematics");
                double physics = readMarks("Physics");
                double english = readMarks("English");
                double cs = readMarks("Computer Science");

                student.setMarks(math, physics, english, cs);
                saveToFile();
                System.out.println("Student updated!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    public static void deleteStudent() {

        System.out.print("Enter student name to delete: ");
        String name = input.nextLine().trim();

        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getName().equalsIgnoreCase(name)) {

                students.remove(i);
                saveToFile();
                System.out.println("Student deleted successfully!");
                return;
            }
        }

        System.out.println("Student not found!");
    }

    // Save all student records to a text file
    public static void saveToFile() {
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("grades.txt"))) {

            writer.println("Name,Math,Physics,English,CS");

            for (Student s : students) {
                writer.printf("%s,%.2f,%.2f,%.2f,%.2f%n",
                        s.getName(), s.getMath(), s.getPhysics(),
                        s.getEnglish(), s.getComputerScience());
            }

            System.out.println("Records saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

}
