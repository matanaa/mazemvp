package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	private Character character ;
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
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:					
					//character.setPos(new Position(pos.x + 1, pos.y));
					character.moveRight();
					redraw();
					break;
				
				case SWT.ARROW_LEFT:					
					character.moveLeft();
					redraw();
					break;
				case SWT.ARROW_UP:					
					character.moveForeword();
					redraw();
					break;
				case SWT.ARROW_DOWN:					
					character.moveBackward();
					redraw();
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
				      }

				   character.draw(w, h, e.gc);
			}
		});
	}
	
	public void setCharacterPos(Position pos){
		character.setPos(pos);
	}
	
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		this.redraw();
	}
}
