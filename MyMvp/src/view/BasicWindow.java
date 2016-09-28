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

public abstract class BasicWindow extends Observable implements Runnable {

	protected Display display;
	protected Shell shell;

	protected abstract void initWidgets();

	@Override
	public void run() {
		display = new Display(); // our display
		shell = new Shell(display); // our window

		// set nice icon
		shell.setImage(new Image(null, "lib/images/icon.ico"));
		initWidgets();

		shell.open();
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

				if ((e.stateMask & SWT.CONTROL) == SWT.CONTROL) {
					if ((wheelCount < 0 && shell.getSize().x > 200 && shell.getSize().y > 200)
							|| (wheelCount > 0 && shell.getSize().x < 2000 && shell.getSize().y < 2000)) {
						shell.setSize(shell.getSize().x + wheelCount, shell.getSize().y + wheelCount);
					}
				}
			}
		});

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

	protected void exitEvent(Event event) {
		int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
		MessageBox messageBox = new MessageBox(shell, style);
		messageBox.setText("Information");
		messageBox.setMessage("Close the Game?");
		if (messageBox.open() == SWT.YES) {
			// event.doit =false;
			setChanged();
			notifyObservers("exit");
		} else if (event != null) {

			event.doit = false;
		}

	}
}
