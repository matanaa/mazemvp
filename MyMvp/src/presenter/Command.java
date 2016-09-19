package presenter;

import java.io.FileNotFoundException;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface Command. Holds the function that the Command class has to
 * implement
 */
public interface Command {

	/**
	 * Do command - Each command will implement this function in order to run
	 *
	 * @param command
	 *            the command
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void doCommand(String[] command) throws FileNotFoundException, IOException;
}
