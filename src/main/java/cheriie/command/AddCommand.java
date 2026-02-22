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

public class AddCommand extends Command {
    private Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

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
