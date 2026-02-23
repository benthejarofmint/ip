package cheriie.command;

import cheriie.storage.Storage;
import cheriie.task.Deadline;
import cheriie.task.Event;
import cheriie.task.Task;
import cheriie.task.Todo;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.CheriieException;

import java.io.IOException;

/**
 * Command to append a new task to the user's active task list.
 */
public class AddCommand extends Command {
    private Task taskToAdd;

    /**
     * Initializes the command with the specific task object that needs to be tracked.
     */
    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    /**
     * Executes the command to add a new task.
     * Appends the task to the current list and immediately synchronizes the updated list to storage.
     *
     * @param taskList The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
    @Override
    public void execute(TaskList taskList,Ui ui, Storage storage) throws CheriieException {
        TaskList.saveTask(taskToAdd);
        try {
            storage.save(taskList.getTasks());
        } catch (IOException e) {
            throw new CheriieException("warning! i could not save the changes: " + e.getMessage());
        }
    }

}
