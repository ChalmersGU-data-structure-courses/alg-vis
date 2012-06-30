package algvis.linkcuttree;

import algvis.core.Algorithm;
import algvis.core.NodePair;

public class LinkCutAlg extends Algorithm {
	LinkCutDS D;
	
	public LinkCutAlg(LinkCutDS D) {
		super(D);
		this.D = D;
	}
	
	public NodePair<LinkCutDSNode> split(LinkCutDSNode v) {
		if (v == null) {return null;}
		NodePair<LinkCutDSNode> p = new NodePair<LinkCutDSNode>();
		p.left = null;
		p.right = null;
		if (v.getParent() != null) {
			p.left = v.getParent();		//vrchol nad
			if (v.getParent().preffered == v) {
				v.getParent().preffered = null;
			}
		} 
		if (v.preffered != null) {
			p.right = v.preffered;		//vrchol pod, ak je nejaky preferovany
			v.preffered = null;			
		}
		D.reposition();
		mysuspend();
		return p;
	}
	
	public void splice(LinkCutDSNode p) {	//p je vrch path(p)
		LinkCutDSNode v = p.getParent();
		if (v == null) {return;}
		if (v.preffered == p) {return;}
		NodePair<LinkCutDSNode> np = split(v);
		if (np.left != null) {
			np.left.preffered = p;
		}
		v.preffered = p;
		D.reposition();
		mysuspend();
	}
	
	public void expose(LinkCutDSNode v) {
		NodePair<LinkCutDSNode> np = split(v);
		if (np.left != null) {
			np.left.preffered = v;
		}
		while (v != null) {
			splice(v);
			v = v.getParent();
		}
	}
	
	public void link(LinkCutDSNode v, LinkCutDSNode w) {
		expose(w);
		mysuspend();
		w.addChild(v);
		w.preffered = v;
		v.setParent(w);
		D.reposition();
	}

	public void cut(LinkCutDSNode v) {
		if (v.isRoot()) {return;}
		expose(v);
		NodePair<LinkCutDSNode> np = split(v);
		if (np.left.getChild() == v) {
			np.left.setChild(v.getRight());
		} else {
			LinkCutDSNode w = (LinkCutDSNode) np.left.getChild();
			while (w.getRight() != v) {
				w = (LinkCutDSNode) w.getRight();
			}
			w.setRight(v.getRight());
		}
		v.setParent(null);
	}
}
