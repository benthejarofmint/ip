package cheriie;

public class Parser {

    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }

    // to validate index for mark/unmark commands
    public static int parseIndex(String argument, int currentTaskCount) throws CheriieException {
        if (argument.isEmpty()) {
            throw new CheriieException("please specify a task number to mark!");
        }
        try {
            int taskIndex = Integer.parseInt(argument.trim()) - 1;
            // check if the index is within bounds
            if (taskIndex < 0 || taskIndex >= currentTaskCount) {
                throw new CheriieException("this task does not exist yet! i can't mark it >:(.\n" + "you have " +
                        currentTaskCount + " task(s) right now !");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new CheriieException("please provide a valid task number! eg. 'mark 1'.");
        }
    }

    // validates todo tasks
    public static String parseToDo(String argument) throws CheriieException {
        if (argument.trim().isEmpty()) {
            throw new CheriieException("the description of the 'todo' command cannot be empty :/");
        }
        return argument.trim();
    }

    // validates deadline tasks
    public static String[] parseDeadline(String argument) throws CheriieException {
        if (argument.trim().isEmpty()) {
            throw new CheriieException("the description of a deadline task cannot be empty!");
        }
        if (!argument.contains(" /by ")) {
            throw new CheriieException("to create a deadline, follow the format: 'deadline [description] /by [date/time]");
        }

        String[] parts = argument.split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (by.isEmpty()) {
            throw new CheriieException("the date/time to do the task by cannot be empty!");
        }
        if (description.isEmpty()) {
            throw new CheriieException("the description of a deadline task cannot be empty!");
        }
        return new String[]{description, by};
    }

    // validates event tasks
    public static String[] parseEvent(String argument) throws CheriieException {
        if (argument.trim().isEmpty()) {
            throw new CheriieException("the description of the event cannot be empty.");
        }
        if (!argument.contains(" /from ") || !argument.contains(" /to ")) {
            throw new CheriieException("please specify event time using '/from [start] /to [end]' :) ");
        }

        String[] parts = argument.split(" /from ", 2);
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ", 2);

        if (timeParts.length < 2) {
            throw new CheriieException("please format event time as: /from [start] /to [end]. thanks!");
        }

        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new CheriieException("the description of the event, start time, and end time cannot be empty. :))");
        }
        return new String[]{description, from, to};
    }
}
