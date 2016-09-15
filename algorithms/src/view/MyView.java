package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;


public class MyView extends Observable implements View, Observer {

	/**CLI */
	
	CLI cli;
	
	/**out*/
	PrintWriter out;
	
	/** in*/
	BufferedReader in;

	
	/** C'tor
	 * @param out
	 * @param in*/
	
	public MyView(PrintWriter out,BufferedReader in) {
		this.out=out;
		this.in=in;
		
		cli = new CLI(out, in);
		cli.addObserver(this);
	}

	@Override
	public void notifyMazeIsReady(String name) {
    System.out.println("Maze Is Ready");		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);	
		out.flush();

	}

	@Override
	public void start() throws Exception {

		cli.start();
	}

	@Override
	public void print(String s) {
		System.out.print(s);
		out.flush();

	}
	
	@Override
	public void dir(String fileName) {
		
		File file = new File(fileName);

		if(!file.exists()){
			out.println("No have file or directory");
		}
		else{
			File[] list = file.listFiles();
			for (File f : list) {
				if (f.isFile()) {
					out.println(f.getName());
				}
				if(f.isDirectory()){
					out.println(f.getName());
				}
			}
		}
	}

	@Override
	public void notifySolutionIsReady(String name) {
	    System.out.println("Solution for " + name + " Is Ready");			
	}

	@Override
	public void update(Observable obs, Object arg) {
		if (obs == cli) {
			setChanged();
			notifyObservers(arg);
		}
		
	}

}
