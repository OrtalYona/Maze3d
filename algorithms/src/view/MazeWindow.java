package view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Properties.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;

/**
 * maze window
 * 
 * @author Ortal Yona
 *
 */
public class MazeWindow extends Observable implements Observer, View {

	protected Shell shell;
	protected Display display;
	private MazeDisplay mazeDisplay;
	protected HashMap<String, Command> commands = new HashMap<String, Command>();
	ArrayList<String> mazeName = new ArrayList<String>();
	String name = new String();
	private BufferedReader in;
	private PrintWriter out;
	Properties pro = new Properties();
	public Maze3d maze;
	String solveName = new String();
	public Solution<Position> solution = new Solution<Position>();

	public MazeWindow(BufferedReader in, PrintWriter out, Properties pro) {
		this.in = in;
		this.out = out;
		this.pro = pro;
		loadMazes();
	}

	public void start() {

		display = new Display();
		shell = new Shell(display);

		initWidgets();
		shell.open();

		// main event loop
		while (!shell.isDisposed()) { // window isn't closed
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	protected void initWidgets() {

		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);

		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		/**
		 * Generate_3d_maze
		 */
		GenerateMazeWindow win = new GenerateMazeWindow();
		win.addObserver(this);
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Start maze");
		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				win.start(display);
				setChanged();

				// notifyObservers("loadMazes");<----------------------------------
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");

		Button btnDisplayMaze = new Button(buttons, SWT.PUSH);
		btnDisplayMaze.setText("Display maze");

		/*
		 * Button btnSaveMaze = new Button(buttons ,SWT.PUSH);
		 * btnSaveMaze.setText("Save maze");
		 * 
		 * Button btnLoadMaze = new Button (buttons ,SWT.PUSH);
		 * btnLoadMaze.setText("Load maze");
		 * 
		 * Button btnHint = new Button (buttons ,SWT.PUSH);
		 * btnHint.setText("Hint");
		 */

		Button btnProperties = new Button(buttons, SWT.PUSH);
		btnProperties.setText("Properties");

		Button btnE = new Button(buttons, SWT.PUSH);
		btnE.setText("Exit");

		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();

		MazeDisplayWindows mdw = new MazeDisplayWindows(mazeName);
		mdw.addObserver(this);
		btnDisplayMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveMazes();
				mdw.start(display);
				mazeDisplay.setMazeData(maze);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		/** SolveMaze */

		btnSolveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				setChanged();
				notifyObservers("solve newMazeA bfs");// +solveName);

				loadCurrentSolution();
				mazeDisplay.setSolution(solution);
				mazeDisplay.goToTheGoal();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		/*
		 * btnSaveMaze.addSelectionListener(new SelectionListener() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent arg0) {
		 * 
		 * // String args="save_maze"+" "+mazeName; setChanged();
		 * notifyObservers("save_maze"+" "+name);//(args); }
		 * 
		 * @Override public void widgetDefaultSelected(SelectionEvent arg0) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * btnLoadMaze.addSelectionListener(new SelectionListener() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent arg0) {
		 * 
		 * //String args="load_maze"+" "+mazeName; setChanged();
		 * notifyObservers("load_maze"+" "+name); }
		 * 
		 * @Override public void widgetDefaultSelected(SelectionEvent arg0) {
		 * 
		 * 
		 * } });
		 */
		/**
		 * properties
		 */
		PropertiesWindow pw = new PropertiesWindow();
		pw.addObserver(this);
		btnProperties.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				pw.start(display);
			}

		});

		/*
		 * btnHint.addSelectionListener(new SelectionListener() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent arg0) {
		 * 
		 * loadCurrentSolution();
		 * 
		 * 
		 * }
		 * 
		 * @Override public void widgetDefaultSelected(SelectionEvent arg0) { //
		 * TODO Auto-generated method stub
		 * 
		 * } });
		 */
		/**
		 * exit
		 */
		btnE.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveMazes();
				setChanged();
				notifyObservers("exit");
				display.dispose();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

	}

	@Override
	public void notifyMazeIsReady(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifySolutionIsReady(String name) {

	}

	@Override
	public void displayMaze(Maze3d maze) {

	}

	@Override
	public void print(String s) {

		MessageBox mb = new MessageBox(shell, SWT.OK);
		mb.setMessage(s);
		mb.open();

	}

	@Override
	public void dir(String fileName) {

	}

	@Override
	public void getMaze(String name) {
		// TODO Auto-generated method stub

	}

	/**
	 * update
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg == "eraseAll") {
			mazeName.removeAll(mazeName);
			saveMazes();
			loadMazes();
			maze = null;
		}
		setChanged();
		notifyObservers(arg);
	}

	/**
	 * information
	 */
	@Override
	public void getInformation(String name) {
		this.mazeName.add(name);
	}

	/**
	 * save the mazes
	 */
	public void saveMazes() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("AllMazesNamesCatch")));
			out.writeObject(this.mazeName);
			out.close();
		} catch (IOException e1) {
		}
	}

	/**
	 * load the mazes
	 */
	@SuppressWarnings("unchecked")
	public void loadMazes() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new GZIPInputStream(new FileInputStream("AllMazesNamesCatch")));
			this.mazeName = (ArrayList<String>) in.readObject();
			in.close();
		} catch (IOException e1) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the current maze
	 */
	public void loadCurrentMaze() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("cuurentMaze")));
			this.maze = (Maze3d) ois.readObject();
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * load the current solution
	 */
	@SuppressWarnings("unchecked")
	public void loadCurrentSolution() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("cuurentSolution")));
			this.solution = (Solution<Position>) ois.readObject();
			System.out.println(solution);
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
