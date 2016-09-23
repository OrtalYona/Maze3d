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
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;

	public enum MazeGenerator {
		SimpleMaze, GrowingTree
	}

	public enum MazeSolve {
		BFS, DFS
	}

	/**
	 * setters and getters
	 * 
	 * @return
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}

	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}

	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}

	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
}

// XMLEncoder xmlEncoder;
/*
 * try { xmlEncoder=new XMLEncoder(new BufferedOutputStream(new
 * FileOutputStream("test.xml"))); xmlEncoder.flush();
 * xmlEncoder.writeObject(xmlEncoder); //???
 * 
 * } catch (FileNotFoundException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); }
 * 
 * finally{ xmlEncoder.close(); }
 */