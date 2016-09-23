package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Properties.Properties;
import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;

public class Run {

	public static void main(String[] args) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		Properties p = new Properties();
		MyModel m = new MyModel();
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
// }
