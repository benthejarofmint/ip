package cheriie.command;

import cheriie.task.Task;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.storage.Storage;
import cheriie.CheriieException;

import java.util.List;

/**
 * Command to retrieve and display tasks that match a specific keyword.
 * Facilitates quick information retrieval without needing to scan the entire list.
 * It performs a case-insensitive search and displays the matching tasks to the user.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword The keyword to search for within task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Executes the command to search for tasks by a keyword.
     * Filters the current task list and displays any matching tasks to the user interface.
     *
     * @param taskList The current {@code TaskList} object of tasks to be read or modified.
     * @param ui The {@code Ui} object to display feedback or errors.
     * @param storage The {@code Storage} object handler to persist state changes to disk.
     * @throws CheriieException If an error occurs during execution, such as invalid data or storage failure.
     */
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
