package cheriie;

public class Display {
    public static void printHorizontalLinesBot() {
        System.out.print("─".repeat(75).indent(3));
    }

    public static void print(String... messages) {
        for (String message : messages) {
            message.lines().forEach(line -> System.out.println("\t" + line));
        }
    }

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
}
