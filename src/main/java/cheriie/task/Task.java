package cheriie.task;

/**
 * Represents a generic task item in the application.
 * Serves as the abstract base class that provides common properties (description, completion status)
 * and enforces a consistent contract for serialization and UI display across all specific task variations.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initializes the core components of a new task.
     * Establishes the base state, defaulting all newly created tasks to an incomplete status.
     *
     * @param description The raw text detailing what the task is.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves a visual character representing the current completion status.
     * Centralizes the logic for how completion states are rendered in the user-facing console.
     *
     * @return A string containing a checkmark ("✔") if done, or a single space (" ") if incomplete.
     */
    public String getStatusIcon() {
        return (isDone ? "✔" : " ");
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Converts the boolean completion status into a compact, parsable format.
     * Optimizes the text file footprint and simplifies the deserialization logic when loading data.
     *
     * @return "1" if the task is completed, or "0" if it remains incomplete.
     */
    public String getStatusForStorage() {
        return (isDone ? "1" : "0");
    }

    /**
     * Serializes the task object into a standardized, delimited string format.
     * Ensures that specific task details (like types and temporal bounds) can be safely written to and recovered from a flat text file.
     *
     * @return The formatted data string ready for persistent storage.
     */
    public abstract String toDataString();

    /**
     * Reconstructs a fully-formed task object from a serialized text line.
     * Acts as a factory method during the loading phase to bridge the gap between raw disk data and in-memory application objects.
     *
     * @param line The delimited, serialized string representation of a single task from the storage file.
     * @return The instantiated subclass {@code Task} object (Todo, Deadline, or Event), or null if the format is unrecognized.
     */
    public static Task fromStorage(String line) {
        String[] parts = line.split(" \\| ");

        String type = parts[0];
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2];

        Task task = null;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (isDone) {
            task.setDone(true);
        }

        return task;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Provides the standard human-readable format of the task.
     * Centralizes how the base task details are rendered before subclasses append their specific formatting.
     *
     * @return The formatted string containing the status icon and description.
     */
    @Override
    public String toString() {
        return " [" + getStatusIcon() + "] " + description;
    }
}
