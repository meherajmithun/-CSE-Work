//University Course Management System
abstract class Course {

    protected String courseCode;
    protected double credit;

    public Course(String courseCode, double credit) {
        this.courseCode = courseCode;
        this.credit = credit;
    }

    public abstract double calculateFinalMarks();

    public void displayCourseInfo() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Credit: " + credit);
    }
}

interface TheoryEvaluation {
    double theoryMarks();
}

interface LabEvaluation {
    double labMarks();
}

class HybridCourse extends Course implements TheoryEvaluation, LabEvaluation {

    private double theoryScore;
    private double labScore;

    public HybridCourse(String courseCode, double credit, double theoryScore, double labScore) {
        super(courseCode, credit);
        this.theoryScore = theoryScore;
        this.labScore = labScore;
    }

    @Override
    public double theoryMarks() {
        return theoryScore;
    }

    @Override
    public double labMarks() {
        return labScore;
    }

    @Override
    public double calculateFinalMarks() {
        return theoryMarks() + labMarks();
    }
}

class CourseService {

    public void printFinalResult(Course c) {
        c.displayCourseInfo();
        System.out.println("Final Marks: " + c.calculateFinalMarks());
        System.out.println("----------------------------");
    }
}
/*
🔹 Explanation
1️⃣ Abstract Class – Course

Contains common attributes (courseCode, credit)

Has an abstract method calculateFinalMarks()

Has a concrete method displayCourseInfo()

2️⃣ Interfaces – TheoryEvaluation & LabEvaluation

Provides multiple inheritance in Java

Each interface has one method to return marks

3️⃣ HybridCourse

Extends Course

Implements TheoryEvaluation and LabEvaluation

Overrides calculateFinalMarks() to sum theory + lab marks

4️⃣ Runtime Polymorphism
Course courseRef = hybrid;
service.printFinalResult(courseRef);

courseRef → Course reference

Actual object → HybridCourse

Calls calculateFinalMarks() of HybridCourse at runtime

5️⃣ Key Points

Abstract class + interfaces = Multiple inheritance in Java

Overriding + runtime polymorphism = flexible method calls

displayCourseInfo() = concrete method from abstract class
*/

public class Main {

    public static void main(String[] args) {

        HybridCourse hybrid = new HybridCourse("CSE301", 3.0, 70, 25);

        Course courseRef = hybrid;

        CourseService service = new CourseService();
        service.printFinalResult(courseRef);
    }
}
