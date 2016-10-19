/**
 * 
 */
package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * @author matan
 *
 */
public class FileMenu extends Observable {

	/**
	 * 
	 */
	Menu myMenu;
	Shell shell;
	MazeWindow mazeWin;
	public FileMenu(Shell shell,int style,MazeWindow mazeWin ) {
		// TODO Auto-generated constructor stub
		
		this.shell = shell;

		Label label = new Label(shell, SWT.CENTER);
		label.setBounds(shell.getClientArea());

		myMenu = new Menu(shell, SWT.BAR);
		this.mazeWin =mazeWin;
	}
	
	public void createMenuBar() {
		addMenuItems();
		shell.setMenuBar(myMenu);
	}
	
	
	private void addMenuItems() {
		Menu mainMnu = new Menu(shell, SWT.DROP_DOWN);
		
		//file part
		MenuItem fileMNU = new MenuItem(myMenu, SWT.CASCADE);
		fileMNU.setText("&File");
		fileMNU.setMenu(mainMnu);

		MenuItem PropItem = new MenuItem(mainMnu, SWT.PUSH);
		PropItem.setText("Open Properties");
		PropItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog propLoadFileDialog = new FileDialog(shell, SWT.OPEN);
				propLoadFileDialog.setText("Load XML File");
				propLoadFileDialog.setFilterPath("lib/");
				propLoadFileDialog.setFilterNames(new String[] { "XML Files" });
				propLoadFileDialog.setFilterExtensions(new String[] { "*.xml" });

				propLoadFileDialog.open();
				if (propLoadFileDialog.getFileName() != ""){
				setChanged();
				notifyObservers("change_xml " + propLoadFileDialog.getFilterPath() + "\\" + propLoadFileDialog.getFileName());
				
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		MenuItem exitItem = new MenuItem(mainMnu, SWT.PUSH); //item for exit the program
		exitItem.setText("Exit");
		exitItem.addSelectionListener(new SelectionListener() { 

			public void widgetSelected(SelectionEvent arg0) {

					setChanged();
					notifyObservers("exit");
			
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		/// game part
		Menu gameMnu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem gameMnuItm = new MenuItem(myMenu, SWT.CASCADE);
		gameMnuItm.setText("&Game");
		
		
		gameMnuItm.setMenu(gameMnu);
		
		MenuItem genItm = new MenuItem(gameMnu, SWT.PUSH);
		genItm.setText("Generate Maze");
		genItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.showGenerateMazeOptions();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		MenuItem loadItm = new MenuItem(gameMnu, SWT.PUSH);
		loadItm.setText("Load Maze");
		loadItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.showLoadMazeOption();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		MenuItem saveItm = new MenuItem(gameMnu, SWT.PUSH);
		saveItm.setText("save Maze to file");
		saveItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.showSaveMazeToFileOption();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		MenuItem loadFileItm = new MenuItem(gameMnu, SWT.PUSH);
		loadFileItm.setText("Load Maze from file");
		loadFileItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.showLoadMazeFromFileOption();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		//cheats part 
		Menu cheatsMnu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem cheatsMnuItm = new MenuItem(myMenu, SWT.CASCADE);
		cheatsMnuItm.setText("&Cheats");
		cheatsMnuItm.setMenu(cheatsMnu);
		
		MenuItem hintItm = new MenuItem(cheatsMnu, SWT.PUSH);
		hintItm.setText("Hint");
		hintItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.getHint();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		MenuItem solvetItm = new MenuItem(cheatsMnu, SWT.PUSH);
		solvetItm.setText("Solve");
		solvetItm.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
	
				mazeWin.getSolve();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	
	
	//end cheat menu
	
	MenuItem About = new MenuItem(myMenu, SWT.PUSH);
	About.setText("About");
	About.addSelectionListener(new SelectionListener() {

		public void widgetSelected(SelectionEvent arg0) {
			int style = SWT.APPLICATION_MODAL ;
			MessageBox messageBox = new MessageBox(shell, style);
			messageBox.setText("About");
			messageBox.setMessage("Build by :\n-Matan Akrabi\n-Snir Balgaly");
			messageBox.open() ;
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub

		}

	});
	
	}

}
