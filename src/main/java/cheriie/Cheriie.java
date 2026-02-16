package cheriie;

import java.util.Scanner;
import java.util.ArrayList;

public class Cheriie {

    private static ArrayList<Task> taskLists = new ArrayList<>();
    private static final int TASK_DISPLAY_OFFSET = 1;

    public static void main(String[] args) {
        showGreeting();
        taskManager();
    }

    public static void saveTask(Task task) {
        taskLists.add(task);
        print("okay got it! i've added this task to the list:",
                " " + task.toString(),
                "now you have " + taskCount + " task(s) in the list.( ˘͈ ᵕ ˘͈)️");
    }

    private static String printHelp() {
        return ("""
                i'm sorry, i have no idea what that means :(
                here are a list of words i understand:
                1. list - lists out all current tasks
                2. todo - adds a todo to tasks
                3. deadline [description] /by [date/time] - adds a deadline task
                4. event [description] /from [date/time] /to [date/time] - adds an event task
                5. mark [task number] - to mark task as complete
                6. unmark [task number] - to mark task as incomplete
                7. bye - to end the conversation""");
    }

    private static void printHorizontalLinesBot() {
        System.out.print("─".repeat(75).indent(3));
    }

    public static void print(String... messages) {
        for (String message : messages) {
            message.lines().forEach(line -> System.out.println("\t" + line));
        }
    }

    public static void showGreeting() {
        String logo = """
             ____ _
            / ___| |__   ___ _ __   (_) (_) ___
           | |   | '_ \\ / _ \\ '__|  | | | |/ _ \\
           | |___| | | |  __/ |     | | | |  __/
            \\____|_| |_|\\___|_|     |_|_|_|\\___|
           """;
        System.out.println(logo);
        printHorizontalLinesBot();
        print("hello there ! my name is Cheriie („• ֊ •„) !");
        print("what may i do for you today?");
        printHorizontalLinesBot();
    }

    public static void handleByeCommand() {
        print("bye :) hope to hear from you again soon!");
    }

    public static void handleListCommand() {
        print("here are the tasks in your current list:");
        for (int i = 0; i < taskCount; i++) {
            print((i + 1) + "." + listOfItems[i]);
        }
    }

    public static void handleMarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskCount, "mark");
        listOfItems[index].markAsDone();
    }

    public static void handleUnmarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskCount, "unmark");
        listOfItems[index].markUndone();
    }

    public static void handleTodoCommand(String argument) throws CheriieException {
        String description = Parser.parseToDo(argument);
        Todo t = new Todo(description);
        saveTask(t);
    }

    public static void handleDeadlineCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseDeadline(argument);
        Deadline d = new Deadline(parts[0], parts[1]);
        saveTask(d);
    }

    public static void handleEventCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseEvent(argument);
        Event e = new Event(parts[0], parts[1], parts[2]);
        saveTask(e);
    }


    public static void taskManager() {
        Scanner in = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            String inputLine = in.nextLine();
            String[] parts = inputLine.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = (parts.length > 1) ? parts[1] : "";

            printHorizontalLinesBot();

            try {
                switch(command) {
                case "bye":
                    handleByeCommand();
                    isRunning = false;
                    break;
                case "list":
                    handleListCommand();
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
                default:
                    throw new CheriieException(printHelp());
                }
            } catch (CheriieException e) {
                print(e.getMessage());
            }
            // print line after output
            printHorizontalLinesBot();
        }
    }
}
