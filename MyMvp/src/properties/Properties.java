package properties;

import java.io.Serializable;

public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;

	public Properties() {

	}

	public int getNumOfThreads() {
		return numOfThreads;
	}

	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}

	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Properties [numOfThreads=" + numOfThreads + ", generateMazeAlgorithm=" + generateMazeAlgorithm
				+ ", solveMazeAlgorithm=" + solveMazeAlgorithm + "]";
	}

	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}

	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}

}
