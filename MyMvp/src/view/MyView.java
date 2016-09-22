package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyView extends CommonView {

	/**
	 * Instantiates a new my view.
	 *
	 * @param in
	 *            the input
	 * @param out
	 *            the output
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		super(in, out);
		// in super
		// set the input and output
		// config the cli as observer
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.CommonView#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		out.println("The Maze " + name + " is ready");
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.CommonView#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printAnswers(java.lang.String[])
	 */
	@Override
	public void printAnswers(String[] args) {
		for (String line : args) {
			out.println(line);
			out.flush();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		cli.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printCross(int[][])
	 */
	@Override
	public void printCross(int[][] cross) {
		for (int[] i : cross) {
			for (int j : i) {
				out.print(j + " ");
			}
			out.println("");
		}
		out.println("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#notifySolutionIsReady(java.lang.String)
	 */
	@Override
	public void notifySolutionIsReady(String name) {
		out.println("The Solution for Maze " + name + " is ready");
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#displayMazeSolution(algorithms.search.Solution)
	 */
	@Override
	public void displayMazeSolution(Solution<Position> solution) {
		out.println(solution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printErrorMessage(java.lang.String[])
	 */
	@Override
	public void printErrorMessage(String[] msg) {
		for (String line : msg) {
			out.println(line);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// System.out.println("yaaaaaaaaaaaaaaaaaaaaaaaaaa"); just to say that
		// matan is the king
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}

}
