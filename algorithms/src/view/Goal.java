package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

/**
 * Goal image and position
 * 
 * @author
 *
 */
public class Goal {

	private Position pos;
	private Image img;

	public Goal() {
		img = new Image(null, "images/tar.jpg");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.z, cellHeight * pos.y,
				cellWidth, cellHeight);// x y
	}

}
