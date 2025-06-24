import java.util.*;

class Student {
    private String id;
    private String name;
    private int attendanceDays;
    private int totalClasses;
    private Map<String, Integer> assignments = new HashMap<>();
    private Map<String, Integer> exams = new HashMap<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.attendanceDays = 0;
        this.totalClasses = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void markAttendance(boolean present) {
        totalClasses++;
        if (present) attendanceDays++;
    }

    public void addAssignment(String assignmentName, int marks) {
        assignments.put(assignmentName, marks);
    }

    public void addExam(String examName, int marks) {
        exams.put(examName, marks);
    }

    public double calculateAttendancePercentage() {
        if (totalClasses == 0) return 0;
        return (attendanceDays * 100.0) / totalClasses;
    }

    public double calculateAssignmentAverage() {
        if (assignments.isEmpty()) return 0;
        return assignments.values().stream().mapToInt(i -> i).average().orElse(0);
    }

    public double calculateExamAverage() {
        if (exams.isEmpty()) return 0;
        return exams.values().stream().mapToInt(i -> i).average().orElse(0);
    }

    public void printGradeSheet() {
        System.out.println("\nGrade Sheet for: " + name + " (" + id + ")");
        System.out.printf("Attendance: %.2f%% (%d/%d days)%n", calculateAttendancePercentage(), attendanceDays, totalClasses);
        System.out.printf("Assignment Avg: %.2f%n", calculateAssignmentAverage());
        System.out.printf("Exam Avg: %.2f%n", calculateExamAverage());

        double finalGrade = (calculateAttendancePercentage() * 0.2) + (calculateAssignmentAverage() * 0.3) + (calculateExamAverage() * 0.5);
        System.out.printf("Final Grade: %.2f%n", finalGrade);
    }
}

public class StudentManagementApp {
    private static Map<String, Student> students = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("\n--- Student Academic Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Remove Student");
            System.out.println("4. Mark Attendance");
            System.out.println("5. Add Assignment Marks");
            System.out.println("6. Add Exam Marks");
            System.out.println("7. Show Grade Sheet");
            System.out.println("8. List All Students");
            System.out.println("9. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> editStudent();
                case 3 -> removeStudent();
                case 4 -> markAttendance();
                case 5 -> addAssignment();
                case 6 -> addExam();
                case 7 -> showGrade();
                case 8 -> listStudents();
                case 9 -> {
                    run = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        if (students.containsKey(id)) {
            System.out.println("Student already exists.");
            return;
        }
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        students.put(id, new Student(id, name));
        System.out.println("Student added.");
    }

    static void editStudent() {
        System.out.print("Enter ID to edit: ");
        String id = sc.nextLine();
        if (!students.containsKey(id)) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        Student old = students.get(id);
        students.put(id, new Student(id, name));
        System.out.println("Student updated.");
    }

    static void removeStudent() {
        System.out.print("Enter ID to remove: ");
        String id = sc.nextLine();
        if (students.remove(id) != null)
            System.out.println("Student removed.");
        else
            System.out.println("Student not found.");
    }

    static void markAttendance() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Was present? (true/false): ");
        boolean present = sc.nextBoolean();
        sc.nextLine();
        s.markAttendance(present);
        System.out.println("Attendance updated.");
    }

    static void addAssignment() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Assignment name: ");
        String name = sc.nextLine();
        System.out.print("Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();
        s.addAssignment(name, marks);
        System.out.println("Assignment recorded.");
    }

    static void addExam() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        Student s = students.get(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Exam name: ");
        String name = sc.nextLine();
        System.out.print("Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();
        s.addExam(name, marks);
        System.out.println("Exam marks added.");
    }

    static void showGrade() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        Student s = students.get(id);
        if (s != null) {
            s.printGradeSheet();
        } else {
            System.out.println("Student not found.");
        }
    }

    static void listStudents() {
        System.out.println("--- Registered Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students.values()) {
                System.out.println(s.getId() + " - " + s.getName());
            }
        }
    }
}
