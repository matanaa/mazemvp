package presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class MyPresenter implements Observer {

	/** The model. */
	protected Model model;

	/** The view. */
	protected View view;

	/** The commands manager. */
	protected CommandsManager commandsManager;

	protected HashMap<String, Command> commands;

	public MyPresenter(View view, Model model) {
		this.model = model;
		this.view = view;

		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();

		//view.printAnswers(new String[] { model.getProperies().toString() });
	}

	public void update(Observable o, Object arg) {
		if (o == view) {
			String commandLine = (String) arg;
			// split the input to array of single words
			String commandsArray[] = commandLine.split(" ");
			// recognizes only the first word as the command itself
			String command = commandsArray[0].toLowerCase();

			// throws error if the command isn't part of the
			// commands list
			if (command.equals("display_maze")){
				view.displayMaze(model.getMaze(commandsArray[0]));
			}
			else if (!commands.containsKey(command)) {
				view.printErrorMessage(new String[] { "Critical Error", "Command doesn't exist!" });
			} else {
				// creates the variable that will hold the arguments
				String[] args = null;
				// if there's more than one argument it will split
				// it to array of single words
				if (commandsArray.length > 1) {
					String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
					args = commandArgs.split(" ");
				}
				// gets the actual command from the command list
				Command cmd = commands.get(command);
				try {
					// initiate the command and sending the
					// arguments
					cmd.doCommand(args);
					// print error if there are no arguments at all
				} catch (NullPointerException | IOException e) {
					view.printErrorMessage(new String[] { "Arguments Error", "No arguments!" });

				}
			}
		} else if (o == model) {

			// split the input to] array of single words
			String commandsArray[] = (String[]) arg;
			// recognizes only the first word as the command itself
			String command = commandsArray[0].toLowerCase();

			// creates the variable that will hold the arguments
			String[] args = null;
			// if there's more than one argument it will split
			// it to array of single words
			if (commandsArray.length > 1) {
				args = new String[commandsArray.length - 1];
				for (int i = 0; i < commandsArray.length - 1; i++) {
					args[i] = commandsArray[i + 1];
				}
			}
			switch (command) {
			case "error":
				view.printErrorMessage(args);
				break;
			case "mazeisready":
				view.notifyMazeIsReady(args[0]);
				break;
			case "solutionisready":
				view.notifySolutionIsReady(args[0]);
				break;
			default:
				break;
			}

		}
	}

}
