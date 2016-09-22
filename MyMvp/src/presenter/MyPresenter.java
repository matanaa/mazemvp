package presenter;

import java.io.IOException;
import java.util.Observable;

import model.Model;
import view.View;

public class MyPresenter extends CommonPresenter {

	public MyPresenter(View view, Model model) {
		super(view, model);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.CommonController#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#notifySolutionIsReady(java.lang.String)
	 */
	@Override
	public void notifySolutionIsReady(String name) {
		view.notifySolutionIsReady(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#printErrorMessage(java.lang.String[])
	 */
	@Override
	public void printErrorMessage(String[] msg) {
		view.printErrorMessage(msg);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == view) {
			String commandLine = (String) arg;
			// split the input to array of single words
			String commandsArray[] = commandLine.split(" ");
			// recognizes only the first word as the command itself
			String command = commandsArray[0].toLowerCase();

			// throws error if the command isn't part of the
			// commands list
			if (!commands.containsKey(command)) {
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
			default:
				break;
			}

		}
	}

}
