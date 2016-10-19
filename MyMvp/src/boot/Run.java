package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import properties.Properties;
import properties.PropertiesLoader;
import view.MazeWindow;
import view.MyView;
 

public class Run {

	public static void main(String[] args) {
		Properties properties = PropertiesLoader.getInstance().getProperties();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		MyModel model = new MyModel();

		String chooseView = properties.getView();
		if (chooseView.equals("GUI")) {
			MazeWindow view = new MazeWindow();
			MyPresenter presenter = new MyPresenter(view, model);
			model.addObserver(presenter);
			view.addObserver(presenter);
			view.start();

		} else if (chooseView.equals("CLI")) {
			MyView view = new MyView(in, out);
			MyPresenter presenter = new MyPresenter(view, model);
			model.addObserver(presenter);
			view.addObserver(presenter);
			view.start();
		} else {
			return;
		}

	}

}
