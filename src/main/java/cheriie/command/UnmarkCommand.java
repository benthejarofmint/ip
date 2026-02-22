package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

import java.io.IOException;

public class UnmarkCommand extends Command {
    private int indexToUnmark;

    public UnmarkCommand(int indexToUnmark) {
        this.indexToUnmark = indexToUnmark;
    }

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
