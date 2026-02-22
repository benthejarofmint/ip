package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Ui.showEndMessage();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
