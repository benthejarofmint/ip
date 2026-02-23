package cheriie.storage;

import cheriie.task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistent storage operations for the application's task list.
 * Separates file input/output logic from the core application behavior
 * ensuring user data is reliably saved across different sessions.
 */
public class Storage {
    private Path filePath;

    /**
     * Initializes the storage handler with the target file path.
     * Establishes the necessary file path reference that will be used for all subsequent read and write operations.
     *
     * @param filePath The relative or absolute string path to the target storage file.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Serializes the current list of tasks and writes them to the storage file.
     * Overwrites the existing file completely to ensure the disk maintains a perfectly accurate, updated snapshot of the current list state.
     *
     * @param tasks The {@code TaskList} of current tasks to be persisted to disk.
     * @throws IOException If there is an issue writing to the file system (e.g., restricted permissions or disk failure).
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.toDataString());
        }

        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Reads the task data from the storage file line by line into application memory.
     * Restores the user's previous session.
     * Automatically detects and creates missing directories or files to prevent startup crashes on a fresh launch.
     *
     * @return An {@code ArrayList<Task>} reconstructed from the saved file data, or an empty list if no prior data exists.
     * @throws IOException If there is an issue reading from the file system or creating missing directories.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            Files.createFile(filePath);
            return tasks;
        }

        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            tasks.add(Task.fromStorage(line));
        }
        return tasks;
    }

}
