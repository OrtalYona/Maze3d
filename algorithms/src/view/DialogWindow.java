package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * class dialog window
 * 
 * @author Ortal Yona
 *
 */
public abstract class DialogWindow {
	
	protected Shell shell;

	protected abstract void initWidgets();

	public abstract String GetUpdate();

	public void start(Display display) {
		shell = new Shell(display);
		initWidgets();
		shell.open();
	}
}
