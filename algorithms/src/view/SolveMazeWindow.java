package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

public class SolveMazeWindow extends DialogWindow{

	String update;
	
	@Override
	protected void initWidgets() {
		/*shell.setText("Solve Maze");
		shell.setSize(200, 300);		
		shell.setLocation(400,400);
		shell.setLayout(new GridLayout(1, false));	
				
		Label lblname = new Label(shell, SWT.NONE);
		lblname.setText("choose maze: ");
		
		String[] items= "".split(" ");
		Combo combo = new Combo(shell,SWT.SINGLE|SWT.DROP_DOWN);
		combo.setItems(items);
		
		Label lblname2 = new Label(shell, SWT.NONE);
		lblname2.setText("choose algorithem: ");
	 
		
		String[] items2= "BFS DFS".split(" ");
		Combo combo2 =new Combo(shell,SWT.SINGLE|SWT.DROP_DOWN);
		combo2.setItems(items2);
	
		
		
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("solve");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				String name = combo.getText(); 
				String algo = combo2.getText();
				update="solve_maze " +name+" "+algo;
				msg.setMessage("Solving maze: " +name+" with algorithem: "+algo);
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}

		});	
		*/
	}
	
	@Override
	public String GetUpdate(){
		return update;
	}
}
