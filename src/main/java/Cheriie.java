import java.util.Scanner;

public class Cheriie {
    public static void main(String[] args) {
        greeting();
        // main functionality goes here
        taskManager();
    }

    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(70).indent(3));
    }

    public static void greeting() {
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

    public static void byeCommand() {
        printHorizontalLinesBot();
        System.out.println("\tbye :) hope to hear from you again soon!");
        printHorizontalLinesBot();
    }

    public static void listCommand(int count, Task[] listOfItems) {
        printHorizontalLinesBot();
        System.out.println("\there are the tasks in your current list:");
        for (int i = 0; i < count; i++) {
            Task t = listOfItems[i];
            System.out.println("\t" + (i + 1) + ". [" + t.getStatusIcon() +"] " + t.description);
        }
        printHorizontalLinesBot();
    }

    public static void taskManager() {
        Task[] listOfItems = new Task[100];
        int count = 0;
        Scanner in = new Scanner(System.in);
        while (true) {
            String inputLine = in.nextLine();
            // split output to recognise commands
            String[] parts = inputLine.split(" ");
            String command = parts[0];

            // if the user wants to quite straight away
            if (command.equalsIgnoreCase("bye")) {
                byeCommand();
                break;
            }

            // handle "list" command
            else if (command.equalsIgnoreCase("list")) {
                listCommand(count, listOfItems);
            }

            // handle "mark" command
            else if (command.equalsIgnoreCase("mark")) {
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    printHorizontalLinesBot();
                    listOfItems[index].markAsDone();
                    printHorizontalLinesBot();
                } catch (NumberFormatException e) {
                    // add error handling in the event the user types "mark homework" as an action
                    Task t = new Task(inputLine);
                    listOfItems[count] = t;
                    count++;

                    printHorizontalLinesBot();
                    System.out.println("\tadded: " + inputLine);
                    printHorizontalLinesBot();
                }
            }

            // handle "unmark" command
            else if (command.equalsIgnoreCase("unmark")) {
                int index = Integer.parseInt(parts[1]) - 1;
                printHorizontalLinesBot();
                listOfItems[index].markUndone();
                printHorizontalLinesBot();
            }

            // handle adding tasks to the list
            else {
                Task t = new Task(inputLine);
                listOfItems[count] = t;
                count++;

                printHorizontalLinesBot();
                System.out.println("\tadded: " + inputLine);
                printHorizontalLinesBot();
            }
        }
    }
}
