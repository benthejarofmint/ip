package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

/**
 * Abstract base class for all command types in the application.
 * <p>
 * Represents an executable action derived from user input.
 * Each specific command must implement the {@code execute} method to perform the specified action.
 * Additionally, commands can indicate if they should terminate the application
 * by overriding the {@code isExit} method.
 * </p>
 */
public abstract class Command {

    /**
     * Executes the core logic of the command.
     * Applies changes to the application state (tasks), handles user interactions, and manages data persistence.
     *
     * @param taskLists The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
    public abstract void execute(TaskList taskLists, Ui ui, Storage storage ) throws CheriieException;

    /**
     * Determines whether this command should trigger the application to exit.
     *
     * @return {@code true} if the command is an exit command, {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
