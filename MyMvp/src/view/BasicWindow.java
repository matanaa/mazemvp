/*
 * 
 */
package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicWindow.
 */
public abstract class BasicWindow extends Observable implements Runnable {

	/** The display. */
	protected Display display;

	/** The shell. */
	protected Shell shell;

	/**
	 * Inits the widgets.
	 */
	protected abstract void initWidgets();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		display = new Display(); // our display
		shell = new Shell(display); // our window

		// set nice icon
		shell.setImage(new Image(null, "lib/images/icon.ico"));

		initWidgets();

		shell.open();

		/*
		 * zoom in and out using the mouse wheel - For the bonus
		 * 
		 */
		zoomEvent();

		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed

			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) { // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}

		} // shell is disposed

		display.dispose(); // dispose OS components
	}

	/**
	 * Exit event.
	 *
	 * @param event
	 *            the event
	 */
	protected void exitEvent(Event event) {
		int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
		// asking the suer if he really wants to exit
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setText("Information");
		messageBox.setMessage("Close the Game?");
		if (messageBox.open() == SWT.YES) {
			setChanged();
			notifyObservers("exit");
		} else if (event != null) {
			event.doit = false;
		}

	}

	/**
	 * Zoom event - using the mouse wheel and zoom in and out
	 */
	protected void zoomEvent() {
		/*
		 * For the bonus
		 * 
		 */
		shell.addMouseWheelListener(new MouseWheelListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.MouseWheelListener#mouseScrolled(org.
			 * eclipse.swt.events.MouseEvent)
			 */
			@Override
			public void mouseScrolled(MouseEvent e) {
				int wheelCount = e.count;
				// using the mouse wheel counter to zoom in and out the display
				// in a certain limit
				if ((e.stateMask & SWT.CONTROL) == SWT.CONTROL) {
					if ((wheelCount < 0 && shell.getSize().x > 200 && shell.getSize().y > 200)
							|| (wheelCount > 0 && shell.getSize().x < 2000 && shell.getSize().y < 2000)) {
						shell.setSize(shell.getSize().x + wheelCount, shell.getSize().y + wheelCount);
					}
				}
			}
		});

	}
}
