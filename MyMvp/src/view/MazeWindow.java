package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import properties.PropertiesLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeWindow.
 */
public class MazeWindow extends BasicWindow implements View {

	/** The maze display. */
	private MazeDisplay mazeDisplay;

	/** The maze name. */
	private String mazeName;

	/** The solution cmd. */
	private String solutionCmd = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.BasicWindow#initWidgets()
	 */
	@Override
	protected void initWidgets() {
		int scale = 1;
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);
		shell.setText("MaTan & Snir MaZe 3D");
		shell.setBackground(new Color(null, 204, 229, 255));

		// check exit
		shell.addListener(SWT.Close, new Listener() {// exit event
			public void handleEvent(Event event) {
				exitEvent(event);
			}

		});

		showMenu(this.shell);
		mazeDisplay = new MazeDisplay(this.shell, SWT.DOUBLE_BUFFERED);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	/**
	 * Show menu.
	 *
	 * @param shell
	 *            the shell
	 */
	protected void showMenu(Shell shell) {

		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		btnGroup.setBackground(new Color(null, 102, 178, 255));
		rowLayout.pack =false;
		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		btnGenerateMaze.setBackground(new Color(null, 102, 178, 255));
		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		Button btnLoadMaze = new Button(btnGroup, SWT.PUSH);
		btnLoadMaze.setText("Load maze");
		btnLoadMaze.setBackground(new Color(null, 102, 178, 255));
		btnLoadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showLoadMazeOption();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnSaveMazeToFile = new Button(btnGroup, SWT.PUSH);
		btnSaveMazeToFile.setText("Save maze to file");
		btnSaveMazeToFile.setBackground(new Color(null, 102, 178, 255));
		btnSaveMazeToFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showSaveMazeToFileOption();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnLoadMazeFromFile = new Button(btnGroup, SWT.PUSH);
		btnLoadMazeFromFile.setText("Load maze from file");
		btnLoadMazeFromFile.setBackground(new Color(null, 102, 178, 255));
		btnLoadMazeFromFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showLoadMazeFromFileOption();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);

		btnSolveMaze.setText("Solve maze");
		btnSolveMaze.setBackground(new Color(null, 102, 178, 255));
		btnSolveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// if (mazeName != null) {
				solutionCmd = "display_solution";
				setChanged();
				notifyObservers("solve " + mazeName);
				// }

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnHint = new Button(btnGroup, SWT.PUSH);

