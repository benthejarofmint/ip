package cheriie.ui;

/**
 * Handles the raw visual formatting and console output logic.
 * Separates the specific aesthetic details (like indentation and ASCII art) from the rest of the application,
 * ensuring a consistent look and feel across all user interactions.
 */
public class Display {
    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(75).indent(3));
    }

    /**
     * Prints one or more messages with a standardized indentation.
     * Ensures all application output aligns uniformly in the console,
     * visually distinguishing the bot's responses from the user's raw input.
     *
     * @param messages A variable number of string messages to be printed line by line.
     */
    public static void print(String... messages) {
        for (String message : messages) {
            message.lines().forEach(line -> System.out.println("\t" + line));
        }
    }

    /**
     * Formats and prints a message prefixed with the application's persona name.
     * Clearly attributes specific output to the chatbot, enhancing the conversational feel of the interface.
     *
     * @param message The core string content to be displayed.
     */
    public static void response(String message) {
        print("Cheriie:\n" + message);
    }

    public static void showWelcomeMessage() {
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

    public static void showEndMessage() {
        print("bye :) hope to hear from you again soon!");
    }

    public static void showHelpMessage() {
        print("""
                here are a list of words i understand:
                1. list - lists out all current tasks
                2. todo - adds a todo to tasks
                3. deadline [description] /by [date/time] - adds a deadline task
                4. event [description] /from [date/time] /to [date/time] - adds an event task
                5. mark [task number] - to mark task as complete
                6. unmark [task number] - to mark task as incomplete
                7. bye - to end the conversation""");
    }
}
