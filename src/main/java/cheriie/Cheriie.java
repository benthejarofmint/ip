package cheriie;

import cheriie.parser.Parser;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.command.Command;

import java.io.IOException;
import java.util.ArrayList;

public class Cheriie {

    private Storage storage;
    private TaskList taskLists;
    private Ui ui;

    public Cheriie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskLists = new TaskList(storage.load());
        } catch (IOException e) {
            Ui.print("error loading file: " + e.getMessage());
            taskLists = new TaskList(new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        new Cheriie("data/cheriie.txt").run();
    }

    private void saveChanges() {
        try {
            storage.save(taskLists.getTasks());
        } catch (IOException e) {
            Ui.print("warning! could not save changes to file: " + e.getMessage());
        }
    }

    public void run() {
        Ui.showWelcomeMessage();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = Ui.getInput();
                Ui.printHorizontalLinesBot();
                Command c = Parser.parse(fullCommand, taskLists.getSize());
                c.execute(taskLists, ui, storage);
                isExit = c.isExit();
            } catch (CheriieException e) {
                Ui.response(e.getMessage());
            }
            Ui.printHorizontalLinesBot();
        }
    }
}
