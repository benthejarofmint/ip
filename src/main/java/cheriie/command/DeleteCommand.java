package cheriie.command;

import cheriie.CheriieException;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    private int indexToDelete;

    public DeleteCommand(int indexToDelete) {
        this.indexToDelete = indexToDelete;
    }

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
