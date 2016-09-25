package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	private Maze3d maze;
	private int[][] mazeData;
	private Character character ;
	private SpecialCube startCube =new SpecialCube("start.jpg");
	private SpecialCube goalCube =new SpecialCube("goal.jpg");
	private SpecialCube stairUp =new SpecialCube("stairs_up.png");
	
	public MazeDisplay(Shell parent, int style) {
		super(parent, style);
		character = new Character();
		character.setPos(new Position(0,0,0));
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPos();
				ArrayList<Position> moves =maze.getPossibleMoves(pos);
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:					
					if (moves.contains(new Position(pos.z, pos.y, pos.x+1))){
					character.moveRight();
					redraw();
					}
					break;
				
				case SWT.ARROW_LEFT:	
					if (moves.contains(new Position(pos.z, pos.y, pos.x-1))){
					character.moveLeft();
					redraw();
					}
					break;
				case SWT.ARROW_UP:	
					if (moves.contains(new Position(pos.z, pos.y-1, pos.x))){
					character.moveForeword();
					redraw();
					}
					break;
				case SWT.ARROW_DOWN:					
					if (moves.contains(new Position(pos.z, pos.y+1, pos.x))){
					character.moveBackward();
					redraw();
					}
					break;
				case SWT.PAGE_UP:
					if (moves.contains(new Position(pos.z+1, pos.y, pos.x))){
						mazeData = maze.getCrossSectionByZ(pos.z+1);
					character.moveUp();
					redraw();
					}
					break;
				case SWT.PAGE_DOWN:
					if (moves.contains(new Position(pos.z-1, pos.y, pos.x))){
						mazeData = maze.getCrossSectionByZ(pos.z-1);
					character.moveDown();
					redraw();
					}
					break;
				}
				
				
				
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;
				
				   e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));

				   int width=getSize().x;
				   int height=getSize().y;

				   int w=width/mazeData[0].length;
				   int h=height/mazeData.length;

				   for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!=0)
				              e.gc.fillRectangle(x,y,w,h);
			          else {
				        	  ArrayList<Position> moves =maze.getPossibleMoves(new Position(character.getPos().z, i, j));
								if (moves.contains(new Position(character.getPos().z+1, i, j))){
									stairUp.draw(w, h, e.gc,(new Position(character.getPos().z+1, i, j)));
								}
				        	  
				          }
				      }
				   
				   if (character.getPos().z == maze.getStartPos().z){
				   startCube.draw(w, h, e.gc,maze.getStartPos());
				   }
				   
				   if (character.getPos().z == maze.getGoalPos().z){
				   goalCube.draw(w, h, e.gc,maze.getGoalPos());
				   }
				   character.draw(w, h, e.gc);
				  
				   
			}
		});
	}
	
	public void setCharacterPos(Position pos){
		character.setPos(new Position(pos.z, pos.y, pos.x));
	}
	
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		this.redraw();
	}

	public void setMaze(Maze3d maze) {
this.maze =maze;		
	}
}
