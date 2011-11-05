package algvis.treenode;

import algvis.core.Algorithm;

public class TreeAppend extends Algorithm {
	TreeDS T;
	int X, Y;
	
	public TreeAppend(TreeDS T, int i, int j) {
		super(T.M);
		this.T = T;
		X = i; // kam
		Y = j; // co
	}

	@Override
	public void run() {
		T.root.append(X, Y);
		mysuspend();
		T.reposition();
	}
	
}
