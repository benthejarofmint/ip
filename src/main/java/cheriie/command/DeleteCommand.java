package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

import java.io.IOException;

/**
 * Command to remove an existing task from the task list based on its order.
 * This command parses the task number provided by the user, validates it,
 * removes the corresponding task from the task list, and ensures that
 * the updated task list is saved to storage.
 */
public class DeleteCommand extends Command {
    private int indexToDelete;

    /**
     * Constructs a {@code DeleteCommand} with the specified index to delete.
     *
     * @param indexToDelete The argument string containing the task number to delete.
     */
    public DeleteCommand(int indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

    /**
     * Executes the command to remove an existing task.
     * Deletes the task from the current list based on its index and saving the updated list to storage.
     *
     * @param tasks The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CheriieException {
        tasks.deleteTask(indexToDelete);
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            throw new CheriieException("warning! could not save changes after deleting: " + e.getMessage());
        }
    }
}
