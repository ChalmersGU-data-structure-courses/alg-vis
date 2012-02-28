package algvis.trie;

import algvis.core.Algorithm;
import algvis.core.NodeColor;

public class TrieFind extends Algorithm {
	Trie T;
	String s;

	public TrieFind(Trie T, String s) {
		super(T);
		this.T = T;
		this.s = s;
		setHeader("triefind", s.substring(0, s.length() - 1));
	}

	public void beforeReturn() {
		T.hw = null;
		T.clearExtraColor();
		addStep("done");
	}

	@Override
	public void run() {
		if (s.compareTo("$") == 0) {
			addNote("badword");
			return;
		}

		TrieNode v = T.getRoot();
		addNote("triefindnote");
		addStep("trierootstart");
		v.mark();
		mysuspend();
		v.unmark();
		T.hw = new TrieHelpWord(s);
		T.hw.setC(NodeColor.CACHED);
		T.hw.goNextTo(v);

		while (s.compareTo("$") != 0) {
			TrieNode wd = (TrieNode) v.getChild();
			while (wd != null) {
				wd.setColor(NodeColor.FIND);
				wd = (TrieNode) wd.getRight();
			}
			wd = (TrieNode) v.getChild();

			String ch = s.substring(0, 1);
			T.hw.setAndGoNT(s, v);
			TrieNode w = v.getChildWithCH(ch);
			if (w == null) {
				while (wd != null) {
					wd.setColor(NodeColor.NORMAL);
					wd = (TrieNode) wd.getRight();
				}
				addStep("triefindending1", ch);
				mysuspend();
				beforeReturn();
				return;
			}
			addStep("triefindmovedown", ch);
			mysuspend();
			while (wd != null) {
				wd.setColor(NodeColor.NORMAL);
				wd = (TrieNode) wd.getRight();
			}
			v = w;
			v.setColor(NodeColor.CACHED);
			s = s.substring(1);
		}
		T.hw.setAndGoNT(s, v);
		TrieNode w = (TrieNode) v.getChildWithCH("$");
		if (w == null) {
			addStep("triefindending2");
		} else {
			addStep("triefindsucc");
		}
		mysuspend();

		beforeReturn();
	}
}
