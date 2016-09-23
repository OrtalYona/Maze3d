package view;

import java.util.ArrayList;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * class maze display window
 * @author Ortal Yona
 *
 */
public class MazeDisplayWindows extends Observable {
	
	String update;
	Shell shell;	
	ArrayList<String> mazeNames=new ArrayList<>();
	
	public MazeDisplayWindows(ArrayList<String> names ){
		this.mazeNames=names;
	}

	String[] getNames(){
		
		String[] s = new String[mazeNames.size()];
		for(int i=0;i<mazeNames.size();i++)
			s[i]=mazeNames.get(i);
		
		return s;
		
	}
	
	
	public void start(Display display) {		
		shell = new Shell(display);
		initWidgets();
		shell.open();		
	}
	

	protected void initWidgets() {	
		
		shell.setText("Display Maze");
		shell.setSize(400, 300);		
		shell.setLocation(400,400);
		shell.setLayout(new GridLayout(2, false));	
		
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("choose maze: ");
		
		
		
		String[] items=getNames();
		Combo combo =new Combo(shell,SWT.SINGLE|SWT.DROP_DOWN);
		combo.setItems(items);
		
		Button btnDisplayMaze = new Button(shell, SWT.PUSH);
		btnDisplayMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnDisplayMaze.setText("Display maze");
	
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Display maze");
				String args=combo.getText();
				setChanged();
				notifyObservers("display_maze"+" "+args);
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		
		});
	}

}
