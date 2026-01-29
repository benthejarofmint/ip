public class Task {
    protected String description;
    protected boolean isDone;

    // setter
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "✅" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
        System.out.println("\tgood job! i've marked this task as done:");
        System.out.println("\t[" + getStatusIcon() + "] " + this.description);
    }

    public void markUndone() {
        this.isDone = false;
        System.out.println("\tunderstands, i've marked this task as NOT done:");
        System.out.println("\t[" + getStatusIcon() + "] " + this.description);
    }
}
