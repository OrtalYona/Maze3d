package view;

import org.eclipse.swt.SWT;import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;

import presenter.Command;

public class PropertiesWindow extends DialogWindow{

	@Override
	protected void initWidgets() {
		shell.setText("Properties");
		shell.setSize(350, 350);
		shell.setLayout(new GridLayout(2, false));	
		
		
		Group lblmaze = new Group(shell, SWT.SHADOW_OUT);
		lblmaze.setText("Maze Generator:");
		lblmaze.setLayout(new GridLayout(2,true));
		Button b1=new Button(lblmaze,SWT.RADIO);
		b1.setText("Simple");
		Button b2=new Button(lblmaze,SWT.RADIO);
		b2.setText("GrowingTree");
					
		Group lblsearch = new Group(shell, SWT.PUSH);
		lblsearch.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblsearch.setText("Search Algorithms:");
		lblsearch.setLayout(new GridLayout(2,true));
		Button s1=new Button(lblsearch,SWT.RADIO);
		s1.setText("BFS");
		Button s2=new Button(lblsearch,SWT.RADIO);
		s2.setText("DFS");
		
		
		Button btnP = new Button(shell, SWT.PUSH);
		btnP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnP.setText("Save Properties");
		
		Button btnR = new Button(shell, SWT.PUSH);
		btnR.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnR.setText("Reset Properties");
		
		b1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		b2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		s1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				String[] args={"bfs"};
			//	Command command= commands.get("save_maze");
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		s2.addSelectionListener(new SelectionListener() {
			
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
	
}
