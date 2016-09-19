package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import algorithm.demo.MazeAdapter;
import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.CommonSearcher;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class MyModel.
 */
public class MyModel extends CommonModel {

	/** The generate maze tasks. */
	protected List<generateMazeRunnable> generateMazeTasks = new ArrayList<generateMazeRunnable>();

	/** The open file count. */
	// will count how many files are open
	protected int openFileCount = 0;

	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {
		super();
	}

	/**
	 * The Class generateMazeRunnable - an adapter to make the generation
	 * process runnable on threads.
	 */
	class generateMazeRunnable implements Runnable {

		/** The floors, rows, colums. */
		private int floors, rows, colums;

		/** The name of the maze. */
		private String name;

		/** The generator type. */
		private Maze3dGenerator generator;

		/**
		 * Instantiates a new generate maze runnable.
		 *
		 * @param floors
		 *            the floors
		 * @param rows
		 *            the rows
		 * @param colums
		 *            the colums
		 * @param name
		 *            the name of the amazing maze
		 * @param generator
		 *            the generator for creating the maze
		 */
		public generateMazeRunnable(int floors, int rows, int colums, String name, Maze3dGenerator generator) {
			super();
			this.floors = floors;
			this.rows = rows;
			this.colums = colums;
			this.name = name;
			this.generator = generator;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// choosing the generator type
			Maze3d maze = generator.generate(floors, rows, colums);
			mazeMap.put(name, maze);
			controller.notifyMazeIsReady(name);
		}

		/**
		 * Terminate.
		 */
		public void terminate() {
			generator.setDone(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CommonModel#generateMaze(java.lang.String, int, int, int)
	 */
	@Override
	public void generateMaze(String name, int floors, int rows, int cols, CommonMaze3dGenerator generator) {
		generateMazeRunnable generateMaze = new generateMazeRunnable(floors, rows, cols, name, generator);
		generateMazeTasks.add(generateMaze);
		threadPool.submit(generateMaze);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CommonModel#getMaze(java.lang.String)
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazeMap.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() {
		for (generateMazeRunnable task : generateMazeTasks) {
			task.terminate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String file, String name) {
		InputStream in;
		// use to check how many files are open for a proper exit
		openFileCount++;
		try {
			
			 File fileinstance = new File(file + ".maz");// file instance need for taking the file size
			// using decorator pattern to get a file using our decompressor
			in = new MyDecompressorInputStream(new FileInputStream(fileinstance));
			// creates a max size byte array
			byte b[] = new byte[(int) fileinstance.length()+1]; //get the file size in bytes and add 1 for the long 
			in.read(b);
			in.close();
			Maze3d loaded = new Maze3d(b);
			mazeMap.put(name, loaded);
			controller.notifyMazeIsReady(name);
		} catch (FileNotFoundException e) {
			controller.printErrorMessage(new String[] { "File location Error", "can't find the file" });

		} catch (IOException e) {
			controller.printErrorMessage(new String[] { "Errorr", "can't Load the maze" });

		} finally {
			// use to check how many files are open for a proper exit
			openFileCount--;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#solveMaze3d(java.lang.String,
	 * algorithms.search.CommonSearcher)
	 */
	@Override
	public void solveMaze3d(String name, CommonSearcher<Position> searcher) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				Maze3d maze = mazeMap.get(name);
				Searchable<Position> searchableMaze = new MazeAdapter(maze);
				Solution<Position> solution = searcher.search(searchableMaze);
				solutionMap.put(name, solution);
				controller.notifySolutionIsReady(name);
			}

		});
		//thread.start();
		threadPool.submit(thread);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.CommonModel#getMazeSolution(java.lang.String)
	 */
	@Override
	public Solution<Position> getMazeSolution(String name) {
		return solutionMap.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#finishThreads()
	 */
	@Override
	public void finishThreads() {
		threadPool.shutdown();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#save_maze(java.lang.String, java.lang.String)
	 */
	@Override
	public void save_maze(String name, String file_name) {
		Maze3d maze = getMaze(name); //get the maze by name
		if (maze==null){
			controller.printErrorMessage(new String[] { "maze name errorr", "can't find the maze "+name });
			return;
		}
		OutputStream savedFile;
		openFileCount++; // will add notification that one file is opend
		try {
			
			savedFile = new MyCompressorOutputStream(new FileOutputStream(file_name + ".maz"));

			savedFile.write(maze.toByteArray());//write the compress byte maze to file
			savedFile.flush();
			savedFile.close();
		} catch (IOException e) {
			controller.printErrorMessage(new String[] { "File location Error", "can't save in that path" });
		} finally {
			openFileCount--; // notify that one file is closed
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Model#waitUntilCloseAllFiles()
	 */
	public void waitUntilCloseAllFiles() throws InterruptedException {
		while (openFileCount != 0) { //until we have no files open
			Thread.sleep(100);
		}
	}
}
