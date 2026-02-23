package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

import java.io.IOException;

/**
 * Command to revert a specific task's status back to 'incomplete'.
 */
public class UnmarkCommand extends Command {
    private int indexToUnmark;

    /**
     * Initializes the command with the list index of the task to be unmarked.
     */
    public UnmarkCommand(int indexToUnmark) {
        this.indexToUnmark = indexToUnmark;
    }

    /**
     * Executes the command to mark a task as incomplete.
     * Reverts the specific task's status and synchronizes the updated state with storage.
     *
     * @param tasks The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CheriieException {
        tasks.unmarkTask(indexToUnmark);
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            throw new CheriieException("warning! could not save changes after unmarking: " + e.getMessage());
        }
    }
}
