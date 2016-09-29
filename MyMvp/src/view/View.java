package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Interface View.
 */
public interface View {

	/**
	 * Notify maze is ready.
	 *
	 * @param name
	 *            the name
	 */
	void notifyMazeIsReady(String name);

	/**
	 * Display maze- will print the maze.
	 *
	 * @param maze
	 *            the maze
	 */
	void displayMaze(Maze3d maze);

	/**
	 * Start.
	 */
	void start();

	/**
	 * Prints the answers form commans.
	 *
	 * @param filelist
	 *            the filelist
	 */
	void printAnswers(String[] filelist);

	/**
	 * Prints the cross will print one cross from maze.
	 *
	 * @param cross
	 *            the cross
	 */
	void printCross(int[][] cross);

	/**
	 * Notify solution is ready.
	 *
	 * @param name
	 *            the name
	 */
	void notifySolutionIsReady(String name);

	/**
	 * Display maze solution.
	 *
	 * @param solution
	 *            the solution
	 */
	void displayMazeSolution(Solution<Position> solution);
	
	void displayHint(Solution<Position> solution);

	/**
	 * will Prints the error message if they occurs .
	 *
	 * @param msg
	 *            the msg
	 */
	void printErrorMessage(String[] msg);
}
