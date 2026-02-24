package cheriie.ui;

import java.util.Scanner;

/**
 * Serves as the primary interface facade for all user interactions.
 * Encapsulates both input gathering and output delegation, allowing the core application logic
 * to communicate with the user without needing to manage raw Scanners or Display formatters directly.
 */
public class Ui {
    private static Scanner scanner = new Scanner(System.in);

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void showWelcomeMessage() {
        Display.showWelcomeMessage();
    }

    public static void showEndMessage() {
        Display.showEndMessage();
    }

    public static void printHorizontalLinesBot() {
        Display.printHorizontalLinesBot();
    }

    public static void showHelpMessage() {
        Display.showHelpMessage();
    }

    public static void response(String message) {
        Display.response(message);
    }

    public static void print(String message) {
        Display.print(message);
    }
}
