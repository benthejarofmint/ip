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
        printHorizontalLines();
        System.out.println("hello there ! my name is Cheriie !");
        System.out.println("what may i do for you today?");
        printHorizontalLines();
        System.out.println("bye :) hope to hear from you again soon!");
        printHorizontalLines();
    }

    public static void printHorizontalLines() {
        System.out.println("─".repeat(75));
    }
}
