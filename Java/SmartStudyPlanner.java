import java.util.*;

// StudyTask class (OOP concept)
class StudyTask implements Comparable<StudyTask> {
    private String subject;
    private int priority; // 1 = High, 2 = Medium, 3 = Low
    private int duration; // Duration in minutes

    public StudyTask(String subject, int priority, int duration) {
        this.subject = subject;
        this.priority = priority;
        this.duration = duration;
    }

    public String getSubject() {
        return subject;
    }

    public int getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(StudyTask other) {
        return Integer.compare(this.priority, other.priority); // Lower priority value = higher priority
    }

    @Override
    public String toString() {
        String level = switch (priority) {
            case 1 -> "High";
            case 2 -> "Medium";
            case 3 -> "Low";
            default -> "Unknown";
        };
        return "Subject: " + subject + ", Priority: " + level + ", Duration: " + duration + " mins";
    }
}

// SmartStudyPlanner class (Main application)
public class SmartStudyPlanner {
    private List<StudyTask> tasks;
    private Scanner scanner;

    public SmartStudyPlanner() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("📚 Smart Study Planner Started!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Study Task");
            System.out.println("2. View Study Plan");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addTask();
                case 2 -> showPlan();
                case 3 -> {
                    System.out.println("Exiting Study Planner. Good luck!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addTask() {
        System.out.print("Enter subject name: ");
        String subject = scanner.nextLine();
        System.out.print("Enter priority (1=High, 2=Medium, 3=Low): ");
        int priority = scanner.nextInt();
        System.out.print("Enter duration (in minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // consume newline

        StudyTask task = new StudyTask(subject, priority, duration);
        tasks.add(task);
        System.out.println("✅ Task added successfully!");
    }

    private void showPlan() {
        if (tasks.isEmpty()) {
            System.out.println("⚠️ No study tasks added yet.");
            return;
        }

        Collections.sort(tasks); // Sort by priority
        System.out.println("\n🗓️ Your Study Plan (sorted by priority):");
        for (StudyTask task : tasks) {
            System.out.println(task);
        }
    }

    public static void main(String[] args) {
        new SmartStudyPlanner().start();
    }
}
