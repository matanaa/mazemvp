package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay;
	
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);				
		
		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		
		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");	
		

		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		mazeDisplay = new MazeDisplay(this.shell, SWT.NONE);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
		
		
	}

	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		Text txtName = new Text(shell, SWT.BORDER);
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		Text txtCols = new Text(shell, SWT.BORDER);
		
		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		btnGenerate.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate_maze "+txtName.getText()+" "+txtFloors.getText()+" " + txtRows.getText() + " " + txtCols.getText());
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		shell.open();		
	}

	@Override
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Maze " + name + " is ready");
				msg.open();	
				
				setChanged();
				notifyObservers("display_maze " + name);

			}
		});			
	}

	@Override
	public void displayMaze(Maze3d maze) {
		
//		int[][] mazeData={
//				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//				{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
//				{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
//				{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
//				{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
//				{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
//				{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
//				{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
//				{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
//				{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
//			};
		int[][] mazeData=maze.getCrossSectionByX(maze.getStartPos().z);
		mazeDisplay.setCharacterPos(maze.getStartPos());
		//System.out.println(maze.getStartPos());
		//System.out.println(maze);
		mazeDisplay.setMazeData(mazeData);
	}


	@Override
	public void start() {
		run();		
	}



	@Override
	public void printAnswers(String[] msg) {
		
		MessageBox msgBox = new MessageBox(shell,SWT.ICON_INFORMATION);
		if (msg[0].isEmpty() == false){
		msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false){
		msgBox.setMessage(msg[1]);
		}
		msgBox.open();	
		
	}

	@Override
	public void printCross(int[][] cross) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifySolutionIsReady(String name) {
		MessageBox msgBox = new MessageBox(shell,SWT.ICON_INFORMATION);
		msgBox.setText("Solution Is Ready");
		msgBox.setMessage("The solution for " + name +" is ready!");
		msgBox.open();	
		
	}

	@Override
	public void displayMazeSolution(Solution<Position> solution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printErrorMessage(String[] msg) {
		MessageBox msgBox = new MessageBox(shell,SWT.ICON_ERROR);
		if (msg[0].isEmpty() == false){
		msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false){
		msgBox.setMessage(msg[1]);
		}
		msgBox.open();	
		
	}

}