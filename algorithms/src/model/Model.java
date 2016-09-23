package model;

import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;


public interface Model {
	
	/** generate
	 * @param name
	 * @param para*/
	void generateMaze(String name,int floor, int rows, int cols);
	
	/**save
	 * @param name
	 * @param fileName */
	void saveMaze(String name, String fileName);

	/**load
	 * @param file name
	 * @param algorithms*/
	void loadMaze(String fileName, String name);
	
	/**exit
	 * @throws IOException*/
	void exit()throws IOException;
	
	/**solve
	 * @param name
	 * @param algorithms */
    void solve(String name , String algorithms);

	/** get maze
     * @param name
     * @return Maze3d */
	Maze3d getMaze(String name);
	
	/**
	 * cross section by
	 * @param name
	 * @param place
	 * @param section
	 * @return
	 */
	int[][] CrossSection(String name, String place, int section);

	/**
	 * display solution
	 * @param name
	 * @return	
	 */
	String display_Solution(String name);

	/**
	 * load solution and name
	 */
	void loadMap();
	/**
	 * solve solution and name
	 */
	void saveMap();

	/**
	 * save maze
	 */
	void saveMazes();

	/**
	 * load maze
	 */
	void loadMazes();
/**
 * get notification
 * @return
 */
	String getMessage();
	
	/**
	 * get the name of the maze
	 * @return
	 */
	public String getMazesNames();

	/**
	 * reset
	 */
	void DeleteAndS();

	/**
	 * set properties
	 * @param args
	 */
	void SetProperties(String[] args);

	/**
	 * reset
	 */
	void eraseAll();

}
