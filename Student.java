package GitHub;

public class Student {

    private String name;
    private double math;
    private double physics;
    private double english;
    private double computerScience;

    // Constructor
    public Student(String name, double math, double physics,
                   double english, double computerScience) {
        this.name = name;
        this.math = math;
        this.physics = physics;
        this.english = english;
        this.computerScience = computerScience;
    }

    public String getName() {
        return name;
    }

    public double getMath() {
        return math;
    }

    public double getPhysics() {
        return physics;
    }

    public double getEnglish() {
        return english;
    }

    public double getComputerScience() {
        return computerScience;
    }

    // Calculate average
    public double getAverage() {
        return (math + physics + english + computerScience) / 4;
    }

    // Calculate grade
    public String getGrade() {
        double average = getAverage();

        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }

    // Update subject marks
    public void setMarks(double math, double physics,
                         double english, double computerScience) {
        this.math = math;
        this.physics = physics;
        this.english = english;
        this.computerScience = computerScience;
    }

    // Display details

    public void displayDetails() {
        System.out.printf("%-20s %-10.1f %-10.1f %-10.1f %-10.1f %-10.2f %-5s%n",
                name, math, physics, english, computerScience, getAverage(), getGrade());
    }

}
