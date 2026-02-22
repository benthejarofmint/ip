package cheriie.ui;

import java.util.Scanner;

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
