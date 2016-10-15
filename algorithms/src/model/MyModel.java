package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
	private Map<String, Solution<Position>> solutions = new HashMap<String, Solution<Position>>();
	public ArrayList<String> mazesNames = new ArrayList<>();
	private HashMap<Maze3d, Solution<Position>> mazeSolution = new HashMap<Maze3d, Solution<Position>>();
	private final String file = "newFile.zip";
	private Properties properties;
	String str;
	String[] constantArgs;

	public MyModel(Properties p) {
		this.properties = p;
		executor = Executors.newFixedThreadPool(50);
	}

	@Override
	public void generateMaze(String name, int floor, int rows, int cols) {

		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {

				Maze3d maze = (Maze3d) queryServer("127.0.0.1", 8090, "generate maze",
						name + "," + floor + "," + rows + "," + cols, "GrowingTree");

				mazes.put(name, maze);
				mazesNames.add(name);
				saveMazes();
				saveSolutions();
				sendMazesNames(name);
				saveCurrentMaze(name);
				setChanged();
				notifyObservers("maze_ready " + name);
				saveMap();
				return maze;

			}
		});

	}

	@Override
	public Maze3d getMaze(String name) {
		saveCurrentMaze(name);
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
		saveMazes();
		setChanged();
		executor.shutdownNow();
	}

	@Override
	public void solve(String name, String algorithms, String pos) {

		Maze3d maze = mazes.get(name);

		@SuppressWarnings("unchecked")
		Solution<Position> sol = (Solution<Position>) queryServer("127.0.0.1", 8090, "solve maze", name, algorithms);

		if (sol == null) {
			return;
		}

		System.out.println(sol);

		mazes.put(name, maze);
		solutions.put(name, sol);
		saveCurrentSolution(name);

		boolean changed = false;
		if (pos != "same") {
			Maze3d temp2 = mazes.get(name);
			String[] p = pos.split("_");
			temp2.setStartPosition(
					new Position(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2])));
			maze = temp2;
			changed = true;

			if (maze != null) {

				Maze3dSearchableAdapter adapter = new Maze3dSearchableAdapter(maze);

				if (!mazeSolution.containsKey(maze)) {

					if (algorithms.toLowerCase().equals("bfs")) {

						// executor.submit(new Callable<Solution<Position>>() {

						// @Override
						// public Solution<Position> call() throws Exception {

						BFS<Position> bfs = new BFS<Position>();
						// Solution<Position> solution1 = bfs.search(adapter);
						Solution<Position> solution = bfs.search(adapter);
						mazes.put(name, maze);
						solutions.put(name, solution);// solution1
						// mazeSolution.put(maze, solution);
						// return solution;
					}

					// });
				}

				else if (algorithms.toLowerCase().equals("dfs")) {

					// executor.submit(new Callable<Solution<Position>>() {

					// @Override
					// public Solution<Position> call() throws Exception {
					DFS<Position> dfs = new DFS<Position>();
					// Solution<Position> solution2 = dfs.search(adapter);
					Solution<Position> solution = dfs.search(adapter);
					mazes.put(name, maze);
					solutions.put(name, solution);
					// mazeSolution.put(maze, solution);
					// return solution;
					// }

					// });
					// }
				}
				saveCurrentSolution(name);
				setChanged();
				notifyObservers("solve_ready " + name);

			}
		}
	}

	public int[][] CrossSection(String name, String place, int section) {

		Maze3d maze3d = mazes.get(name);
		StringBuilder sb = new StringBuilder();
		int maze2d[][] = null;

		switch (place) {
		case "x":
			maze2d = maze3d.getCrossSectionByX(section);
			for (int i = 0; i < maze2d.length; i++) {
				for (int j = 0; j < maze2d[0].length; j++) {
					sb.append((((Integer) maze2d[i][j]).toString() + " "));
				}
				sb.append("\n");
			}
			return maze2d;

		case "y":
			maze2d = maze3d.getCrossSectionByY(section);
			for (int i = 0; i < maze2d.length; i++) {
				for (int j = 0; j < maze2d[0].length; j++) {
					sb.append((((Integer) maze2d[i][j]).toString() + " "));
				}
				sb.append("\n");
			}
			return maze2d;

		case "z":
			maze2d = maze3d.getCrossSectionByZ(section);
			for (int i = 0; i < maze2d.length; i++) {
				for (int j = 0; j < maze2d[0].length; j++) {
					sb.append((((Integer) maze2d[i][j]).toString() + " "));
				}
				sb.append("\n");
			}
			return maze2d;

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

			FileOutputStream fileOut = new FileOutputStream(file);
			GZIPOutputStream zip = new GZIPOutputStream(fileOut);
			out = new ObjectOutputStream(zip);
			out.writeObject(this.mazes);
			out.writeObject(this.solutions);
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
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveMazes() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("AllMazesCatch")));// fileNames
			out.writeObject(this.mazes);
			out.close();
		} catch (IOException e1) {
			str = "save faild";
		}
		str = "";
	}

	@SuppressWarnings("unchecked")

	@Override
	public void loadMazes() {
		ObjectInputStream in = null;
		try {

			in = new ObjectInputStream(new GZIPInputStream(new FileInputStream("AllMazesCatch")));// fileNames
			this.mazes = (HashMap<String, Maze3d>) in.readObject();
			System.out.println(this.mazes);
			in.close();
		} catch (IOException e1) {
			str = "load faild";
		} catch (ClassNotFoundException e) {
			str = "load faild- class not found";
			e.printStackTrace();
		}
		str = "Mazes loaded!\n";
	}

	@Override
	public String getMessage() {
		return str;
	}

	void sendMazesNames(String name) {
		setChanged();
		notifyObservers("getInformation" + " " + name);
	}

	@Override
	public String getMazesNames() {
		String temp = "";
		for (String s : mazesNames) {
			temp += s + " ";
		}

		return temp;
	}

	@Override
	public void DeleteAndS() {
		this.mazes.clear();
		this.solutions.clear();
		setChanged();
		notifyObservers("message Reset!");
	}

	@Override
	public void SetProperties(String[] args) {

		if (args[0] == "GrowingTree") {
			properties.setMazeGenerator(1);
		}

		else if (args[0] == "Simple") {
			properties.setMazeGenerator(0);
		}

		if (args[1] == "bfs") {

			properties.setSolveAlgorithm(1);
		} else if (args[1] == "dfs") {
			properties.setSolveAlgorithm(0);
		}
	}

	@Override
	public void eraseAll() {

		this.mazes.clear();
		this.solutions.clear();
	}

	public void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("AllSolutionsCatch")));
			oos.writeObject(this.solutions);
			oos.close();
		} catch (IOException e1) {
			str = "save faild";
		}
	}

	@SuppressWarnings("unchecked")
	public void loadSolutions() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("AllSolutionsCatch")));
			this.solutions = (Map<String, Solution<Position>>) ois.readObject();
			ois.close();
		} catch (IOException e1) {
			str = "load faild";
		} catch (ClassNotFoundException e) {
			str = "load faild";
			e.printStackTrace();
		}

	}

	public void saveCurrentMaze(String name) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("cuurentMaze")));// cuurentMaze
			oos.writeObject(name);
			oos.writeObject(this.mazes.get(name));
			oos.close();
		} catch (IOException e1) {

		}
	}

	public void saveCurrentSolution(String name) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("cuurentSolution")));
			oos.writeObject(this.solutions.get(name));
			oos.close();
		} catch (IOException e1) {
		}
	}

	private Object queryServer(String serverIP, int serverPort, String command, String data, String property) {
		Object result = null;
		Socket server = null;
		
		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket("127.0.0.1", 8090);// (serverIP,serverPort);
			PrintWriter writerToServer = new PrintWriter((new OutputStreamWriter(server.getOutputStream())));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed = null;
			inputDecompressed = new ObjectInputStream(server.getInputStream());
			result = inputDecompressed.readObject();
			writerToServer.close();
			inputDecompressed.close();
			server.close();
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();

		}

		return result;

	}

}