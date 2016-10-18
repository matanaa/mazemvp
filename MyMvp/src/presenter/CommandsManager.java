package presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import algorithms.mazeGenerators.CommonMaze3dGenerator;
import algorithms.mazeGenerators.GrowingTreeMaze3dGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Solution;
import model.Model;
import properties.PropertiesLoader;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandsManager - The class where we hold the commands list.
 */
public class CommandsManager {

	/** The model. */
	private Model model;

	/** The view. */
	private View view;

	/** The commands list. */
	HashMap<String, Command> commands = new HashMap<String, Command>();

	/**
	 * Instantiates a new commands manager.
	 *
	 * @param model
	 *            the model
	 * @param view
	 *            the view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Gets the commands map.
	 *
	 * @return the commands map
	 */
	public HashMap<String, Command> getCommandsMap() {
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new Dir());
		commands.put("display_cross", new Display_Cross_Section());
		commands.put("save_maze", new save_maze());
		commands.put("load_maze", new load_maze());
		commands.put("solve", new solveMaze3d());
		commands.put("solve_from_position", new solveMaze3dfrompos());
		commands.put("display_solution", new displayMazeSolution());
		commands.put("display_hint", new displayHint());
		commands.put("change_xml", new change_xml());
		commands.put("exit", new exit());
		return commands;
	}

	/**
	 * The Class GenerateMazeCommand. Will generate a maze after receiving maze
	 * name, 3 axis sizes and the generator
	 */
	public class GenerateMazeCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 4) {
				view.printErrorMessage(
						new String[] { "Arguments Error", "Please enter <maze name> <floors> <rows> <cols>" });
				return;
			}
			// parsing the args
			String name = args[0];
			int floors = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			// String generator = args[4];
			// ask the model to generate the maze
			model.generateMaze(name, floors, rows, cols,
					getGenerator(model.getProperties().getGenerateMazeAlgorithm()));
		}

		/**
		 * Gets the generator using the command pattern.
		 *
		 * @param generator
		 *            the generator
		 * @return the generator
		 */
		public CommonMaze3dGenerator getGenerator(String generator) {
			HashMap<String, CommonMaze3dGenerator> generators = new HashMap<String, CommonMaze3dGenerator>();
			generators.put("Growing", new GrowingTreeMaze3dGenerator());
			generators.put("Simple", new SimpleMaze3dGenerator());
			if (!generators.containsKey(generator)) {
				view.printErrorMessage(
						new String[] { "Arguments Error", "Unknown generator. please choose: " + generators.keySet() });
			}
			return generators.get(generator);
		}
	}

	/**
	 * The Class DisplayMazeCommand. Will display maze according to its name.
	 */
	public class DisplayMazeCommand implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 1) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Maze name" });
				return;
			}
			// parsing the args
			String name = args[0];
			// get the maze from the maze list
			Maze3d maze = model.getMaze(name);
			if (maze == null) {
				// check if maze exist
				view.printErrorMessage(new String[] { "maze name error", "can't find the maze " + name });
			} else {
				// sending the view a command to display the maze
				view.displayMaze(maze);
			}
		}
	}

	/**
	 * The Class Dir. Will get a directory and show all of its content
	 */
	public class Dir implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if (args.length != 1) {
				// will send error if not enough args has been sent
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Directory location" });
				return;
			}
			// parsing the args
			String paths = args[0];
			// setting variables for the command
			File folderPath = null;
			String[] filelist;
			try {
				// create new file
				folderPath = new File(paths);
				// array of files and directory
				filelist = folderPath.list();
				// for each name in the path array
				view.printAnswers(filelist);
			} catch (Exception e) {
				// if any error occurs
				view.printErrorMessage(new String[] { "File Error", "Error Accessing the path" });
			}
		}
	}

	/**
	 * The Class Display_Cross_Section. Will receive index, axis and maze and
	 * show its cross section.
	 */
	public class Display_Cross_Section implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if (args.length != 3) {
				// will send error if not enough args has been sent
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter <index> <axis> <maze name>" });
				return;
			}
			// parsing the args
			String index = args[0];
			String cross = args[1].toLowerCase();
			String name = args[2];
			// gets the maze from the maze list
			Maze3d maze = model.getMaze(name);
			try {
				// each axis will initiate different function from the model
				switch (cross) {
				case "z":
					view.printCross(maze.getCrossSectionByZ(Integer.parseInt(index)));
					break;
				case "y":
					view.printCross(maze.getCrossSectionByY(Integer.parseInt(index)));
					break;
				case "x":
					view.printCross(maze.getCrossSectionByX(Integer.parseInt(index)));
					break;
				default:
					view.printErrorMessage(new String[] { "cross select", "please enter x,y or z. " });
					break;
				}

			} catch (IndexOutOfBoundsException e) {
				view.printErrorMessage(new String[] { "index select", "wrong index." });
			}
		}
	}

	/**
	 * The Class save_maze. Will get a maze name and file path and save the maze
	 * (.maz extension)
	 */
	public class save_maze implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) throws IOException {
			// will send error if not enough args has been sent
			if (args.length != 2) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Filename and Filepath" });
				return;
			}
			// parsing the args
			String name = args[0];
			String file_name = args[1];
			// sending the model a command to save the maze
			model.save_maze(name, file_name);
		}
	}

	/**
	 * The Class load_maze. Will get a file name and a new name to load the
	 * saved file into
	 */
	public class load_maze implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) throws FileNotFoundException, IOException {
			// will send error if not enough args has been sent
			if (args.length != 2) {
				view.printErrorMessage(
						new String[] { "Arguments Error", "Please enter maze file location and Maze name" });
				return;
			}
			// parsing the args
			String file_name = args[0];
			String name = args[1];
			// sending the model a command to lave the maze
			model.loadMaze(file_name, name);
		}
	}

	/**
	 * The Class solveMaze3d. Will receive a maze name and an algorithm and will
	 * solve the maze
	 */
	public class solveMaze3d implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 1) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Maze name" });
				return;
			}
			// parsing the args
			String name = args[0];
			// String algorithm = args[1];
			// will send a command to the model to solve the maze
			// model.solveMaze3d(name, getAlgorithm(algorithm));
			model.solveMaze3d(name, getAlgorithm(model.getProperties().getSolveMazeAlgorithm()));

		}

		/**
		 * Gets the algorithm. a command pattern the holds all the available
		 * algorithms we can use
		 *
		 * @param algName
		 *            the alg name
		 * @return the algorithm
		 */
		public CommonSearcher<Position> getAlgorithm(String algName) {
			HashMap<String, CommonSearcher<Position>> commands = new HashMap<String, CommonSearcher<Position>>();
			commands.put("bfs", new BFS<Position>());
			commands.put("dfs", new DFS<Position>());
			return commands.get(algName.toLowerCase());
		}
	}

	/**
	 * The Class solveMaze3dfrompos.
	 */
	public class solveMaze3dfrompos implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see presenter.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 4) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Maze name and position" });
				return;
			}
			// parsing the args
			String name = args[0];
			int positionZ = Integer.parseInt(args[1]);
			int positionY = Integer.parseInt(args[2]);
			int positionX = Integer.parseInt(args[3]);
			Position position = new Position(positionZ, positionY, positionX);
			// will send a command to the model to solve the maze with the
			// specific starting position
			model.solveMaze3dFromPos(name, getAlgorithm(model.getProperties().getSolveMazeAlgorithm()), position);

		}

		/**
		 * Gets the algorithm.
		 *
		 * @param algName
		 *            the alg name
		 * @return the algorithm
		 */
		public CommonSearcher<Position> getAlgorithm(String algName) {
			HashMap<String, CommonSearcher<Position>> commands = new HashMap<String, CommonSearcher<Position>>();
			commands.put("bfs", new BFS<Position>());
			commands.put("dfs", new DFS<Position>());
			return commands.get(algName.toLowerCase());
		}
	}

	/**
	 * The Class displayMazeSolution. Will display the solution and the cost of
	 * the solution.
	 */
	public class displayMazeSolution implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 1) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Maze name" });
				return;
			}
			// parsing the args
			String name = args[0];
			// will get the solution from the solution list
			Solution<Position> solution = model.getMazeSolution(name);
			if (solution == null) {
				view.printErrorMessage(
						new String[] { "maze solution error", "can't find the solution for maze " + name });
			}
			// send command to the view to show the solution and the cost
			else {
				view.displayMazeSolution(solution);
			}
		}
	}

	/**
	 * The Class displayHint.
	 */
	public class displayHint implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			// will send error if not enough args has been sent
			if (args.length != 1) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please enter Maze name" });
				return;
			}
			// parsing the args
			String name = args[0];
			// will get the solution from the solution list
			Solution<Position> solution = model.getMazeSolution(name);
			if (solution == null) {
				view.printErrorMessage(
						new String[] { "maze solution error", "can't find the solution for maze " + name });
			}
			// send command to the view to show the solution and the cost
			else {
				view.displayHint(solution);
			}
		}
	}

	/**
	 * The Class change_xml.
	 */
	public class change_xml implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			if (args.length != 1) {
				view.printErrorMessage(new String[] { "Arguments Error", "Please specify XML file" });
				return;
			}
			// parsing the args
			String file = args[0];
			PropertiesLoader xmLoader = new PropertiesLoader();
			xmLoader.setPropertiesLoader(file);
			model.setProperties(xmLoader.getProperties());

		}
	}

	/**
	 * The Class exit. - Will exit while finishing all the threads and closing
	 * all the files
	 */
	public class exit implements Command {

		/*
		 * (non-Javadoc)
		 * 
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.saveSolutions();
			// finishing all threads in the model
			model.finishThreads();
			// closing all files in the model
			try {
				model.waitUntilCloseAllFiles();
			} catch (InterruptedException e) {
				view.printErrorMessage(new String[] { "File Error", "Error with closing all files" });
			}
			// This just terminates the program.
			System.exit(0);

		}
	}
}