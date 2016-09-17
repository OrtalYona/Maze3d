package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


import model.Model;
import view.View;

public class Presenter implements Observer{
	
	private Model model;
	private View view;
	private CommandManager commandsManager;
	private HashMap<String, Command> commands;
	

	public Presenter(View myView, Model myModel) {
		this.view = myView;
		this.model = myModel;
		
		commandsManager = new CommandManager(model, view);
		commands = commandsManager.getCommandsMap();
		myModel.loadMap();
	}

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

/*	public HashMap<String,Command> getCommands() {
	
	commands.put("dir", new Dir());
	commands.put("generate_3d_maze", new GenerateMazeCommand());
	commands.put("display_maze", new DisplayMazeCommand());
	commands.put("display_cross_section", new DisplayCross());
	commands.put("save_maze", new Save());
	commands.put("load_maze", new Load());
	commands.put("solve", new Solve());
	commands.put("display_solution", new DisplaySolution());
	commands.put("exit", new Exit());
	
	return commands;
	
	}
	
/**
 * Generate Maze
 /
public class GenerateMazeCommand implements Command {

	@Override
	public void doCommand(String[] args) {
		if(args.length==5){
		String name = args[1];
		int floor=Integer.parseInt(args[2]);
		int rows = Integer.parseInt(args[3]);
		int cols = Integer.parseInt(args[4]);
		model.generateMaze(name, floor, rows, cols);
	}
		else{
			view.print("Invalid input\n");
		}
   }
}

/**
 * Display Maze
 /
public class DisplayMazeCommand implements Command {

	@Override
	public void doCommand(String[] args) {
		if(args.length==2){
		String name = args[1];
		Maze3d maze = model.getMaze(name);
		view.displayMaze(maze);
	}
		else{
			view.print("Invalid input");
		}
	}
  }

/**
 * Dir
 /
public class Dir implements Command{
	
	@Override
	public void doCommand(String[] args){
		
	  try{
		if(args[1] != null){
		  String fileName = args[1];
		  view.dir(fileName);
		 }
	    } catch (ArrayIndexOutOfBoundsException e){
		view.print("Error no arguments");
		}
	}
}

/**
 * Exit
 /
public class Exit implements Command{

	@Override
	public void doCommand(String[] args) {
		
		try {
			model.exit();
		} catch (IOException e) {
			view.print("Can't close");
		}
	}
}

/**
 * Load
 /
public class Load implements Command{
	
	@Override
	public void doCommand(String[] args) {
     
		try{
		model.loadMaze(args[2], args[1]);
		}
		catch (ArrayIndexOutOfBoundsException e){
		view.print("Invalid input");
	}
  }
}
/**
 Save
  /
public class Save implements Command{

	@Override
	public void doCommand(String[] args) {

		try{
			model.saveMaze(args[1], args[2]);}
		catch (ArrayIndexOutOfBoundsException e){
			view.print("Invalid input");
		}
	}
}

/**
 * Solve
 /
public class Solve implements Command{

	@Override
	public void doCommand(String[] args) {

	if(args.length == 3){
        
		String name = args[1];
		
        String algorithm = args[2];
		
        try{
			model.solve(name, algorithm);

		}catch (ArrayIndexOutOfBoundsException e){
			model.solve(name, algorithm);

		}
		
		}
		else
			view.print("invalid paramters");
	}

}
	
/**
 * Display Cross
 /
public class DisplayCross implements Command {
	
	@Override
	public void doCommand(String[] args) {
	
		if(args.length==4){
	  
	   model.CrossSection(args[1],args[2],Integer.parseInt(args[3]));
	   view.print("Maze " + args[1] + " By " + args[2]);
		}
		else{
			view.print("Invalid input");
		}
 }
}

/**
 * Display Solution
 /
public class DisplaySolution implements Command {
	
	@Override
	public void doCommand(String[] args) {
		if(args.length==2){
		String solution=model.display_Solution(args[1]);
		view.print(solution);
     }
		else{
			view.print("invalid input");
		}
   }
 }
	
	
*/	
}
