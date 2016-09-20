package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonView - abstract class for all views
 */
public abstract class CommonView extends Observable implements View, Observer   {



	/** The input. */
	protected BufferedReader in;

	/** The output. */
	protected PrintWriter out;

	/** The cli. */
	protected CLI cli;

	/**
	 * Instantiates a new common view.
	 *
	 * @param in
	 *            the input
	 * @param out
	 *            the output
	 */
	public CommonView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		cli = new CLI(in, out);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public abstract void notifyMazeIsReady(String name);

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public abstract void displayMaze(Maze3d maze);

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#setCommands(java.util.HashMap)
	 */


}
