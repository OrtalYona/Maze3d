package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonCharacter extends Canvas {//implements Character

	int cellX;
	int cellY;
	
	public CommonCharacter(Composite parent, int style) {
		super(parent, style);
	}

/*	@Override 
	public void drawCharacter() { 
	 	this.redraw(); 
	}
*/
	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	} 
	
}
