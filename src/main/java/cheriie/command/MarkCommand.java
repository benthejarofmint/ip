package cheriie.command;

import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.CheriieException;
import java.io.IOException;

public class MarkCommand extends Command {
    private int indexToMark;

    public MarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

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
