package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

/**
 * Command to display usage instructions and available features to the user.
 */
public class HelpCommand extends Command {

    /**
     * Executes the command to display usage instructions.
     * Triggers the user interface to output the standard help guide.
     *
     * @param taskList The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Ui.showHelpMessage();
    }
}
