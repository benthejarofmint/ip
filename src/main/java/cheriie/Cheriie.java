package cheriie;

import java.util.Scanner;

public class Cheriie {

    private static final int MAX_TASKS = 100;
    private static Task[] listOfItems = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static final int TASK_DISPLAY_OFFSET = 1;

    public static void main(String[] args) {
        showGreeting();
        taskManager();
    }

    public static void saveTask(Task task) {
        //handle error
        if (taskCount >= MAX_TASKS) {
            printHorizontalLinesBot();
            System.out.println("\toh no! your task list is full!");
            printHorizontalLinesBot();
        }
        listOfItems[taskCount] = task;
        taskCount++;

        System.out.println("\tokay got it! i've added this task to the list:");
        System.out.println("\t  " + task.toString());
        System.out.println("\tnow you have " + taskCount + " task(s) in the list. ( ˘͈ ᵕ ˘͈)️");
    }

    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(75).indent(3));
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
        System.out.println("\thello there ! my name is Cheriie („• ֊ •„) !");
        System.out.println("\twhat may i do for you today?");
        printHorizontalLinesBot();
    }

    public static void handleByeCommand() {
        System.out.println("\tbye :) hope to hear from you again soon!");
    }

    public static void handleListCommand() {
        System.out.println("\there are the tasks in your current list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + "." + listOfItems[i]);
        }
    }

    public static void handleMarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskCount);
        listOfItems[index].markAsDone();
    }

    public static void handleUnmarkCommand(String argument) throws CheriieException {
        int index = Parser.parseIndex(argument, taskCount);
        listOfItems[index].markUndone();
    }

    public static void handleTodoCommand(String argument) throws CheriieException {
        String description = Parser.parseToDo(argument);
        Todo t = new Todo(argument);
        saveTask(t);
    }

    public static void handleDeadlineCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseDeadline(argument);
        // parts[0]: description
        // parts[1]: by
        Deadline d = new Deadline(parts[0], parts[1]);
        saveTask(d);
    }

    public static void handleEventCommand(String argument) throws CheriieException {
        String[] parts = Parser.parseEvent(argument);
        // parts[0]= description;
        // parts[1] = /from;
        // parts[2] = /to;

        Event e = new Event(parts[0], parts[1], parts[2]);
        saveTask(e);
    }


    public static void taskManager() {
        Scanner in = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            String inputLine = in.nextLine();
            // split output to recognise commands
            String[] parts = inputLine.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = (parts.length > 1) ? parts[1] : "";

            printHorizontalLinesBot();

            // if the user wants to quite straight away
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
                    throw new CheriieException("""
    i'm sorry, i have no idea what that means :(
    \there are a list of words i understand:
    \t1. list - lists out all current tasks
    \t2. todo - adds a todo to tasks
    \t3. deadline [description] /by [date/time] - adds a deadline task
    \t4. event [description] /from [date/time] /to [date/time] - adds an event task
    \t5. mark [task number] - to mark task as complete
    \t6. unmark [task number] - to mark task as incomplete
    \t7. bye - to end the conversation""");
                }
            } catch (CheriieException e) {
                System.out.println("\t" + e.getMessage());
            }
            // print line after output
            printHorizontalLinesBot();
        }
    }
}
