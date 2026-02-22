package cheriie.tasklist;

import cheriie.task.Task;
import cheriie.ui.Ui;

import java.util.ArrayList;

public class TaskList {
    private static ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

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

    public static void saveTask(Task task) {
        tasks.add(task);
        Ui.response("okay got it! i've added this task to the list:\n" + " " + task.toString());
        Ui.print("now you have " + tasks.size() + " task(s) in the list.( ˘͈ ᵕ ˘͈)️");
    }

    public void deleteTask(int index) {
        Ui.response("okay got it! i've removed this task from the list: \n" + " " + tasks.get(index).toString());
        tasks.remove(index);
        Ui.print("now you have " + tasks.size() + " task(s) in the list.( ˘͈ ᵕ ˘͈)️");
    }

    public void markTask(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        Ui.response("nice! i've marked this task as done:\n" + " " + task.toString());
    }

    public void unmarkTask(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        Ui.response("understands, i've marked this task as NOT done yet:\n " + task.toString());
    }

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