		btnHint.setText("Show Hint");
		btnHint.setBackground(new Color(null, 102, 178, 255));
		btnHint.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mazeName != null) {
					solutionCmd = "display_hint";
					setChanged();
					notifyObservers("solve " + mazeName);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnLoadXML = new Button(btnGroup, SWT.PUSH);
		btnLoadXML.setText("Load Properties File");
		btnLoadXML.setBackground(new Color(null, 102, 178, 255));
		btnLoadXML.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "XML Files", "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.xml", "*.*" });
				dialog.setFilterPath("c:\\");
				dialog.open();

				setChanged();
				notifyObservers("change_xml " + dialog.getFilterPath() + "\\" + dialog.getFileName());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnExit = new Button(btnGroup, SWT.PUSH);
		btnExit.setText("Exit game");
		btnExit.setBackground(new Color(null, 102, 178, 255));
		btnExit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				exitEvent(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Show generate maze options.
	 */
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(250, 200);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		Text txtName = new Text(shell, SWT.BORDER);

		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);

		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);

		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		Text txtCols = new Text(shell, SWT.BORDER);

		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate_maze " + txtName.getText() + " " + txtFloors.getText() + " "
						+ txtRows.getText() + " " + txtCols.getText());
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.open();
	}

	/**
	 * Show load maze option.
	 */
	protected void showLoadMazeOption() {
		Shell shell = new Shell();
		shell.setText("Load Maze");
		shell.setSize(200, 100);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		// Combo txtName = new Combo(shell, SWT.BORDER);
		// txtName.add("");
		Text txtName = new Text(shell, SWT.BORDER);

		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Load");
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mazeName = txtName.getText();
				setChanged();
				notifyObservers("display_maze " + txtName.getText());
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.open();
	}

	/**
	 * Show save maze to file option.
	 */
	protected void showSaveMazeToFileOption() {
		Shell shell = new Shell();
		shell.setText("Save Maze To File");
		shell.setSize(400, 150);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Maze name: ");
		Text name = new Text(shell, SWT.BORDER | SWT.FILL);
		Button lauchSaveDialog = new Button(shell, SWT.PUSH);
		lauchSaveDialog.setText("Save");
		lauchSaveDialog.setBackground(new Color(null, 102, 178, 255));
		lauchSaveDialog.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveMazeToFileDialog(name.getText());
				shell.close();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		shell.open();
	}

	/**
	 * Save maze to file dialog.
	 *
	 * @param mazeName
	 *            the maze name
	 */
	protected void saveMazeToFileDialog(String mazeName) {
		Shell shell = new Shell();

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterNames(new String[] { "Maze Files", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.maz" });
		dialog.setFilterPath("c:\\");
		dialog.setFileName(mazeName);
		dialog.open();

		setChanged();
		notifyObservers("save_maze" + " " + mazeName + " " + dialog.getFilterPath() + "\\" + dialog.getFileName());
		shell.close();
	}

	/**
	 * Show load maze from file option.
	 */
	protected void showLoadMazeFromFileOption() {
		Shell shell = new Shell();
		shell.setText("Load Maze From File");
		shell.setSize(400, 150);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Maze name: ");
		Text name = new Text(shell, SWT.BORDER | SWT.FILL);
		Button lauchLoadDialog = new Button(shell, SWT.PUSH);
		lauchLoadDialog.setText("Load");
		lauchLoadDialog.setBackground(new Color(null, 102, 178, 255));
		lauchLoadDialog.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				loadMazeToFileDialog(name.getText());
				shell.close();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.open();
	}

	/**
	 * Load maze to file dialog.
	 *
	 * @param mazeName
	 *            the maze name
	 */
	protected void loadMazeToFileDialog(String mazeName) {
		Shell shell = new Shell();

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterNames(new String[] { "Maze Files", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.maz" });
		dialog.setFilterPath("c:\\");
		dialog.setFileName(mazeName);
		dialog.open();

		setChanged();
		notifyObservers("load_maze" + " " + dialog.getFilterPath() + "\\" + dialog.getFileName() + " " + mazeName);
		shell.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Maze " + name + " is ready");
				msg.open();

				setChanged();
				notifyObservers("display_maze " + name);
				mazeName = name;

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#displayMaze(algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		if (maze == null) {

			this.printErrorMessage(new String[] { "Maze not found", "can't find the maze" });
			return;
		}
		mazeDisplay.setMaze(maze);
		mazeDisplay.forceFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#start()
	 */
	@Override
	public void start() {

		run();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printAnswers(java.lang.String[])
	 */
	@Override
	public void printAnswers(String[] msg) {

		MessageBox msgBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		if (msg[0].isEmpty() == false) {
			msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false) {
			msgBox.setMessage(msg[1]);
		}
		msgBox.open();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printCross(int[][])
	 */
	@Override
	public void printCross(int[][] cross) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#notifySolutionIsReady(java.lang.String)
	 */
	@Override
	public void notifySolutionIsReady(String name) {
		setChanged();
		notifyObservers(solutionCmd + " " + name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#displayMazeSolution(algorithms.search.Solution)
	 */
	@Override
	public void displayMazeSolution(Solution<Position> solution) {
		mazeDisplay.printSolution(solution);
	}

	/**
	 * Display maze hint.
	 *
	 * @param solution
	 *            the solution
	 */
	public void displayMazeHint(Solution<Position> solution) {
		mazeDisplay.prinHint(solution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.View#printErrorMessage(java.lang.String[])
	 */
	@Override
	public void printErrorMessage(String[] msg) {
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_ERROR);
		if (msg[0].isEmpty() == false) {
			msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false) {
			msgBox.setMessage(msg[1]);
		}
		msgBox.open();

	}

}
