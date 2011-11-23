package algvis.core;

import algvis.bst.BST;
import algvis.scenario.NewAlgorithmCommand;
import algvis.scenario.PauseCommand;

/**
 * The Class Algorithm.
 * Each visualized data structure consists of data and algorithms (such as insert, delete)
 * that update the data. All such algorithms are descendants of the class Algorithm.
 * 
 * A visualized algorithm has its own thread which can be suspended (e.g., after each step
 * of the algorithm; see method mysuspend) and is automatically resumed (method myresume)
 * after pressing the "Next" button.   
 */
abstract public class Algorithm extends Thread {
	private DataStructure D;
	boolean suspended = false;

	/**
	 * rather use Algorithm(DataStructure D, String name)
	 */
	public Algorithm(DataStructure D) {
		this.D = D;
	}
	
	public Algorithm(DataStructure D, String name) {
		this(D);
		D.scenario.add(new NewAlgorithmCommand(name));
	}

	/**
	 * Mysuspend.
	 */
	public void mysuspend() {
		if (D.scenario != null) {
			D.scenario.add(new PauseCommand());
		}
		if (D.M.pause) {
			suspended = true;
			synchronized (this) {
				try {
					while (suspended) {
						wait();
					}
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * Myresume.
	 */
	public void myresume() {
		// if (suspended)
		synchronized (this) {
			suspended = false;
			notifyAll();
		}
	}

	protected void finish() {
		if (D instanceof BST) {
			((BST) D).unsetNodeV();
		}
		D.scenario.add(new PauseCommand());
	}
	
	public void setHeader(String s) {
		if (D.M.pause) {
			D.M.C.setHeader(s);
		}
	}

	public void setText(String s) {
		if (D.M.pause) {
			D.M.C.setText(s);
		}
	}

	public void setText(String s, String... par) {
		if (D.M.pause) {
			D.M.C.setText(s, par);
		}
	}

	public void setText(String s, int... par) {
		if (D.M.pause) {
			D.M.C.setText(s, par);
		}
	}

	public void run() {
	}
}
