package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

/**
 * Command to safely end the user session and terminate the application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the command to terminate the application.
     * Displays the final sign-off message to the user before the session ends.
     *
     * @param taskList The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Ui.showEndMessage();
    }

    /**
     * Determines whether this command should trigger the application to exit.
     *
     * @return {@code true} to indicate that the application should exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
