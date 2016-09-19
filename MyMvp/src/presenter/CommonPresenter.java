package presenter;

import java.util.HashMap;
import java.util.Observer;

import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonController. Abstract class for all controllers
 */
public abstract class CommonPresenter implements Presenter, Observer{

	/** The model. */
	protected Model model;

	/** The view. */
	protected View view;

	/** The commands manager. */
	protected CommandsManager commandsManager;
	
	protected HashMap<String, Command> commands;

	/**
	 * Instantiates a new common controller.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public CommonPresenter(View view, Model model) {
		this.model = model;
		this.view = view;
			
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#notifyMazeIsReady(java.lang.String)
	 */
	@Override
	public abstract void notifyMazeIsReady(String name);

}
