package cheriie;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "✔" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
        Cheriie.print("good job! i've marked this task as done:");
        Cheriie.print(this.toString());
    }

    public void markUndone() {
        this.isDone = false;
        Cheriie.print("understands, i've marked this task as NOT done:");
        Cheriie.print(this.toString());
    }

    @Override
    public String toString() {
        return " [" + getStatusIcon() + "] " + description;
    }
}
