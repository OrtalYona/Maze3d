package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;

	public Character() {
		img = new Image(null, "images/1.jpg");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.z, cellHeight * pos.y,
				cellWidth, cellHeight);

	}
	
	/**
	 * move 
	 */

	public void moveRight() {
		pos.z++;

	}

	public void moveForward() {
		pos.y--;
	}

	public void moveBack() {
		pos.y++;
	}

	public void moveLeft() {
		pos.z--;
	}

	public void moveDown() {
		pos.x--;
	}

	public void moveUp() {
		pos.x++;
	}

}
