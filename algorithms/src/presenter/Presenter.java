package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


import model.Model;
import view.View;

/**
 * interface Presenter
 * @author Ortal Yona
 *
 */
public class Presenter implements Observer{
	
	private Model model;
	private View view;
	private CommandManager commandsManager;
	private HashMap<String, Command> commands;
	
/**
 * Ctor
 * @param myView
 * @param myModel
 */
	public Presenter(View myView, Model myModel) {
		this.view = myView;
		this.model = myModel;	
		commandsManager = new CommandManager(model, view);
		commands = commandsManager.getCommandsMap();
		myModel.loadMap();
	}

	/**
	 * update
	 */
	@Override
	public void update(Observable obs, Object arg) {
        
		String commandLine = (String)arg;
		String arr[] = commandLine.split(" ");
		String command = arr[0];			
		
		if(!commands.containsKey(command)) {
			view.print("Command doesn't exist");			
		}
		else {
			String[] args = null;
			if (arr.length > 1) {
				String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);//+1
				args = commandArgs.split(" ");							
			}
			Command cmd = commands.get(command);
			cmd.doCommand(args);	
		}
	}	
}
