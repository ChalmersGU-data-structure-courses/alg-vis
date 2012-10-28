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
package algvis.ds.suffixtree;

import java.util.Vector;

import algvis.core.Algorithm;
import algvis.core.NodeColor;
import algvis.core.TreeNode;
import algvis.ds.trie.TrieWordNode;

public class SuffixTreeFind extends Algorithm {
	private final SuffixTree T;
	private String s;
	private TrieWordNode hw;

	public SuffixTreeFind(SuffixTree T, String s) {
		super(T.panel);
		this.T = T;
		this.s = s.substring(0, s.length() - 1);
	}

	void beforeReturn() {
		removeFromScene(hw);
		T.clearExtraColor();
		addStep("done");
	}

	@Override
	public void runAlgorithm() throws InterruptedException {
		setHeader("triefind", s);
		if (s.length() == 0) {
			// addNote("badword");
		}

		SuffixTreeNode v = T.getRoot();
		// addNote("triefindnote");
		addStep("trierootstart");
		v.mark();
		pause();
		v.unmark();
		hw = new TrieWordNode(T, s);
		addToScene(hw);
		hw.setColor(NodeColor.CACHED);
		hw.goNextTo(v);

		while (s.length() > 0) {
			SuffixTreeNode wd = v.getChild();
			while (wd != null) {
				wd.setColor(NodeColor.FIND);
				wd = wd.getRight();
			}
			wd = v.getChild();

			char ch = s.charAt(0);
			hw.setAndGoNextTo(s, v);
			SuffixTreeNode w = v.getChildWithCH(ch);
			if (w == null) {
				while (wd != null) {
					wd.setColor(NodeColor.NORMAL);
					wd = wd.getRight();
				}
				addStep("triefindending1", "" + ch);
				pause();
				beforeReturn();
				return;
			}
			addStep("triefindmovedown", "" + ch);
			pause();
			v = w;
			while (wd != null) {
				wd.setColor(NodeColor.NORMAL);
				wd = wd.getRight();
			}
			v.setColor(NodeColor.CACHED);
			s = s.substring(1);
		}
		hw.setAndGoNextTo(s, v);
		Vector<TreeNode> leaves = v.getLeaves();
		Vector<Integer> pos = new Vector<Integer>();
		for (TreeNode w : leaves) {
			int p = w.getKey();
			w.mark();
			T.str.mark(p);
			pos.add(p);
			System.out.print(p + ", ");
		}
		System.out.println();

		pause();
		for (TreeNode w : leaves) {
			w.unmark();
			T.str.unmark(w.getKey());
		}
		beforeReturn();
	}
}