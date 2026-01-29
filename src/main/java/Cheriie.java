import java.util.Scanner;

public class Cheriie {
    public static void main(String[] args) {
        greeting();
        // echo function goes here
        echo();
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

    public static void echo() {
        String[] listOfItems = new String[100];
        int count = 0;
        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.nextLine();
            // if the user wants to quite straight away
            if (line.equalsIgnoreCase("bye")) {
                printHorizontalLinesBot();
                System.out.println("\tbye :) hope to hear from you again soon!");
                printHorizontalLinesBot();
                break;
            }
            else if (line.equalsIgnoreCase("list")) {
                printHorizontalLinesBot();
                for (int i = 0; i < count; i++) {
                    System.out.println("\t" + (i + 1) + ". " + listOfItems[i]);
                }
                printHorizontalLinesBot();
            } else {
                listOfItems[count] = line;
                count++;
                printHorizontalLinesBot();
                System.out.println("\tadded: " + line);
                printHorizontalLinesBot();
            }

        }
    }
}
