package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * generate maze window
 * 
 * @author Ortal Yona
 *
 */
public class GenerateMazeWindow extends Observable {

	Shell shell;
	String name = new String();

	public void start(Display display) {
		shell = new Shell(display);
		initWidgets();
		shell.open();
	}

	protected void initWidgets() {
		shell.setText("Generate maze window");
		shell.setSize(300, 200);

		shell.setLayout(new GridLayout(2, false));

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");

		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label lblFloor = new Label(shell, SWT.NONE);
		lblFloor.setText("Floor: ");

		Text txtFloor = new Text(shell, SWT.BORDER);
		txtFloor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");

		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");

		Text txtCols = new Text(shell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");

		btnGenerateMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Title");
				int floor = Integer.parseInt(txtFloor.getText());
				int rows = Integer.parseInt(txtRows.getText());
				int cols = Integer.parseInt(txtCols.getText());

				name = txtName.getText();

				msg.setMessage("Generating maze with floor: " + floor + " rows: " + rows + " cols: " + cols);
				msg.open();
				shell.close();
				setChanged();
				notifyObservers("generate_3d_maze" + " " + name + " " + floor + " " + rows + " " + cols);// (args);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

		});

	}

}
