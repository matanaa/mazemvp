package view;

import java.io.Serializable;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Character - In charge of all of our character display and actions.
 */
public class Character implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The pos. */
	private Position pos;

	/** The img. */
	private Image img;

	/**
	 * Instantiates a new character.
	 */
	public Character() {
		// Gets the image
		ImageData ideaData = new ImageData("lib/images/character.png");
		img = new Image(null, ideaData);

	}

	/**
	 * Instantiates a new character With an image and a position.
	 *
	 * @param pos
	 *            the pos
	 * @param img
	 *            the img
	 */
	public Character(Position pos, Image img) {
		super();
		this.pos = pos;
		this.img = img;
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public Position getPos() {
		return pos;
	}

	/**
	 * Gets the pos Z.
	 *
	 * @return the pos Z
	 */
	public int getPosZ() {
		return pos.getZ();
	}

	/**
	 * Gets the pos Y.
	 *
	 * @return the pos Y
	 */
	public int getPosY() {
		return pos.getY();
	}

	/**
	 * Gets the pos X.
	 *
	 * @return the pos X
	 */
	public int getPosX() {
		return pos.getX();
	}

	/**
	 * Sets the pos.
	 *
	 * @param pos
	 *            the new pos
	 */
	public void setPos(Position pos) {
		this.pos = new Position(pos.z, pos.y, pos.x);

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
	 */
	public void draw(int cellWidth, int cellHeight, GC gc) {

		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y,
				cellWidth, cellHeight);

	}

	/**
	 * Move right.
	 */
	public void moveRight() {
		pos.x++;
	}

	/**
	 * Move left.
	 */
	public void moveLeft() {
		pos.x--;
	}

	/**
	 * Move up.
	 */
	public void moveUp() {
		pos.z++;
	}

	/**
	 * Move down.
	 */
	public void moveDown() {
		pos.z--;
	}

	/**
	 * Move foreword.
	 */
	public void moveForeword() {
		pos.y--;
	}

	/**
	 * Move backward.
	 */
	public void moveBackward() {
		pos.y++;
	}

}
