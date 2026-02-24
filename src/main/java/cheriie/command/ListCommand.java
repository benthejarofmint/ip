package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

/**
 * Command to output the entire user's current task list.
 * If the task list is empty, it notifies the user accordingly.
 */
public class ListCommand extends Command {

    /**
     * Executes the command to display all current tasks.
     * Prompts the task list to print all tracked items to the user interface.
     *
     * @param taskList The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     */
    @Override
    public void execute(TaskList taskList,Ui ui, Storage storage) {
        taskList.listTasks();
    }
}
