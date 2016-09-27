package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
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

		initWidgets();

		shell.open();

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
		}
		else if (event!=null) {
			 
			event.doit =false;
		}
		
	}
}
