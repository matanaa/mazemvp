package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class CLI - The class that is in charge of the menu and getting input
 * from the user. Defines how to get input and where to show output
 */
public class CLI {

	/** The input. */
	private BufferedReader in;

	/** The output. */
	private PrintWriter out;

	/** The commands' list. */
	private HashMap<String, Command> commands;

	/**
	 * Instantiates a new cli.
	 *
	 * @param in
	 *            the input
	 * @param out
	 *            the output
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}

	/**
	 * Sets the commands.
	 *
	 * @param commands
	 *            the commnds
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}

	/**
	 * Prints the menu.
	 */
	private void printMenu() {
		out.println("\n\n==============================================");
		out.println("Please choose Command to run:");
		// the menu
		Integer i = 1;
		for (String command : commands.keySet()) {
			out.println("\t" + i.toString() + ")" + command);
			i++;
		}
		// example command
		out.println("\n\texample: generate_maze newmaze 5 5 5 Growing\n");
		out.flush();
	}

	/**
	 * Start - the function that handles what happen after the user choose an
	 * option
	 */
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					printMenu();
					try {
						// get the user input
						String commandLine = in.readLine();
						// split the input to array of single words
						String commandsArray[] = commandLine.split(" ");
						// recognizes only the first word as the command itself
						String command = commandsArray[0].toLowerCase();

						// throws error if the command isn't part of the
						// commands list
						if (!commands.containsKey(command)) {
							out.println("Command doesn't exist!");
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
							} catch (NullPointerException e) {
								out.println("Arguments Error: No arguments");
							}
						}
					} catch (IOException e) {
						out.println("Error creating a thread");
					}
				}
			}
		}).start();
	}
}
