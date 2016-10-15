package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Properties.Properties;
import Properties.PropertiesLoader;
import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;

public class Run {

	public static void main(String[] args) throws Exception {
		
		Properties p=null;
		
		try {
			p = PropertiesLoader.getInstance();
		} catch (FileNotFoundException e2) {
			System.out.println("Could't find");
			p = new Properties();
			try {
				PropertiesLoader.write(p, "newPro.xml");
			} catch (Exception e) {
				System.out.println("Could't save");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyModel m = new MyModel(p);
		MazeWindow win = new MazeWindow(in, out, p);
		Presenter pre = new Presenter(win, m);
		win.addObserver(pre);
		m.addObserver(pre);
		win.start();

		// MazeWindow win = new MazeWindow(in,out);
		// win.start();

		/*
		 * BufferedReader in = new BufferedReader(new
		 * InputStreamReader(System.in)); PrintWriter out = new
		 * PrintWriter(System.out);
		 * 
		 * MyView view = new MyView(out, in); MyModel model = new MyModel();
		 * 
		 * Presenter presenter = new Presenter(view, model);
		 * model.addObserver(presenter); view.addObserver(presenter);
		 * 
		 * view.start();
		 */

	}
}
