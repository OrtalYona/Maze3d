package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Properties.Properties;
import presenter.Command;

public class MazeWindow extends DialogWindow{// Observable implements Observer,View {

    protected Shell shell;	
	protected Display display;

	public void start() {
		display = new Display();
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
		
		// main event loop
		while(!shell.isDisposed()){ // window isn't closed
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}

	Properties pro=new Properties();
	private MazeDisplay mazeDisplay;
	protected HashMap<String, Command> commands=new HashMap<String,Command>();
	String mazeName;
	//protected HashMap<String, Maze3d> mazeName=new HashMap<String,Maze3d>();
	private BufferedReader in;
	private PrintWriter out;
	View view=new MyView(out, in);
	
	@Override
	protected void initWidgets() {
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		
	
	//	win.addObserver(this);
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow();
				win.start(display);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub	
			}
		});
				
		
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		Button btnSaveMaze = new Button(buttons ,SWT.PUSH); 
		btnSaveMaze.setText("Save maze"); 
				
		Button btnLoadMaze = new Button (buttons ,SWT.PUSH); 
		btnLoadMaze.setText("Load maze"); 
		
		Button btnHint = new Button (buttons ,SWT.PUSH); 
		btnHint.setText("Hint"); 
		
		Button btnProperties = new Button (buttons ,SWT.PUSH); 
		btnProperties.setText("Properties"); 
		
		
		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);	
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				String algorithm=pro.getGenerateMazeAlgorithm();
				
			//	String[] args= {"solve", mazeName,algorithm}; 
				String args="solve"+" " +mazeName +" "+ algorithm;
				commands.get("solve"); 
			//	setChanged();
				//notifyObservers(args);
				view.update(args);	
			}
			

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		btnSaveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//String[] args={"save_maze",mazeName};
				String args="save_maze"+" "+mazeName;

				commands.get("save_maze");
				//setChanged();
			//	notifyObservers(args);

				view.update(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		btnLoadMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//String[] args={"load_maze",mazeName};
				String args="load_maze"+" "+mazeName;
				commands.get("load_maze");
				//setChanged();
				//notifyObservers(args);
				view.update(args);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
					
		//pw.addObserver(this);
		btnProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				PropertiesWindow pw=new PropertiesWindow();
					pw.start(display);
				}
			
		});
		
		btnHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}

	public String getMazeName() {
		return mazeName;
	}
}
