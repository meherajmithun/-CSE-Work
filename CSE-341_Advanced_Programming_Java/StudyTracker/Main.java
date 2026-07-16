//StudyTracker
class StudyTracker {

    private int studentId;
    private int hoursStudied;
    private int dailyGoal;

    private static int totalTrackers = 0;

    public StudyTracker(int studentId, int dailyGoal) {
        this.studentId = studentId;
        this.dailyGoal = dailyGoal;
        this.hoursStudied = 0;  
        totalTrackers++;
    }

    public StudyTracker addStudyHours(int h) {
        this.hoursStudied += h;
        System.out.println("Student " + studentId + " studied " + h + " hours. Total: " + hoursStudied);
        return this;  
    }

    public StudyTracker mergeTracker(StudyTracker st) {
        this.hoursStudied += st.hoursStudied;
        System.out.println("Student " + studentId + " merged " + st.hoursStudied +
                           " hours from Student " + st.studentId + ". Total: " + hoursStudied);
        return this;
    }

    public StudyTracker remainingHours() {
        int remaining = dailyGoal - hoursStudied;
        remaining = remaining > 0 ? remaining : 0;
        System.out.println("Student " + studentId + " needs " + remaining + " more hours to reach daily goal");
        return this;
    }

    public StudyTracker compareProgress(StudyTracker st) {
        if (this.hoursStudied > st.hoursStudied) {
            System.out.println("Student " + studentId + " is ahead");
        } else if (this.hoursStudied < st.hoursStudied) {
            System.out.println("Student " + studentId + " is behind");
        } else {
            System.out.println("Both students studied the same amount");
        }
        return this;
    }
    public StudyTracker resetIfGoalReached() {
        if (hoursStudied >= dailyGoal) {
            System.out.println("Congratulations Student " + studentId + "! Daily goal reached.");
            hoursStudied = 0;
        } else {
            System.out.println("Student " + studentId + ": Daily goal not reached yet");
        }
        return this;
    }

    public static void getTotalTrackers() {
        System.out.println("Total StudyTracker objects created: " + totalTrackers);
    }
}
/*
🔹 Explanation of Key Features

Static Variable

totalTrackers keeps track of all StudyTracker objects created.

Method Chaining

Methods like addStudyHours(), mergeTracker(), remainingHours(), compareProgress(), resetIfGoalReached() return this, so you could chain calls if needed.

Example:

trackers[0].addStudyHours(2).mergeTracker(trackers[1]).resetIfGoalReached();

Merge Tracker

Adds another student's hours to the current student's hours.

Runtime Output

All methods display clear messages for user feedback.
*/

public class Main {

    public static void main(String[] args) {

        StudyTracker[] trackers = new StudyTracker[2];

        trackers[0] = new StudyTracker(101, 5);  
        trackers[1] = new StudyTracker(102, 6);  

        trackers[0].addStudyHours(2);  
        trackers[1].addStudyHours(3);   

        trackers[0].mergeTracker(trackers[1]);

        trackers[0].compareProgress(trackers[1]);
        trackers[1].compareProgress(trackers[0]);

        trackers[0].remainingHours();

        trackers[0].resetIfGoalReached();
        trackers[1].resetIfGoalReached();

        StudyTracker.getTotalTrackers();
    }
}
