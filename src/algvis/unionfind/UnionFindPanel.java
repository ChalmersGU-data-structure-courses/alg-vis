package algvis.unionfind;

import algvis.core.DataStructure;
import algvis.core.Settings;
import algvis.core.VisPanel;
import algvis.internationalization.Languages;

public class UnionFindPanel extends VisPanel {
	private static final long serialVersionUID = -532478811422102888L;
	public static Class<? extends DataStructure> DS = UnionFind.class;

	public UnionFindPanel(Settings S) {
		super(S);
	}

	@Override
	public void initDS() {
		D = new UnionFind(this);
		B = new UnionFindButtons(this);
	}
}
