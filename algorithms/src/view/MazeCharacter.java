package view;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.GC;

import algorithms.mazeGenerators.Position;

public class MazeCharacter {//extends CommonCharacter
	private Image img;
	private Position pos;
	ImageData[] image;
	int index;
	ImageLoader gifs = new ImageLoader();
	
	public MazeCharacter(Composite parent, int style) {
	
        this.image=gifs.load("‪algorithms/lib/Cinderella.jpg‬");//
		//img = new Image(null, "images/character.jpg");
	}

/*	@Override
	public ImageData[] getCharacterImagesArray() {
		return image;
	}

	@Override
	public void setCharacterImageArray(ImageData[] image) {
         this.image=image;
	}
*/
	
	public int getCharacterImageIndex() {
		return index;
	}

	public void setCharacterImageIndex(int index) {
		this.index=index;
	}
	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}

}
