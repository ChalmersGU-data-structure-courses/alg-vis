package algvis.unionfind;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import algvis.core.Buttons;
import algvis.core.VisPanel;
import algvis.internationalization.IButton;

public class UnionFindButtons extends Buttons {
	private static final long serialVersionUID = 2683381160819263717L;
	IButton makesetB, unionB;

	public UnionFindButtons(VisPanel M) {
		super(M);
	}

	@Override
	public void actionButtons(JPanel P) {
		makesetB = new IButton(M.S.L, "button-makeset");
		makesetB.setMnemonic(KeyEvent.VK_M);
		makesetB.addActionListener(this);

		unionB = new IButton(M.S.L, "button-union");
		unionB.setMnemonic(KeyEvent.VK_U);
		unionB.addActionListener(this);

		P.add(makesetB);
		P.add(unionB);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		super.actionPerformed(evt);
		if (evt.getSource() == makesetB) {
			final Vector<Integer> args = I.getVI();
			if (args.size() == 0) {
				args.add(1);
			}
			// is this thread necessary?
			Thread t = new Thread(new Runnable() {
				public void run() {
					for (int x : args) {
						for (int j = 0; j < x; j++) {
							((UnionFind) D).makeSet();
						}
					}
				}
			});
			t.start();
		} else if (evt.getSource() == unionB) {
			int count = ((UnionFind) D).count;
			final Vector<Integer> args = I.getVI(1, count+1);
			// if (args.size() != 2) { return; }
			Random G = new Random(System.currentTimeMillis());
			switch (args.size()) {
			case 0:
				args.add(G.nextInt(count)+1);
			case 1:
				int i;
				int ii = args.elementAt(0);
				do {
					i = G.nextInt(count)+1;
//					System.out.println(i);
				} while (i == ii);
				args.add(i);
			}
			//System.out.println(args.get(0)+" "+ args.get(1));
			// is this thread necessary?
			Thread t = new Thread(new Runnable() {
				public void run() {
					((UnionFind) D).union(args.elementAt(0)-1, args.elementAt(1)-1);
				}
			});
			t.start();
		}

	}
}
