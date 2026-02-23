package cheriie.command;

import cheriie.task.Task;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.storage.Storage;
import cheriie.CheriieException;

import java.util.List;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws CheriieException {
        List<Task> tasks = taskList.getTasks();

        List<String> matchTask = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword))
                .map(task -> (tasks.indexOf(task) + 1) + ". " + task.toString())
                .toList();

        if (matchTask.isEmpty()) {
            Ui.response("no matching tasks found in your list :(");
        } else {
            Ui.response("Found " + matchTask.size() + " matching task(s) in your list :)");
            matchTask.forEach(Ui::print);
        }
    }
}
