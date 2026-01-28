import java.util.Scanner;

public class Cheriie {
    public static void main(String[] args) {
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

        // echo function goes here
        echo();
    }

    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(70).indent(3));
    }

    public static void echo() {
        while (true) {
            String line;
            Scanner in = new Scanner(System.in);
            line = in.nextLine();
            if (line.equalsIgnoreCase("bye")) {
                printHorizontalLinesBot();
                System.out.println("\tbye :) hope to hear from you again soon!");
                printHorizontalLinesBot();
                break;
            }
            printHorizontalLinesBot();
            System.out.print(line.indent(4));
            printHorizontalLinesBot();
        }
    }
}
