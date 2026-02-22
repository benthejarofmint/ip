package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;


public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList,Ui ui, Storage storage) {
        taskList.listTasks();
    }
}
