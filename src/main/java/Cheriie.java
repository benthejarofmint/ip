import java.util.Scanner;

public class Cheriie {

    private static final int MAX_TASKS = 100;
    private static Task[] listOfItems = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        showGreeting();
        taskManager();
    }

    public static void saveTask(Task t) {
        listOfItems[taskCount] = t;
        taskCount++;

        printHorizontalLinesBot();
        System.out.println("\tokay got it! i've added this task to the list:");
        System.out.println("\t  " + t.toString());
        System.out.println("\tnow you have " + taskCount + " task(s) in the list. ☺️");
        printHorizontalLinesBot();
    }

    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(70).indent(3));
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
        System.out.println("\thello there ! my name is Cheriie !");
        System.out.println("\twhat may i do for you today?");
        printHorizontalLinesBot();
    }

    public static void handleByeCommand() {
        printHorizontalLinesBot();
        System.out.println("\tbye :) hope to hear from you again soon!");
        printHorizontalLinesBot();
    }

    public static void handleListCommand() {
        printHorizontalLinesBot();
        System.out.println("\there are the tasks in your current list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + "." + listOfItems[i]);
        }
        printHorizontalLinesBot();
    }


    public static void taskManager() {
        Scanner in = new Scanner(System.in);

        while (true) {
            String inputLine = in.nextLine();
            // split output to recognise commands
            String[] parts = inputLine.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = (parts.length > 1) ? parts[1] : "";

            // if the user wants to quite straight away
            if (command.equalsIgnoreCase("bye")) {
                handleByeCommand();
                break;
            }

            // handle "list" command
            else if (command.equalsIgnoreCase("list")) {
                handleListCommand();
            }

            // handle "mark" command
            else if (command.equalsIgnoreCase("mark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                try {
                    if (index >= 0 && index < taskCount) {
                        printHorizontalLinesBot();
                        listOfItems[index].markAsDone();
                        printHorizontalLinesBot();
                    } else {
                        // this handles cases where index is valid for array but no task exists there yet
                        printHorizontalLinesBot();
                        System.out.println("\tthis task does not exist yet! i can't mark it >:(");
                        printHorizontalLinesBot();
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    printHorizontalLinesBot();
                    System.out.println("\tplease provide a valid task number! you can check using the 'list' command");
                    printHorizontalLinesBot();
                }
            }

            // handle "unmark" command
            else if (command.equalsIgnoreCase("unmark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                try {
                    if (index >= 0 && index < taskCount) {
                        printHorizontalLinesBot();
                        listOfItems[index].markAsDone();
                        printHorizontalLinesBot();
                    } else {
                        // this handles cases where index is valid for array but no task exists there yet
                        printHorizontalLinesBot();
                        System.out.println("\tthis task does not exist yet! i can't unmark it >:(");
                        printHorizontalLinesBot();
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    printHorizontalLinesBot();
                    System.out.println("\tplease provide a valid task number! you can check using the 'list' command");
                    printHorizontalLinesBot();
                }
            }

            else if (command.equalsIgnoreCase("todo")) {
                if (arguments.isEmpty()) {
                    printHorizontalLinesBot();
                    System.out.println("\toh no, the description of the 'todo' command cannot be empty :/");
                    printHorizontalLinesBot();
                    break;
                }
                Todo t = new Todo(arguments);
                saveTask(t);
            }

            else if (command.equalsIgnoreCase("deadline")) {
                if (!arguments.contains(" /by ")) {
                    printHorizontalLinesBot();
                    System.out.println("\tplease specify a deadline using '/by'. thanks.");
                    printHorizontalLinesBot();
                    return;
                }
                String[] part = arguments.split(" /by ");
                String description = part[0];
                String by = part[1];

                Deadline d = new Deadline(description, by);
                saveTask(d);
            }

            else if (command.equalsIgnoreCase("event")) {
                if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                    System.out.println("\tplease specify event time using '/from' and '/to'. thanks.");
                    return;
                }
                String[] part = arguments.split(" /from ");
                String description = part[0];
                String[] timeParts = part[1].split(" /to ");

                Event e = new Event(description, timeParts[0], timeParts[1]);
                saveTask(e);
            }

            // handle adding tasks to the list
            else {
                printHorizontalLinesBot();
                System.out.println("\ti'm sorry, i have no idea what that means :(");
                printHorizontalLinesBot();
            }
        }
    }
}
