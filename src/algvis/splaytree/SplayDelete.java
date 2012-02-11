package algvis.splaytree;

import algvis.core.NodeColor;
import algvis.core.Node;

public class SplayDelete extends SplayAlg {
	public SplayDelete(SplayTree T, int x) {
		super(T, x);
		setHeader("deletion");
	}

	@Override
	public void run() {
		if (T.getRoot() == null) {
			s.goToRoot();
			addStep("empty");
			mysuspend();
			s.goDown();
			s.setColor(NodeColor.NOTFOUND);
			addStep("notfound");
			return;
		}

		SplayNode w = find(K);
		splay(w);

		w.setColor(NodeColor.NORMAL);

		if (w.key != s.key) {
			addStep("notfound");
			s.setColor(NodeColor.NOTFOUND);
			s.goDown();
			return;
		}

		T.setV(w);
		T.getV().goDown();
		T.getV().setColor(NodeColor.DELETE);
		if (w.getLeft() == null) {
			addStep("splaydeleteright");
			T.setRoot(w.getRight());
			T.getRoot().setParent(null);
			T.reposition();
			mysuspend();
		} else if (w.getRight() == null) {
			addStep("splaydeleteleft");
			T.setRoot(w.getLeft());
			T.getRoot().setParent(null);
			T.reposition();
			mysuspend();
		} else {
			addStep("splaydelete");
			T.setRoot2(w.getLeft());
			T.getRoot2().setParent(null);
			T.setRoot(w.getRight());
			T.getRoot().setParent(null);
			T.setVV(s = new SplayNode(T, -Node.INF));
			s.setColor(NodeColor.FIND);
			w = w.getRight();
			s.goTo(w);
			mysuspend();
			while (w.getLeft() != null) {
				w = w.getLeft();
				s.goTo(w);
				mysuspend();
			}
			w.setColor(NodeColor.FIND);
			T.setVV(null);
			// splay
			while (!w.isRoot()) {
				if (w.getParent().isRoot()) {
					T.rotate2(w);
					// setText ("splayroot");
				} else {
					if (w.isLeft() == w.getParent().isLeft()) {
						/*
						 * if (w.isLeft()) setText ("splayzigzigleft"); else
						 * setText ("splayzigzigright");
						 */
						T.rotate2(w.getParent());
						mysuspend();
						T.rotate2(w);
					} else {
						/*
						 * if (!w.isLeft()) setText ("splayzigzagleft"); else
						 * setText ("splayzigzagright");
						 */
						T.rotate2(w);
						mysuspend();
						T.rotate2(w);
					}
				}
				mysuspend();
			}
			addStep("splaydeletelink");
			T.setRoot(w);
			w.setColor(NodeColor.NORMAL);
			w.linkLeft(T.getRoot2());
			T.setRoot2(null);
			T.reposition();
			mysuspend();
		}

		addStep("done");
		T.setVV(null);
	}
}
