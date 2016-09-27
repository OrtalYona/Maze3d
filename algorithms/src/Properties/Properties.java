package Properties;

import java.io.Serializable;

/**
 * class properties
 * 
 * @author
 *
 */
public class Properties implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	private int mazeGenerator;
	private int solveAlgorithm;
	private int numOfThreads;


	public enum MazeGenerator {
		SimpleMaze, GrowingTree
	}

	public enum MazeSolve {
		BFS, DFS
	}
	
	
	public Properties() {
		mazeGenerator = 1;
		solveAlgorithm = 1;
		numOfThreads=50;
       
	}
	
	public Properties(int maze, int solveAlgorithm,int num){
		
		this.mazeGenerator=maze;
		this.solveAlgorithm=solveAlgorithm;
		this.numOfThreads=num;	
	}

	/**
	 * setters and getters
	 * 
	 * @return
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	public int getMazeGenerator() {
		return mazeGenerator;
	}

	public void setMazeGenerator(int mazeGenerator) {
		this.mazeGenerator = mazeGenerator;
	}

	public int getSolveAlgorithm() {
		return solveAlgorithm;
	}

	public void setSolveAlgorithm(int solveAlgorithm) {
		this.solveAlgorithm = solveAlgorithm;
	}

	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

}

// XMLEncoder xmlEncoder;
/*
 * try { xmlEncoder=new XMLEncoder(new BufferedOutputStream(new
 * FileOutputStream("test.xml"))); xmlEncoder.flush();
 * xmlEncoder.writeObject(xmlEncoder); //
 * 
 * } catch (FileNotFoundException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * finally{ xmlEncoder.close(); }
 */