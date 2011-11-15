package algvis.scenario;

import org.jdom.Element;

import algvis.bst.BSTNode;

public class LinkLeftCommand implements Command {
	private BSTNode n1, n2;

	public LinkLeftCommand(BSTNode n1, BSTNode n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	@Override
	public void execute() {
		n1.linkLeft(n2);
	}

	@Override
	public void unexecute() {
		n1.unlinkLeft(n2);
	}

	@Override
	public Element getXML() {
		Element e = new Element("node");
		e.setAttribute("action", "linkLeft");
		e.setAttribute("parentKey", Integer.toString(n1.key));
		e.setAttribute("childKey", Integer.toString(n2.key));
		return e;
	}

}
