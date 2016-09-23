package presenter;

import java.io.IOException;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class CommandManager {
	
		private Model model;
		private View view;
		private HashMap<String, Command> commands=new HashMap<String, Command>();
		String[] arg;
		public CommandManager(Model myModel,View myView) {
			this.view = myView;
			this.model = myModel;
		}
		
	public HashMap<String, Command> getCommandsMap() {
		
		commands.put("dir", new Dir());
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display_maze", new DisplayMazeCommand());
		commands.put("display_cross_section", new DisplayCross());
		commands.put("save_maze", new Save());
		commands.put("load_maze", new Load());
		commands.put("solve", new Solve());
		commands.put("display_solution", new DisplaySolution());
		commands.put("exit", new Exit());
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("solve_ready", new SolveReadyCommand());
		commands.put("message", new DisplayMessage());
		commands.put("load_ready", new LoadReadyCommand());
		commands.put("save_ready", new SaveReadyCommand());
		commands.put("getInformation", new getInformation());
		commands.put("getMaze", new getMaze());
		commands.put("getMazesNames",  new getMazesNames());
		commands.put("loadMazes", new loadMazes());
		commands.put("saveMazes", new saveMazes());
		commands.put("deleteAndSave", new DeleteAndSave());
		commands.put("setProperties", new setProperties());
		commands.put("eraseAll", new eraseAll());
	





		
		
		return commands;
		
		}
		
	/**
	 * Generate Maze
	 */
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			//if(args.length==5){
			String name = args[0];
			int floor=Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			model.generateMaze(name, floor, rows, cols);
		}//
			//else{
				//view.print("Invalid input\n");
			//}

		@Override
		public void setArguments(String[] args) {
			arg=args;
		}
	   }

	//}

	/**
	 * Display Maze
	 */
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if(args.length==1){
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
			else{
				view.print("Invalid input");
			}
		}

		@Override
		public void setArguments(String[] args) {
         arg=args;			
		}
	  }

	/**
	 * Dir
	 */
	public class Dir implements Command{
		
		@Override
		public void doCommand(String[] args){
			
		  try{
			if(args.length == 1){//args[1] != null
			  String fileName = args[0];
			  view.dir(fileName);
			 }
		    } catch (ArrayIndexOutOfBoundsException e){
			view.print("Error no arguments");
			}
		}

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}
	}

	/**
	 * Exit
	 */
	public class Exit implements Command{

		@Override
		public void doCommand(String[] args) {
			
			try {
				model.exit();
			} catch (IOException e) {
				view.print("Can't close");
			}
		}

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}
	}

	/**
	 * Load
	 */
	public class Load implements Command{
		
		@Override
		public void doCommand(String[] args) {
	     
			try{
			model.loadMaze(args[1], args[0]);
			}
			catch (ArrayIndexOutOfBoundsException e){
			view.print("Invalid input");
		}
	  }

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}
	}
	/**
	 Save
	  */
	public class Save implements Command{

		@Override
		public void doCommand(String[] args) {

			try{
				model.saveMaze(args[0], args[1]);}
			catch (ArrayIndexOutOfBoundsException e){
				view.print("Invalid input");
			}
		}

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}
	}

	/**
	 * Solve
	 */
	public class Solve implements Command{

		@Override
		public void doCommand(String[] args) {

		if(args.length == 2){//2?
	        
			String name = args[0];//0
			
	        String algorithm = args[1];
			
	        try{
				model.solve(name, algorithm);

			}catch (ArrayIndexOutOfBoundsException e){
				model.solve(name, algorithm);

			}
			
			}
			else
				view.print("invalid paramters");
		}

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}

	}
		
	/**
	 * Display Cross
	 */
	public class DisplayCross implements Command {
		
		@Override
		public void doCommand(String[] args) {
		
			if(args.length==3){
		  
		   model.CrossSection(args[0],args[1],Integer.parseInt(args[2]));
		   view.print("Maze " + args[0] + " By " + args[1]);
		   
			}
			else{
				view.print("Invalid input");
			}
	 }

		@Override
		public void setArguments(String[] args) {
	         arg=args;			
		}
	}

	/**
	 * Display Solution
	 */
	public class DisplaySolution implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==1){//2
			String solution=model.display_Solution(args[0]);
			view.print(solution);
	     }
			else{
				view.print("invalid input");
			}
	   }

		@Override
		public void setArguments(String[] args) {
	         arg=args;					
		}
	 }
	class MazeReadyCommand implements Command {//add solution ready

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String msg = "maze " + name + " is ready";
		view.print(msg);
		}

	@Override
	public void setArguments(String[] args) {
		// TODO Auto-generated method stub
		
	}
		
	}
	
	class SolveReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String msg = "solve " + name + " is ready";
		view.print(msg);
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class DisplayMessage implements Command{

		@Override
		public void doCommand(String[] args) {
		
			view.print(model.getMessage());
			
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class LoadReadyCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String msg = "maze is load";
		    view.print(msg);			
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class SaveReadyCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String msg = "maze is save";
		    view.print(msg);			
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class getInformation implements Command{

		@Override
		public void doCommand(String[] args) {

			view.getInformation(args[0]);
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class getMaze implements Command{

		@Override
		public void doCommand(String[] args) {

			view.getMaze(args[1]);
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class getMazesNames implements Command{

		@Override
		public void doCommand(String[] args) {
			
			model.getMazesNames();
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	public class loadMazes implements Command{

		@Override
		public void doCommand(String[] args) {

			model.loadMazes();
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	public class saveMazes implements Command{

		@Override
		public void doCommand(String[] args) {

			model.saveMazes();
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public class DeleteAndSave implements Command{

		@Override
		public void doCommand(String[] args) {

			model.DeleteAndS();
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	public class setProperties implements Command{

		@Override
		public void doCommand(String[] args) {
			model.SetProperties(args);			
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class eraseAll implements Command{

		@Override
		public void doCommand(String[] args) {
			model.eraseAll();			
		}

		@Override
		public void setArguments(String[] args) {
			// TODO Auto-generated method stub
			
		}
			}
	
	
}

