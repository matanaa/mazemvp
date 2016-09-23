package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;


// TODO: Auto-generated Javadoc
/**
 * The Interface Model.
 */
public interface Model {

	/**
	 * Generate maze.
	 *
	 * @param name
	 *            the name
	 * @param floors
	 *            the floors
	 * @param rows
	 *            the rows
	 * @param colums
	 *            the colums
	 * @param maze3dGenerator
	 */
	void generateMaze(String name, int floors, int rows, int colums, CommonMaze3dGenerator generator);

	/**
	 * Gets the maze.
	 *
	 * @param name
	 *            the name
	 * @return the maze
	 */
	Maze3d getMaze(String name);

	/**
	 * Exit.
	 */
	void exit();


	/**
	 * Load maze.
	 *
	 * @param file_name
	 *            the file name
	 * @param name
	 *            the name
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void loadMaze(String file_name, String name) throws FileNotFoundException, IOException;

	/**
	 * Solve maze 3 d.
	 *
	 * @param name
	 *            the name
	 * @param commonSearcher
	 *            the common searcher
	 */
	void solveMaze3d(String name, CommonSearcher<Position> commonSearcher);

	/**
	 * Gets the maze solution.
	 *
	 * @param name
	 *            the name
	 * @return the maze solution
	 */
	Solution<Position> getMazeSolution(String name);

	/**
	 * Finish threads.
	 */
	void finishThreads();

	/**
	 * Save maze.
	 *
	 * @param name
	 *            the name
	 * @param file_name
	 *            the file name
	 */
	void save_maze(String name, String file_name);

	/**
	 * Wait until close all files.
	 * 
	 * @throws InterruptedException
	 */
	void waitUntilCloseAllFiles() throws InterruptedException;

	String getProperies();

	

}
