package algvis.btree;

import algvis.core.DataStructure;
import algvis.core.DictButtons;
import algvis.core.VisPanel;
import algvis.internationalization.Languages;

public class a23Panel  extends VisPanel {
	private static final long serialVersionUID = 3849830550882705599L;
	public static Class<? extends DataStructure> DS = a23Tree.class;

	public a23Panel(Languages L) {
		super(L);
	}

	@Override
	public void initDS() {
		D = new a23Tree(this);
		B = new DictButtons(this);
	}
}
