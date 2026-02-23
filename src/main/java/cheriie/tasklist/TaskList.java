package cheriie.tasklist;

import cheriie.task.Task;
import cheriie.ui.Ui;

import java.util.ArrayList;

/**
 * Represents the in-memory state of the user's task tracker.
 * Centralizes all list manipulation operations (add, delete, mark) so that commands
 * interact with a clean interface rather than manipulating the raw data structure directly.
 */
public class TaskList {
    private static ArrayList<Task> tasks;

    /**
     * Initializes the task list with an existing collection of tasks.
     * Allows the application to resume its previous state by loading data retrieved from storage.
     *
     * @param tasks The {@code ArrayList} of tasks loaded from disk, or an empty list for a fresh start.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Displays the entirety of the current task list to the user via the UI.
     * Provides a human-readable overview of all pending and completed items currently tracked in the session.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            Ui.response("the task list is currently empty :(");
        } else {
            Ui.response("here are the tasks in your current list:");
            for (int i = 0; i < tasks.size(); i++) {
                Ui.print((i + 1) + "." + tasks.get(i).toString());
            }
        }
    }

    /**
     * Appends a newly created task to the active task list.
     * Integrates the new task into the current session and triggers UI feedback to confirm successful creation.
     *
     * @param task The {@code Task} object to be added to the list.
     */
    public static void saveTask(Task task) {
        tasks.add(task);
        Ui.response("okay got it! i've added this task to the list:\n" + " " + task.toString());
        Ui.print("now you have " + tasks.size() + " task(s) in the list.( ˘͈ ᵕ ˘͈)️");
    }

    /**
     * Removes a specified task from the task list based on its chronological position.
     * Keeps the list manageable by allowing users to discard completed, outdated, or mistaken entries.
     *
     * @param index The 0-based integer index of the task to be removed.
     */
    public void deleteTask(int index) {
        Ui.response("okay got it! i've removed this task from the list: \n" + " " + tasks.get(index).toString());
        tasks.remove(index);
        Ui.print("now you have " + tasks.size() + " task(s) in the list.( ˘͈ ᵕ ˘͈)️");
    }

    /**
     * Flags a specific task in the list as completed.
     * Updates the task's internal state to reflect user progress without permanently deleting it from the record.
     *
     * @param index The 0-based integer index of the task to be marked.
     */
    public void markTask(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        Ui.response("nice! i've marked this task as done:\n" + " " + task.toString());
    }

    /**
     * Reverts a previously completed task back to a pending state.
     * Allows users to gracefully correct accidental completions or reopen tasks that require further action.
     *
     * @param index The 0-based integer index of the task to be unmarked.
     */
    public void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        Ui.response("understands, i've marked this task as NOT done yet:\n " + task.toString());
    }

    /**
     * Retrieves the underlying data structure containing all currently tracked tasks.
     * Necessary for operations that require bulk processing, such as serializing the entire list to disk.
     *
     * @return The {@code ArrayList<Task>} representing the current state of the tracker.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }
}
