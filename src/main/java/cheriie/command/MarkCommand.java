package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.CheriieException;
import java.io.IOException;

/**
 * Command to update a specific task's status to 'completed'.
 */
public class MarkCommand extends Command {
    private int indexToMark;

    /**
     * Initializes the command with the list index of the task that was completed.
     */
    public MarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    /**
     * Executes the command to mark a task as completed.
     * Updates the specific task's status and synchronizes the updated state with storage.
     *
     * @param tasks The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CheriieException {
        tasks.markTask(indexToMark);
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            throw new CheriieException("warning! could not save changes after marking: " + e.getMessage());
        }
    }
}
