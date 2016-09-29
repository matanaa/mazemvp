package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecialCube.
 */
public class SpecialCube {

	/** The img. */
	private Image img;

	/**
	 * Instantiates a new special cube.
	 *
	 * @param type
	 *            the type
	 */
	public SpecialCube(String type) {
		super();
		setImg(type);
	}

	/**
	 * Sets the img.
	 *
	 * @param type
	 *            the new img
	 */
	public void setImg(String type) {
		ImageData ideaData = new ImageData("lib/images/" + type);
		int whitePixel = ideaData.palette.getPixel(new RGB(255, 255, 255));
		ideaData.transparentPixel = whitePixel;
		img = new Image(null, ideaData);
	}

	/**
	 * Draw.
	 *
	 * @param cellWidth
	 *            the cell width
	 * @param cellHeight
	 *            the cell height
	 * @param gc
	 *            the gc
	 * @param pos
	 *            the pos
	 */
	public void draw(int cellWidth, int cellHeight, GC gc, Position pos) {

		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y,
				cellWidth, cellHeight);

	}

}
