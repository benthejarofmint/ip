package cheriie;

import java.io.IOException;
import java.util.ArrayList;

public class Cheriie {

    private static final int TASK_DISPLAY_OFFSET = 1;
    private  Storage storage;
    private TaskList taskLists;

    public Cheriie(String filePath) {
        storage = new Storage(filePath);
        try {
            taskLists = new TaskList(storage.load());
        } catch (IOException e) {
            Ui.print("error loading file: " + e.getMessage());
            taskLists = new TaskList(new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        new Cheriie("data/cheriie.txt").run();
    }

    private void saveChanges() {
        try {
            storage.save(taskLists.getTasks());
        } catch (IOException e) {
            Ui.print("warning! could not save changes to file: " + e.getMessage());
        }
    }

    public void handleMarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskLists.getSize(), "mark");
        taskLists.markTask(index);
        saveChanges();
    }

    public void handleUnmarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskLists.getSize(), "unmark");
        taskLists.unmarkTask(index);
        saveChanges();
    }

    public void handleDeleteCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskLists.getSize(), "delete");
        taskLists.deleteTask(index);
        saveChanges();
    }

    public void handleTodoCommand(String argument) throws CheriieException {
        String description = Parser.parseToDo(argument);
        Todo t = new Todo(description);
        TaskList.saveTask(t);
    }

    public void handleDeadlineCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseDeadline(argument);
        Deadline d = new Deadline(parts[0], parts[1]);
        TaskList.saveTask(d);
    }

    public void handleEventCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseEvent(argument);
        Event e = new Event(parts[0], parts[1], parts[2]);
        TaskList.saveTask(e);
    }

    public void run() {
        Ui.showWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            String inputLine = Ui.getInput();
            String[] parts = inputLine.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = (parts.length > 1) ? parts[1] : "";

            Ui.printHorizontalLinesBot();

            try {
                switch(command) {
                case "bye":
                    Ui.showEndMessage();
                    isRunning = false;
                    break;
                case "list":
                    taskLists.listTasks();
                    break;
                case "mark":
                    handleMarkCommand(arguments);
                    break;
                case "unmark":
                    handleUnmarkCommand(arguments);
                    break;
                case "todo":
                    handleTodoCommand(arguments);
                    break;
                case "deadline":
                    handleDeadlineCommand(arguments);
                    break;
                case "event":
                    handleEventCommand(arguments);
                    break;
                case "delete":
                    handleDeleteCommand(arguments);
                    break;
                default:
                    Ui.showHelpMessage();
                }
            } catch (CheriieException e) {
                Ui.print(e.getMessage());
            }
            // print line after output
            Ui.printHorizontalLinesBot();
        }
    }
}
