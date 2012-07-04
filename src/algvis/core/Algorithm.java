/*******************************************************************************
 * Copyright (c) 2012 Jakub Kováč, Katarína Kotrlová, Pavol Lukča, Viktor Tomkovič, Tatiana Tóthová
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package algvis.core;

/**
 * The Class Algorithm. Each visualized data structure consists of data and
 * algorithms (such as insert, delete) that update the data. All such algorithms
 * are descendants of the class Algorithm.
 * 
 * A visualized algorithm has its own thread which can be suspended (e.g., after
 * each step of the algorithm; see method mysuspend) and is automatically
 * resumed (method myresume) after pressing the "Next" button.
 */
abstract public class Algorithm extends Thread {
	private DataStructure D;
	private boolean suspended = false;

	protected Algorithm(DataStructure D) {
		this.D = D;
		D.M.scenario.newAlgorithm();
		D.M.scenario.newStep();
	}

	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * Mysuspend.
	 */
    protected void mysuspend() {
		if (D.M.pause && !D.M.scenario.isEnabled()) {
			D.M.C.update();
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
		D.M.scenario.newStep();
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

	protected void setHeader(String s) {
		D.M.C.setHeader(s);
	}

	protected void setHeader(String s, String... par) {
		D.M.C.setHeader(s, par);
	}

	protected void setHeader(String s, int... par) {
		D.M.C.setHeader(s, par);
	}

	protected void addNote(String s) {
		D.M.C.addNote(s);
	}

	public void addNote(String s, String... par) {
		D.M.C.addNote(s, par);
	}

	protected void addNote(String s, int... par) {
		D.M.C.addNote(s, par);
	}

	protected void addStep(String s) {
		D.M.C.addStep(s);
	}

	protected void addStep(String s, String... par) {
		D.M.C.addStep(s, par);
	}

	protected void addStep(String s, int... par) {
		D.M.C.addStep(s, par);
	}

	@Override
	public void run() {
	}
}
