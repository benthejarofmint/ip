package cheriie.parser;

import cheriie.CheriieException;
import cheriie.command.*;
import cheriie.task.Deadline;
import cheriie.task.Event;
import cheriie.task.Todo;
import cheriie.ui.Ui;

/**
 * Utility class responsible for translating raw user string input into executable commands.
 * Separates the user interface input processing from the core command execution logic.
 */
public class Parser {

    /**
     * Translates raw user input into the corresponding executable command object.
     * Acts as a central factory to route user intent to the correct application logic.
     *
     * @param input The raw string command entered by the user.
     * @param currentTaskCount The total number of tasks currently in the list, used to aid early validation of index-based commands.
     * @return The instantiated {@code Command} object corresponding to the user's input.
     * @throws CheriieException If the command word is unrecognized or if the arguments provided are invalid.
     */
    public static Command parse(String input, int currentTaskCount) throws CheriieException {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "todo":
            return new AddCommand(new Todo(parseToDo(arguments)));
        case "deadline":
            String[] dParts = parseDeadline(arguments);
            return new AddCommand(new Deadline(dParts[0], dParts[1]));
        case "event":
            String[] eParts = parseEvent(arguments);
            return new AddCommand(new Event(eParts[0], eParts[1], eParts[2]));
        case "mark":
            int markIndex = parseIndex(arguments, currentTaskCount, "mark");
            return new MarkCommand(markIndex);
        case "unmark":
            int unmarkIndex = parseIndex(arguments, currentTaskCount, "unmark");
            return new UnmarkCommand(unmarkIndex);
        case "delete":
            int deleteIndex = parseIndex(arguments, currentTaskCount, "delete");
            return new DeleteCommand(deleteIndex);
        case "help":
            return new HelpCommand();
        case "find":
            if (arguments.isEmpty()) {
                throw new CheriieException("error: you need to provide a keyword!");
            }
            return new FindCommand(arguments);
        default:
            throw new CheriieException("i'm sorry, i have no idea what that means :(");
        }
    }

    /**
     * Extracts and validates a target task index from the user's input arguments.
     * Ensures that operations targeting specific tasks receive a valid, in-bounds, zero-based index before execution.
     *
     * @param argument The string argument expected to contain a target task number.
     * @param currentTaskCount The current size of the task list for boundary validation.
     * @param command The name of the command invoking this method, used to contextualize error messages.
     * @return The validated 0-based {@code taskIndex} integer index.
     * @throws CheriieException If the argument is missing, not a valid integer, or falls outside the current list bounds.
     */
    public static int parseIndex(String argument, int currentTaskCount, String command) throws CheriieException {
        if (argument.isEmpty()) {
            throw new CheriieException("please specify a task number to " + command + " !");
        }
        try {
            int taskIndex = Integer.parseInt(argument.trim()) - 1;
            // check if the index is within bounds
            if (taskIndex < 0 || taskIndex >= currentTaskCount) {
                throw new CheriieException("this task does not exist yet! i can't " + command + " it >:(.\n"
                        + "you have " + currentTaskCount + " task(s) right now !");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new CheriieException("please provide a valid task number! eg. '" + command + "' 1'.");
        }
    }

    /**
     * Validates the arguments provided for creating a new Todo task.
     * Prevents the creation of tasks with empty or whitespace-only descriptions.
     *
     * @param argument The raw description string provided by the user.
     * @return The trimmed and validated description string.
     * @throws CheriieException If the provided description is empty.
     */
    public static String parseToDo(String argument) throws CheriieException {
        if (argument.trim().isEmpty()) {
            throw new CheriieException("the description of the 'todo' command cannot be empty :/");
        }
        return argument.trim();
    }

    /**
     * Extracts and validates the description and deadline date/time from the user's input.
     * Ensures the deadline task has both a description and a temporal anchor, correctly formatted.
     *
     * @param argument The raw string containing the description and deadline details.
     * @return A String array of size 2, where index 0 is the description and index 1 is the deadline date/time.
     * @throws CheriieException If the format lacks the {@code '/by'} delimiter or required fields are missing.
     */
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

    /**
     * Extracts and validates the description, start time, and end time for an event task.
     * Guarantees that event tasks contain all necessary temporal bounds and correct delimiter formatting.
     *
     * @param argument The raw string containing the description and event timeframe.
     * @return A String array of size 3, where index 0 is the description, index 1 is the start time, and index 2 is the end time.
     * @throws CheriieException If the format lacks {@code '/from'} or '{@code /to'} delimiters, or if any required fields are missing.
     */
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
