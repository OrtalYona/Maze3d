package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;


/** class CLI */
public class CLI extends Observable {
	
	/**out */
    private PrintWriter out;
	
    /** in*/
    private BufferedReader in;
	
    /** commands */
    private HashMap<String,Command> commands;
	
    /**
     * C'tor
     * @param out
     * @param in
     */
	public CLI(PrintWriter out, BufferedReader in) {

		this.out = out;
		this.in = in;
		
	}
	/**
	 * get out
	 * @return out
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * set out
	 * @param out
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * get in
	 * @return in
	 */
	public BufferedReader getIn() {
		return in;
	}
	
	/**
	 * set in
	 * @param in
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	/**
	 * get commands
	 * @return commands
	 */
	public HashMap<String, Command> getCommands() {
		return commands;
	}
	/**
	 * set commands
	 * @param commands
	 */
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
	}
	
/*	public void printCommands(){
		out.print("Choose command:");
		out.flush();
		
		out.write("Show files in directory                      dir <name>\n");
		out.write("Generate maze                                generate_3d_maze <name> <x> <y> <z>\n");
		out.write("Display maze                                 display_maze <name>\n");
		out.write("Display maze by                              display_cross_section <name> <x/y/z> <index>\n");
		out.write("Save the maze                                save_maze <name> <file name>\n");
		out.write("Load the maze                                load_maze <file name> <name>\n");	
		out.write("Solve the maze                               solve <name> <algorithm>\n");
		out.write("Display the solution                         display_solution <name>\n");
		out.write("Exit                                         exit\n");
		out.write("\n");
	}
*/
	
	/** start */
	public void start() throws Exception {
		
		Thread thread = new Thread(new Runnable() {
		
			String line= null;
		//	Command command = null;
	
		@Override
		public void run() {
			//printCommands();
			do{
			 out.write("Enter command:\n");
			 
				out.flush();
				try {
					line = in.readLine();
					setChanged();
					notifyObservers(line);
					} 
				catch (IOException e) {
					e.printStackTrace();
					}
			
				/*
			String[] args = line.split(" ");

			command = commands.get(args[0]);
			
			if (command != null){
				
				command.doCommand(args);	
			}
	
			else{
				
				out.write("Invalid parameters!\n");
				
				out.flush(); 
			}*/
			out.write("\n");
					
			} while (!line.equalsIgnoreCase("exit"));
		}
	});
	thread.run();
	thread.join();
	//thread.start();	
  }
	
}
	
