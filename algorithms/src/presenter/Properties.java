package presenter;


import java.io.Serializable;


public class Properties implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	public enum MazeGenerator{
		SimpleMaze,GrowingTree
	}
	
	public enum MazeSolve{
		BFS,DFS
	}
	
	public MazeGenerator generator;
	public MazeSolve solve;
	
	public Properties() {
	
		this.generator=MazeGenerator.GrowingTree;
		this.solve=MazeSolve.BFS;

	}
	
	public Properties(MazeGenerator maze,MazeSolve solve){
		this.generator=maze;
		this.solve=solve;
	}

	public MazeGenerator getGenerator() {
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