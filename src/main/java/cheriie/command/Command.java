package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskLists, Ui ui, Storage storage ) throws CheriieException;

    public boolean isExit() {
        return false;
    }
}
