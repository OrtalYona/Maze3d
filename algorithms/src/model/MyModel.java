package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import Properties.Properties;
import Properties.PropertiesLoader;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel extends Observable implements Model {

	private ExecutorService executor;

	MyCompressorOutputStream myCompressor;
	MyDecompressorInputStream myDecompressor;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();

	// without Position
	private Map<String, Solution<Position>> solutions = new HashMap<String, Solution<Position>>();
	// private HashMap<String,Maze3d> maze = new HashMap<String , Maze3d>();

	private HashMap<Maze3d, Solution<Position>> mazeSolution = new HashMap<Maze3d, Solution<Position>>();

	private final String file = "newFileM.zip";
	private Properties properties;

	// private ObjectInputStream in;

	public MyModel() {
	//	properties = PropertiesLoader.getInstance().getProperties();
		//executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		executor = Executors.newFixedThreadPool(50);
	}

	@Override
	public void generateMaze(String name, int floor, int rows, int cols) {

		//executor.submit(new Callable<Maze3d>() {

			//@Override
		//	public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				Maze3d maze = generator.generate(floor, rows, cols);
				mazes.put(name, maze);

				setChanged();
				notifyObservers("maze_ready " + name);
			//	return maze;

			//}
		//});

	}

	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	@Override
	public void saveMaze(String name, String fileName) {
		try {

			myCompressor = new MyCompressorOutputStream(new FileOutputStream(fileName));
			myCompressor.write(mazes.get(name).toByteArray());
			myCompressor.flush();
			myCompressor.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error not found exeption");

		} catch (IOException e) {
			System.out.println("Error IO exeption");

		} finally {
			try {
				myCompressor.close();
			} catch (IOException e) {
				System.out.println("Error closing file");
			}
		}
	}

	@Override
	public void loadMaze(String fileName, String name) {

		byte[] byteArr = new byte[3];
		InputStream in = null;

		try {
			in = new FileInputStream(fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			in.read(byteArr);
			int size = (byteArr[0] * byteArr[1] * byteArr[2]) + 9;
			byteArr = new byte[size];
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Maze3d myMaze = new Maze3d(byteArr);
		mazes.put(name, myMaze);

	}

	@Override
	public void exit() throws IOException {

		saveMap();
		executor.shutdownNow();
	}

	@Override
	public void solve(String name, String algorithms) {

		Maze3d maze = mazes.get(name);
		Maze3dSearchableAdapter adapter = new Maze3dSearchableAdapter(maze);

		if (!mazeSolution.containsKey(maze)) {// Value?

			if (algorithms.toLowerCase().equals("bfs")) {

				executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {

						BFS<Position> bfs = new BFS<Position>();
						// Solution<Position> solution1 = bfs.search(adapter);
						Solution<Position> solution = bfs.search(adapter);
						mazes.put(name, maze);
						solutions.put(name, solution);// solution1
						// mazeSolution.put(maze, solution);
						return solution;
					}

				});
			}

			else if (algorithms.toLowerCase().equals("dfs")) {

				executor.submit(new Callable<Solution<Position>>() {

					@Override
					public Solution<Position> call() throws Exception {
						DFS<Position> dfs = new DFS<Position>();
						// Solution<Position> solution2 = dfs.search(adapter);
						Solution<Position> solution = dfs.search(adapter);
						mazes.put(name, maze);
						solutions.put(name, solution);// 2
						// mazeSolution.put(maze, solution);
						return solution;
					}

				});
			}
		}
		setChanged();
		notifyObservers("solve_ready " + name);

	}

	public int[][] CrossSection(String name, String place, int section) {

		Maze3d maze3d = mazes.get(name);

		switch (place) {
		case "x":
			return maze3d.getCrossSectionByX(section);

		case "y":
			return maze3d.getCrossSectionByY(section);

		case "z":
			return maze3d.getCrossSectionByZ(section);

		default:
			return null;
		}

	}

	public String display_Solution(String name) {

		if (mazes.get(name) != null) {

			return solutions.get(name).getStates().toString();

		} else {
			return "Error";
		}
	}

	public void saveMap() {
		ObjectOutputStream out;
		try {

			FileOutputStream fileOut=new FileOutputStream(file);
			 GZIPOutputStream zip=new GZIPOutputStream(fileOut);
			 out = new ObjectOutputStream(zip);
			 System.out.println(this.mazes);
			 System.out.println(this.solutions);
			 out.writeObject(this.mazes);//mazeSolution
			 out.writeObject(this.solutions);
			
			/*out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

			out.writeObject(this.mazes);
			out.writeObject(this.solutions);*/

			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void loadMap() {

		try {

			FileInputStream fileIn = new FileInputStream(file);
			GZIPInputStream zip = new GZIPInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(zip);

			this.mazes = (Map<String, Maze3d>) in.readObject();
			this.solutions = (Map<String, Solution<Position>>) in.readObject();
			System.out.println(this.mazes);
			System.out.println(this.solutions);
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
