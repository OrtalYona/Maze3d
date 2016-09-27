package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * maze display
 * 
 * @author Ortal Yona
 *
 */
public class MazeDisplay extends Canvas {

	public Character character;
	private int[][] mazeCurFloor;
	private Goal goal;
	private WinGoal wGoal;;
	Solution<Position> sol;
	Maze3d maze;
	int[][][] tempMaze;
	int curFloor;
	String mazeName;
	Position tempPos;

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		
		///if(maze==null)
			//return;
		loadCurrentMaze();//////////////////////////////
		
		if(maze!=null){
			
		//loadCurrentMaze();/////////////////////////////////////////////////////////	
		tempMaze = maze.getMaze();
		curFloor = maze.getStartPosition().x;
		mazeCurFloor = maze.getCrossSectionByZ(curFloor);
	}
		
		tempPos=new Position(maze.getStartPosition());
		character = new Character();
		character.setPos(maze.getStartPosition());

		goal = new Goal();
		goal.setPos(new Position(1, 1, 1));

		wGoal = new WinGoal();
		wGoal.setPos(new Position(1, 1, 1));

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

				Position pos = character.getPos();//
				int temp;
				ArrayList<Position> allPossible = maze.getPossibleMoves(character.getPos());
				switch (e.keyCode) {

				/**
				 * back
				 */
				case SWT.ARROW_DOWN:
					if (Iscontains(allPossible,
							new Position(curFloor, character.getPos().y + 1, character.getPos().z))) {
						character.moveBack();
						redraw();
					}

					break;

				/**
				 * forward
				 */
				case SWT.ARROW_UP:
					if (Iscontains(allPossible,
							new Position(curFloor, character.getPos().y - 1, character.getPos().z))) {
						character.moveForward();
						redraw();
					}
					break;

				/**
				 * right
				 */
				case SWT.ARROW_RIGHT:
					if (Iscontains(allPossible,
							new Position(curFloor, character.getPos().y, character.getPos().z + 1))) {
						character.moveRight();
						redraw();
					}

					break;

				/**
				 * left
				 */
				case SWT.ARROW_LEFT:
					if (Iscontains(allPossible,
							new Position(curFloor, character.getPos().y, character.getPos().z - 1))) {

						character.moveLeft();

					}
					redraw();
					break;

				/**
				 * up
				 */
				case SWT.PAGE_UP:

					temp = curFloor;

					if (Iscontains(allPossible, new Position(temp + 1, character.getPos().y, character.getPos().z))) {
						
						character.moveUp();
						character.moveUp();
						curFloor+=2;//++
						
						mazeCurFloor = maze.getCrossSectionByZ(curFloor);

					}
					redraw();
					break;

				/**
				 * down
				 */
				case SWT.PAGE_DOWN:

					temp = curFloor;
					if (Iscontains(allPossible, new Position(temp - 1, character.getPos().y, character.getPos().z))) {

						character.moveDown();
						character.moveDown();

						curFloor-=2;//--
						mazeCurFloor = maze.getCrossSectionByZ(curFloor);

					}

					redraw();
					break;
				}
			}

			private boolean Iscontains(ArrayList<Position> allPossible, Position position) {
				for (Position pos : allPossible) {

					if ((pos.getX() == position.x) && (pos.getY() == position.y) && (pos.getZ() == position.z))
						return true;

				}
				return false;
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		});

		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				
				Position pos =new Position(maze.getStartPosition());
				
				if (maze != null){
					curFloor = maze.getStartPosition().x;
				}
						
					
				e.gc.setBackground(new Color(null, 0, 0, 0));
				e.gc.setForeground(new Color(null, 255, 255, 255));

				int width = getSize().x;
				int height = getSize().y;

				if (mazeCurFloor != null) {
					
					
					character.setPos(maze.getStartPosition());


					goal.setPos(maze.getGoalPosition());

					wGoal.setPos(maze.getGoalPosition());

					int w = width / mazeCurFloor[0].length;
					int h = height / mazeCurFloor.length;

					for (int i = 0; i < mazeCurFloor.length; i++)
						for (int j = 0; j < mazeCurFloor[i].length; j++) {
							int x = j * w;
							int y = i * h;
							if (mazeCurFloor[i][j] != 0)
								e.gc.fillRectangle(x, y, w, h);
						}

					character.draw(w, h, e.gc);

					if (goal.getPos().x == curFloor) {
						goal.draw(w, h, e.gc);

						if (wGoal.getPos() == maze.getGoalPosition()) {
							wGoal.draw(w, h, e.gc);
										
						}
					//	System.exit(0);

					}
				

				}

				e.gc.drawString(
						"floor: " + curFloor + "  row: " + character.getPos().y + "  col: " + character.getPos().z, 5,
						5, false);
			
				
				}
		});
	}
	

	public void setMazeData(String name, Maze3d md) {
		
		this.mazeName = name;
		this.maze = md;
		
		setMazeCurFloor(maze.getCrossSectionByZ(maze.getStartPosition().x));//new
		
	//	redraw();/////////////////////////////////////////////

	}

	public void mazeDisplay(Composite parent, int style) {
	}

	void setMazeCurFloor(int[][] t) {
		mazeCurFloor = t;
	}

	/**
	 * load current maze
	 */
	public void loadCurrentMaze() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("cuurentMaze")));// cuurentMaze
			this.mazeName = (String) ois.readObject();
			this.maze = (Maze3d) ois.readObject();
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	Position getCurentPosition() {
		return character.getPos();
	}

	void setSolution(Solution<Position> t) {
		this.sol = t;
	}

	void goToTheGoal(int num) {

		int k = 0;

		TimerTask task = new TimerTask() {

			String where;

			int i = 0;

			int loops = sol.getSize() - 1;

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {

						if (num == 0) {
							loops = 1;
						}

						if (i < loops) {

							where = whereToMove(sol.getStates().get(i).getPosition(),
									sol.getStates().get(i + 1).getPosition());

							int temp;
							switch (where) {
							case "down":
								
								temp = curFloor;
								
								character.moveDown();
								curFloor--;
								mazeCurFloor = maze.getCrossSectionByZ(curFloor);

								break;
							case "up":
								temp = curFloor;
								character.moveUp();
								curFloor++;
								mazeCurFloor = maze.getCrossSectionByZ(curFloor);
								break;
							case "forward":
								character.moveForward();
								redraw();
								break;
							case "left":
								character.moveLeft();
								break;
							case "back":
								character.moveBack();
								break;
							case "right":
								character.moveRight();
								break;
							default:
								break;
							}
							redraw();
						}
						i++;

					}

				});

			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);

	}

	/**
	 * check where to move
	 * 
	 * @param now
	 * @param to
	 * @return
	 */
	String whereToMove(Position now, Position to) {

		int x = now.x - to.x;
		int y = now.y - to.y;
		int z = now.z - to.z;

		if (x != 0) {
			if (x == -1)
				return "up";
			else
				return "down";

		}
		if (y != 0) {
			if (y == -1)
				return "back";

			else
				return "forward";
		}
		if (z != 0) {
			if (z == -1)
				return "right";
			else
				return "left";
		}
		return null;
	}

}
