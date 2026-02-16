package cheriie;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toDataString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\terror saving file: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() throws CheriieException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return loadedTasks;
            } catch (IOException e) {
                throw new CheriieException("error creating new file: " + e.getMessage());
            }
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
                    System.out.println("\tskipping corrupted line: " + line);
                    continue;
                }

                if (isDone) {
                    task.setDone(true);
                }
                loadedTasks.add(task);
            }
        } catch (Exception e) {
            throw new CheriieException("error loading tasks: " + e.getMessage());
        }

        return loadedTasks;
    }
}
