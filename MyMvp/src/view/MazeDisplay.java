package view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeDisplay - In charge of how the maze will look like.
 */
public class MazeDisplay extends Canvas {

	/** The maze. */
	private Maze3d maze;

	/** The maze data. */
	private int[][] mazeData;

	/** The win. */
	protected boolean win = false;

	/** The character. */
	private Character character;

	/** The start cube. */
	private SpecialCube startCube = new SpecialCube("start.png");

	/** The goal cube. */
	private SpecialCube goalCube = new SpecialCube("goal1.png");

	/** The stair up. */
	private SpecialCube stairUp = new SpecialCube("stairs_up.png");

	/** The stair down. */
	private SpecialCube stairDown = new SpecialCube("stairs_down.png");

	/** The wall cube. */
	private SpecialCube wallCube = new SpecialCube("wall.png");

	/** The road cube. */
	private SpecialCube roadCube = new SpecialCube("road.jpg");

	/** The hint cube. */
	//private SpecialCube hintCube = new SpecialCube("hint.png");

	/**
	 * Gets the character.
	 *
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Sets the character.
	 *
	 * @param character
	 *            the new character
	 */
	public void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 * Instantiates a new maze display.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		this.setBackground(new Color(null, 233, 232, 233));
		initCharacter();
		addListeners();
		drawMaze();
	}

	/**
	 * Adds the listeners.
	 */
	protected void addListeners() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (mazeData == null) {
					return;
				}
				KeyManaget(e);

			}
		});
	}

	/**
	 * Inits the character.
	 */
	protected void initCharacter() {
		character = new Character();
		character.setPos(new Position(0, 0, 0));

	}

	/**
	 * Draw maze.
	 */
	protected void drawMaze() {

		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null) {
					Image imgBack = new Image(null, "lib/images/menu.jpg");
					e.gc.drawImage(imgBack, 0, 0, imgBack.getBounds().width, imgBack.getBounds().height, 0, 0,
							getSize().x, getSize().y);
					return;
				}

				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int w = width / mazeData[0].length;
				int h = height / mazeData.length;

				for (int i = 0; i < mazeData.length; i++)
					for (int j = 0; j < mazeData[i].length; j++) {
						if (mazeData[i][j] != 0) {
							wallCube.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
						} else {
							// check for stairs
							ArrayList<Position> moves = maze.getPossibleMoves(new Position(character.getPos().z, i, j));
							// up
							roadCube.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
							if (moves.contains(new Position(character.getPos().z + 1, i, j))) {
								stairUp.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
								// down
							} else if (moves.contains(new Position(character.getPos().z - 1, i, j))) {
								stairDown.draw(w, h, e.gc, (new Position(character.getPos().z - 1, i, j)));
							}

						}

					}
				// draw the starting position
				if (character.getPos().z == maze.getStartPos().z) {
					startCube.draw(w, h, e.gc, maze.getStartPos());
				}
				// draw the goal positions
				if (character.getPos().z == maze.getGoalPos().z) {
					goalCube.draw(w, h, e.gc, maze.getGoalPos());
				}
				// draw the character
				character.draw(w, h, e.gc);
				// in case the character starts in the finish line
				if (character.getPos().equals(maze.getGoalPos()) && !win) {
					winnerEvent();
					forceFocus();
				}

			}
		});

	}

	/**
	 * Sets the character pos.
	 *
	 * @param pos
	 *            the new character pos
	 */
	public void setCharacterPos(Position pos) {
		character.setPos(new Position(pos.z, pos.y, pos.x));
	}

	/**
	 * Sets the maze data.
	 *
	 * @param mazeData
	 *            the new maze data
	 */
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		this.redraw();
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze
	 *            the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
		setMazeData(maze.getCrossSectionByZ(maze.getStartPos().z));
		setCharacterPos(maze.getStartPos());
		win = false;
	}

	/**
	 * Prints the solution.
	 *
	 * @param solution
	 *            the solution
	 */
	public void printSolution(Solution<Position> solution) {

		// Time task that make sure that each step will take place after certain
		// amount of time
		TimerTask task = new TimerTask() {
			int i = solution.getSolution().indexOf(new State<Position>(character.getPos()));

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {

						if (i == solution.getSolution().size()) {
							cancel();
							return;
						}
						if (i == -1) {
							i = 0;
						}

						character.setPos(solution.getSolution().get(i++).getState());
						setMazeData(maze.getCrossSectionByZ(character.getPos().z));
						redraw();
					}
				});

			}
		};
		Timer timer = new Timer();
		// The timer properties
		timer.scheduleAtFixedRate(task, 0, 200);

	}

	/**
	 * Prints the hint - right now shows only hint from the start position.
	 * needs fix TODO fix it
	 *
	 * @param solution
	 *            the solution
	 */
	public void printHint(Solution<Position> solution) {

		getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				character.setPos(solution.getSolution().get(1).getState());
				setMazeData(maze.getCrossSectionByZ(character.getPos().z));
				redraw();
			}
		});

	}

	/**
	 * Winner event.
	 */
	private void winnerEvent() {
		MessageBox msgBox = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
		msgBox.setText("Congratulations!");
		msgBox.setMessage("You Win!");
		msgBox.open();
		win = true;

	}

	/**
	 * Key managet.
	 *
	 * @param e
	 *            the e
	 */
	private void KeyManaget(KeyEvent e) {
		Position pos = character.getPos();
		ArrayList<Position> moves = maze.getPossibleMoves(pos);
		switch (e.keyCode) {
		case SWT.ARROW_RIGHT:
			if (moves.contains(new Position(pos.z, pos.y, pos.x + 1))) {
				character.moveRight();
				redraw();
			}
			break;

		case SWT.ARROW_LEFT:
			if (moves.contains(new Position(pos.z, pos.y, pos.x - 1))) {
				character.moveLeft();
				redraw();
			}
			break;
		case SWT.ARROW_UP:
			if (moves.contains(new Position(pos.z, pos.y - 1, pos.x))) {
				character.moveForeword();
				redraw();
			}
			break;
		case SWT.ARROW_DOWN:
			if (moves.contains(new Position(pos.z, pos.y + 1, pos.x))) {
				character.moveBackward();
				redraw();
			}
			break;
		case SWT.PAGE_UP:
			if (moves.contains(new Position(pos.z + 1, pos.y, pos.x))) {
				mazeData = maze.getCrossSectionByZ(pos.z + 1);
				character.moveUp();
				redraw();
			}
			break;
		case SWT.PAGE_DOWN:
			if (moves.contains(new Position(pos.z - 1, pos.y, pos.x))) {
				mazeData = maze.getCrossSectionByZ(pos.z - 1);
				character.moveDown();
				redraw();
			}
			break;
		default:
			break;
		}
	}

}
