package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.util.zip.GZIPInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Properties.Properties;

/**
 * properties window
 * 
 * @author Ortal Yona
 *
 */
public class PropertiesWindow extends Observable {

	protected Shell shell;
	Properties pro = new Properties();
	String solve;
	String maze;

	public void start(Display display) {
		shell = new Shell(display);
		initWidgets();
		shell.open();
	}

	protected void initWidgets() {
		shell.setText("Properties");
		shell.setSize(350, 350);
		shell.setLayout(new GridLayout(2, false));

		Group lblmaze = new Group(shell, SWT.SHADOW_OUT);
		lblmaze.setText("Maze Generator:");
		lblmaze.setLayout(new GridLayout(2, true));
		Button b1 = new Button(lblmaze, SWT.RADIO);
		b1.setText("Simple");
		Button b2 = new Button(lblmaze, SWT.RADIO);
		b2.setText("GrowingTree");

		Group lblsearch = new Group(shell, SWT.PUSH);
		lblsearch.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		lblsearch.setText("Search Algorithms:");
		lblsearch.setLayout(new GridLayout(2, true));
		Button s1 = new Button(lblsearch, SWT.RADIO);
		s1.setText("BFS");
		Button s2 = new Button(lblsearch, SWT.RADIO);
		s2.setText("DFS");

		Button btnP = new Button(shell, SWT.PUSH);
		btnP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnP.setText("Save Properties");


		Button erase = new Button(shell, SWT.PUSH);
		erase.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		erase.setText("Reset Properties");

		b1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze = "simpleMaze";
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		b2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				maze = "GrowingTree";
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		s1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				solve = "bfs";

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		s2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				solve = "dfs";

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnP.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				setChanged();
				notifyObservers("setProperties" + " " + maze + " " + solve);
				//saveSolveName();
				shell.close();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		erase.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("ERASE");
				setChanged();
				notifyObservers("eraseAll");
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	void saveSolveName() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("SolveMazename")));
			this.solve = (String) ois.readObject();
			ois.close();
		} catch (IOException e1) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
