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
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class MazeDisplay extends Canvas {
	private Maze3d maze;
	private int[][] mazeData;
	protected boolean win = false;
	private Character character;
	private int scale =1;

	private SpecialCube startCube = new SpecialCube("start.png");
	private SpecialCube goalCube = new SpecialCube("goal.jpg");
	private SpecialCube stairUp = new SpecialCube("stairs_up.png");
	private SpecialCube stairDown = new SpecialCube("stairs_down.png");
	private SpecialCube wallCube = new SpecialCube("wall.png");
	private SpecialCube roadCube =new SpecialCube("road.jpg");
	

	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		this.setBackground(new Color(null, 233, 232, 233));
		character = new Character();
		character.setPos(new Position(0, 0, 0));
		
		/*this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent e) {
				int wheelCount = e.count;
				if ((e.stateMask & SWT.CONTROL) == SWT.CONTROL) {
					if (wheelCount > 0) {
						scale += .2;
						redraw();
					} else if (wheelCount < 0 && scale >= 1.2) {
						scale -= .2;
						redraw();
					}
				}
			}
		});*/

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (mazeData==null){
					return;
				}
				KeyManaget(e);


			}
		});

		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;

				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));

				int width = getSize().x;
				int height = getSize().y;

				int w = width / mazeData[0].length;
				int h = height / mazeData.length;

				for (int i = 0; i < mazeData.length; i++)
					for (int j = 0; j < mazeData[i].length; j++) {
						int x = j * w;
						int y = i * h;
						if (mazeData[i][j] != 0) {
							// e.gc.fillRectangle(x, y, w, h);
							wallCube.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
						} else {
							// check for staris
							ArrayList<Position> moves = maze.getPossibleMoves(new Position(character.getPos().z, i, j));
							roadCube.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
							if (moves.contains(new Position(character.getPos().z + 1, i, j))) {// up
								stairUp.draw(w, h, e.gc, (new Position(character.getPos().z + 1, i, j)));
							} else if (moves.contains(new Position(character.getPos().z - 1, i, j))) {// down
								stairDown.draw(w, h, e.gc, (new Position(character.getPos().z - 1, i, j)));
							}

						}

					}

				if (character.getPos().z == maze.getStartPos().z) {
					startCube.draw(w, h, e.gc, maze.getStartPos());
				}

				if (character.getPos().z == maze.getGoalPos().z) {
					goalCube.draw(w, h, e.gc, maze.getGoalPos());
				}
				character.draw(w, h, e.gc);

				if (character.getPos().equals(maze.getGoalPos()) && !win) {
					winnerEvent();
				}

			}
		});
	}

	public void setCharacterPos(Position pos) {
		character.setPos(new Position(pos.z, pos.y, pos.x));
	}

	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		this.redraw();
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
		setMazeData(maze.getCrossSectionByZ(maze.getStartPos().z));
		setCharacterPos(maze.getStartPos());
		win = false;
	}

	public void printSolution(Solution<Position> solution) {

		// i=solution.getSolution().indexOf(new State<Position>
		// (character.getPos()) );

		TimerTask task = new TimerTask() {
			int i = solution.getSolution().indexOf(new State<Position> (character.getPos()) );

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {

						if (i == solution.getSolution().size()) {
							cancel();
							return;
							// TODO: fix this line
						}

						character.setPos(solution.getSolution().get(i++).getState());
						setMazeData(maze.getCrossSectionByZ(character.getPos().z));
						redraw();
					}
				});

			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);

	}


	public void prinHint(Solution<Position> solution) {

		// i=solution.getSolution().indexOf(new State<Position>
		// (character.getPos()) );

		TimerTask task = new TimerTask() {
			int i = solution.getSolution().indexOf(new State<Position> (character.getPos()) );

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {

						if (i == solution.getSolution().size()) {
							cancel();
							return;
							// TODO: fix this line
						}

						character.setPos(solution.getSolution().get(i++).getState());
						setMazeData(maze.getCrossSectionByZ(character.getPos().z));
						redraw();
					}
				});

			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);

	}
	
	private void winnerEvent() {
		MessageBox msgBox = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
		msgBox.setText("win!");
		msgBox.setMessage("win!");
		msgBox.open();
		win = true;
		
	}
	
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
