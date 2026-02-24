package cheriie;

import cheriie.parser.Parser;
import cheriie.storage.Storage;
import cheriie.tasklist.TaskList;
import cheriie.ui.Ui;
import cheriie.command.Command;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main driver class for the Cheriie task management app.
 * Orchestrates the initialization of all core components (UI, Storage, TaskList)
 * and manages the main continuous execution loop of the program.
 */
public class Cheriie {

    private Storage storage;
    private TaskList taskLists;
    private Ui ui;

    /**
     * Initializes the core components required for the application to function.
     * Attempts to load existing task data from the specified file path to seamlessly resume a previous session,
     * or starts fresh if no prior data is found.
     *
     * @param filePath The string path designating where the application's data file is stored.
     */
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

    /**
     * Serves as the primary entry point for the Java application.
     * Starts the program by instantiating the main class with a default storage path and triggering the run loop.
     *
     * @param args Array of command-line arguments passed during execution.
     */
    public static void main(String[] args) {
        new Cheriie("data/cheriie.txt").run();
    }


    /**
     * Executes the main interactive loop of the application.
     * Continuously listens for user input, delegates parsing to the Parser, executes the resulting Commands,
     * and gracefully traps any application-specific exceptions to prevent hard crashes until the user decides to exit.
     */
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
