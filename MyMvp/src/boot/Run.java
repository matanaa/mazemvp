package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import properties.Properties;
import view.MazeWindow;
import view.MyView;
import view.WindowsBuilder;

public class Run {

	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		//MyView view = new MyView(in, out);
		MazeWindow view = new MazeWindow();
		MyModel model = new MyModel();
		
		MyPresenter presenter = new MyPresenter(view, model);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		
		
		view.start();
	}

}
