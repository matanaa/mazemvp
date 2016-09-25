package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

public class SpecialCube {
	private Image img;
	public SpecialCube(String type) {
		super();
		ImageData ideaData = new ImageData("lib/images/"+type);
		 int whitePixel = ideaData.palette.getPixel(new RGB(255,255,255));
		 ideaData.transparentPixel = whitePixel;
		 img = new Image(null,ideaData);
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc,Position pos){
		

			
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y,
					cellWidth, cellHeight);


	}
	
}
