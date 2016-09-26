package view;

import java.io.Serializable;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

public class Character implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Position pos;
	private Image img;
// try animation
//	private ImageLoader loader;
//	private int imageNumber = 0;
//	private Thread thread;
//	private int cellWidth, cellHeight;
//	private GC mazeGC;

	public Character() {
		//regualr
		//img = new Image(null, "lib/images/character.jpg");
		
		//delete white pixsel
		ImageData ideaData = new ImageData("lib/images/character.jpg");
		 int whitePixel = ideaData.palette.getPixel(new RGB(255,255,255));
		 ideaData.transparentPixel = whitePixel;
		 img = new Image(null,ideaData);
		 
		// try animation
//		loader = new ImageLoader();
//		loader.load("lib/images/1.gif");
//		System.out.println(loader.data.length);
//		img = new Image(null, loader.data[0]);
	}

	public Character(Position pos, Image img) {
		super();
		this.pos = pos;
		this.img = img;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = new Position(pos.z, pos.y, pos.x);
		
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
//		this.cellWidth = cellWidth;
//		this.cellHeight = cellHeight;
//		this.mazeGC = gc;
		
		
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y,
				cellWidth, cellHeight);

	}

	public void moveRight() {
		pos.x++;
	}

	public void moveLeft() {
		pos.x--;
	}

	public void moveUp() {
		pos.z++;
	}

	public void moveDown() {
		pos.z--;
	}

	public void moveForeword() {
		pos.y--;
	}

	public void moveBackward() {
		pos.y++;
	}
	
/*	public void animation(){

		
			while (true) {
				System.out.println(imageNumber);
				long currentTime = System.currentTimeMillis();
				int delayTime = loader.data[imageNumber].delayTime;
				try {
					Thread.sleep(delayTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageNumber = imageNumber == loader.data.length - 1 ? 0 : imageNumber + 1;
				img = new Image(null, loader.data[imageNumber]);
				System.out.println(imageNumber);
				if (mazeGC!=null && cellHeight!=0){
				//draw(cellWidth, cellHeight, mazeGC);
				}
			}
		
	
	}*/
}
