package view;


import algorithms.mazeGenerators.Maze3d;


public interface View {
	

	/**
	 * notify maze ready
	 * @param name
	 */
	void notifyMazeIsReady(String name);
	
	/**
	 * notify solution ready
	 * @param name
	 */
	void notifySolutionIsReady(String name);
	
	/**
	 * display maze
	 * @param maze
	 */
	void displayMaze(Maze3d maze);
	
	/**
	 * start
	 * @throws Exception
	 */
	void start()throws Exception ;
	
	/**print
	 * @param sb*/
	void print(String s);
	
	/**
	 * dir
	 * @param fileName
	 */
	void dir(String fileName);
	
	/**
	 * set controller
	 * @param controller
	 */
	//public void setUserCommand(Command command);
	void update(String[] args);

}
