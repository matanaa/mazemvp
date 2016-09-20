package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;


// TODO: Auto-generated Javadoc
/**
 * The Class CLI - The class that is in charge of the menu and getting input
 * from the user. Defines how to get input and where to show output
 */
public class CLI extends Observable {

	/** The input. */
	private BufferedReader in;

	/** The output. */
	private PrintWriter out;


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
	 * Prints the menu.
	 */
	private void printMenu() {
		out.println("\n\n==============================================");
		out.println("Please choose Command to run:");
		// the menu
/*		
		Integer i = 1;
		for (String command : commands.keySet()) {
			out.println("\t" + i.toString() + ")" + command);
			i++;
		}
		// example command
		out.println("\n\texample: generate_maze newmaze 5 5 5 Growing\n");*/
		out.flush();
	}

	/**
	 * Start - the function that handles what happen after the user choose an
	 * option
	 */
	
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				
					printMenu();
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
						
						if (commandLine.equals("exit"))
							break;
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();		
	}
	

	public void addObserver(MyView myView) {
		// TODO Auto-generated method stub
		
	}
}
