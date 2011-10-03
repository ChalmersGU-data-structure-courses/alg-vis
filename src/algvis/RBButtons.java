package algvis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class RBButtons extends DictButtons implements ActionListener {
	private static final long serialVersionUID = 5601437441473816995L;
	ICheckBox B24;

	public RBButtons(VisPanel M) {
		super(M);
	}

	@Override
	public void otherButtons(JPanel P) {
		B24 = new ICheckBox(M.a, "mode234", false);
		B24.setMnemonic(KeyEvent.VK_2);
		B24.addActionListener(this);
		P.add(B24);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		super.actionPerformed(evt);
		if (evt.getSource() == B24) {
			((RB) M.D).mode24 = B24.isSelected();
			((RB) M.D).reposition();
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		B24.refresh();
	}
}
