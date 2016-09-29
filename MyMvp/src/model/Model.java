package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearcher;
import algorithms.search.Solution;
import properties.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Interface Model - Interface that holds all the function that a model
 * should implement.
 */
public interface Model {

	/**
	 * Generate maze - Will generate a new Maze3d.
	 *
	 * @param name
	 *            the name
	 * @param floors
	 *            the floors
	 * @param rows
	 *            the rows
	 * @param colums
	 *            the colums
	 * @param generator
	 *            the generator
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
	 * Load maze - Will load a maze from a file to a name you choose.
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
	 * Solve maze 3 d - Solve a maze using one of the searchers.
	 *
	 * @param name
	 *            the name
	 * @param commonSearcher
	 *            the common searcher
	 */
	void solveMaze3d(String name, CommonSearcher<Position> commonSearcher);

	/**
	 * Gets the maze solution - Only if there is a solution to the maze.
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
	 * Save maze - to a file.
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
	 *             the interrupted exception
	 */
	void waitUntilCloseAllFiles() throws InterruptedException;

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	Properties getProperties();

	/**
	 * Sets the properties.
	 *
	 * @param properties
	 *            the new properties
	 * @return the properties
	 */
	void setProperties(Properties properties);

	/**
	 * Save solutions.
	 */
	void saveSolutions();

}
