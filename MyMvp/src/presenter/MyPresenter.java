package presenter;

import java.io.IOException;
import java.util.Observable;

import model.Model;
import view.View;


public class MyPresenter extends CommonPresenter {


	public MyPresenter(View view, Model model) {
		super(view, model);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.CommonController#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#notifySolutionIsReady(java.lang.String)
	 */
	@Override
	public void notifySolutionIsReady(String name) {
		view.notifySolutionIsReady(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#printErrorMessage(java.lang.String[])
	 */
	@Override
	public void printErrorMessage(String[] msg) {
		view.printErrorMessage(msg);
	}

	@Override
	public void update(Observable o, Object arg) {
	String commandLine = (String)arg;
		
		String arr[] = commandLine.split(" ");
		String command = arr[0];			
		
		if(!commands.containsKey(command)) {
			//view.displayMessage("Command doesn't exist");	
			view.printErrorMessage(new String[]{"Command doesn't exist","Command doesn't exist"});
			
		}
		else {
			String[] args = null;
			if (arr.length > 1) {
				String commandArgs = commandLine.substring(
						commandLine.indexOf(" ") + 1);
				args = commandArgs.split(" ");							
			}
			Command cmd = commands.get(command);
			try {
				cmd.doCommand(args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}

}
