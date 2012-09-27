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
package algvis.ds.rotations;

import algvis.core.history.HashtableStoreSupport;
import algvis.core.visual.ZDepth;
import algvis.ds.DataStructure;
import algvis.ds.dictionaries.bst.BST;
import algvis.ds.dictionaries.bst.BSTNode;
import algvis.gui.InputField;
import algvis.gui.VisPanel;
import algvis.gui.view.Alignment;
import algvis.gui.view.ClickListener;
import algvis.gui.view.Layout;
import algvis.gui.view.View;

import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Random;

public class Rotations extends DataStructure implements ClickListener {
	public static String adtName = "dictionary";
	public static String dsName = "rotations";
	public final BST T;
	BSTNode v;
	public boolean subtrees = false;

	@Override
	public String getName() {
		return "rotations";
	}

	/*
	 * Algorithm.D != Buttons.D (T != this)
	 */
	public Rotations(VisPanel M) {
		super(M);
		T = new BST(M);
		M.screen.V.setDS(this);
		M.screen.V.align = Alignment.LEFT;
	}

	public void rotate(int x) {
		BSTNode v = T.getRoot();
		while (v != null && v.getKey() != x) {
			if (v.getKey() < x) {
				v = v.getRight();
			} else {
				v = v.getLeft();
			}
		}
		if (v == null) {
			// vypis ze taky vrchol neexistuje
			return;
		} else {
			start(new Rotate(this, v));
		}
	}

	@Override
	public void insert(int x) {
		BSTNode v = new BSTNode(T, x, ZDepth.NODE);
		BSTNode w = T.getRoot();
		if (w == null) {
			T.setRoot(v);
		} else {
			while (true) {
				if (w.getKey() == x) {
					break;
				} else if (w.getKey() < x) {
					if (w.getRight() == null) {
						w.linkRight(v);
						break;
					} else {
						w = w.getRight();
					}
				} else {
					if (w.getLeft() == null) {
						w.linkLeft(v);
						break;
					} else {
						w = w.getLeft();
					}
				}
			}
		}
		reposition();
	}

	@Override
	public void clear() {
		T.setRoot(null);
	}

	@Override
	public void draw(View V) {
		if (v != null && v.getParent() != null) {
			V.drawWideLine(v.x, v.y, v.getParent().x, v.getParent().y);
		}
		if (T.getRoot() != null) {
			T.getRoot().drawTree(V);
		}
	}

	@Override
	protected void move() {
		if (T.getRoot() != null) {
			T.getRoot().moveTree();
		}
	}

	@Override
	protected Rectangle2D getBoundingBox() {
		return null; // TODO
	}

	@Override
	protected void endAnimation() {
		// TODO
	}

	@Override
	protected boolean isAnimationDone() {
		return true; // TODO
	}

	public void reposition() {
		T.reposition();
		T.getRoot().repos(T.getRoot().leftw, 0);
		panel.screen.V.setBounds(T.x1, T.y1, T.x2, T.y2);
	}
	
	@Override
	public String stats() {
		return T.stats();
	}

	@Override
	public void random(int n) {
		Random g = new Random(System.currentTimeMillis());
		final boolean p = panel.pauses;
		for (int i = 0; i < n; ++i) {
			insert(g.nextInt(InputField.MAX + 1));
		}
		start(new Runnable() {
			@Override
			public void run() {
				T.getRoot().calcTree();
				setStats();
				panel.pauses = p;
			}
		});
		//panel.screen.V.resetView();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (T.getRoot() == null)
			return;
		BSTNode v = T.getRoot().find(x, y);
		if (v != null) {
			if (v.marked) {
				v.unmark();
				chosen = null;
				panel.buttons.I.setText("");
			} else {
				if (chosen != null)
					chosen.unmark();
				v.mark();
				chosen = v;
				panel.buttons.I.setText("" + chosen.getKey());
			}
		}
	}
	
	@Override
	public Layout getLayout() {
		return Layout.SIMPLE;
	}

	@Override
	public void storeState(Hashtable<Object, Object> state) {
		super.storeState(state);
		T.storeState(state);
		HashtableStoreSupport.store(state, hash + "v", v);
		if (v != null) v.storeState(state);
	}

	@Override
	public void restoreState(Hashtable<?, ?> state) {
		super.restoreState(state);
		T.restoreState(state);
		Object v = state.get(hash + "v");
		if (v != null) this.v = (BSTNode) HashtableStoreSupport.restore(v);
		
		if (this.v != null) this.v.restoreState(state); 
	}
}
