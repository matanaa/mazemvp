package model;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonModel - abstract class for all models
 */
public abstract class CommonModel implements Model {



	/** The maze list. */
	protected HashMap<String, Maze3d> mazeMap;

	/** The solution list. */
	protected HashMap<String, Solution<Position>> solutionMap;

	/** The thread pool. */
	protected ExecutorService threadPool;



	/**
	 * Gets the maze map.
	 *
	 * @return the maze map
	 */
	public HashMap<String, Maze3d> getMazeMap() {
		return mazeMap;
	}

	/**
	 * Sets the maze map.
	 *
	 * @param mazeMap
	 *            the maze map
	 */
	public void setMazeMap(HashMap<String, Maze3d> mazeMap) {
		this.mazeMap = mazeMap;
	}

	/**
	 * Gets the solution map.
	 *
	 * @return the solution map
	 */
	public HashMap<String, Solution<Position>> getSolutionMap() {
		return solutionMap;
	}

	/**
	 * Sets the solution map.
	 *
	 * @param solutionMap
	 *            the solution map
	 */
	public void setSolutionMap(HashMap<String, Solution<Position>> solutionMap) {
		this.solutionMap = solutionMap;
	}

	/**
	 * Gets the thread pool.
	 *
	 * @return the thread pool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * Sets the thread pool.
	 *
	 * @param threadPool
	 *            the new thread pool
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	/**
	 * Instantiates a new common model.
	 */
	public CommonModel() {
		this.mazeMap = new HashMap<String, Maze3d>();
		this.solutionMap = new HashMap<String, Solution<Position>>();
		this.threadPool = Executors.newCachedThreadPool();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#generateMaze(java.lang.String, int, int, int)
	 */
	@Override
	public abstract void generateMaze(String name, int floors, int rows, int colums, CommonMaze3dGenerator generator);

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#getMaze(java.lang.String)
	 */
	@Override
	public abstract Maze3d getMaze(String name);

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#getMazeSolution(java.lang.String)
	 */
	@Override
	public abstract Solution<Position> getMazeSolution(String name);

}
