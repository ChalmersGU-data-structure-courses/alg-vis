package algvis.btree;

import algvis.core.Algorithm;
import algvis.core.Node;

public class BFind extends Algorithm {
	BTree T;
	BNode v;

	public BFind(BTree T, int x) {
		super(T.M);
		this.T = T;
		v = T.v = new BNode(T, x);
		v.bgColor(Node.FIND);
		setHeader("search");
	}

	@Override
	public void run() {
		if (T.root == null) {
			v.goToRoot();
			setText("empty");
			mysuspend();
			v.goDown();
			v.bgColor(Node.NOTFOUND);
			setText("notfound");
		} else {
			BNode w = T.root;
			v.goTo(w);
			setText("bstfindstart");
			mysuspend();

			while (true) {
				if (w.isIn(v.key[0])) {
					setText("found");
					v.goDown();
					v.bgColor(Node.FOUND);
					break;
				}
				if (w.isLeaf()) {
					setText("notfound");
					v.bgColor(Node.NOTFOUND);
					v.goDown();
					break;
				}
				w = w.way(v.key[0]);
				v.goTo(w);
				mysuspend();
			}
		}
	}
}
