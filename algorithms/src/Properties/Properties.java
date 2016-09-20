package Properties;


import java.io.Serializable;


public class Properties implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	

	public enum MazeGenerator{
		SimpleMaze,GrowingTree
	}
	
	public enum MazeSolve{
		BFS,DFS
	}
	
	//public MazeGenerator generator;
	//public MazeSolve solve;
	
	public Properties() {
	
		//this.generator=MazeGenerator.GrowingTree;
	//	this.solve=MazeSolve.BFS;

	}

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

	/*public MazeGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(MazeGenerator generator) {
		this.generator = generator;
	}

	public MazeSolve getSolve() {
		return solve;
	}

	public void setSolve(MazeSolve solve) {
		this.solve = solve;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	*/
}

//XMLEncoder xmlEncoder;
/*		try {
			xmlEncoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("test.xml")));
			xmlEncoder.flush(); 
			xmlEncoder.writeObject(xmlEncoder); //???

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		finally{ 
		xmlEncoder.close(); 
		} */