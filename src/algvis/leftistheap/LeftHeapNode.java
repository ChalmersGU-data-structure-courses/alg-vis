package algvis.leftistheap;

import java.awt.Color;

import algvis.binomialheap.BinHeapNode;
import algvis.bst.BSTNode;
import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.MeldablePQ;
import algvis.core.View;

public class LeftHeapNode extends BSTNode {
	//LeftHeapNode left, right, parent;
	Color color = Color.yellow;
	int height = 1;
	int rank = 1;
	
	public LeftHeapNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);		
		bgKeyColor();		
	}
	
	public LeftHeapNode(DataStructure D, int key) {
		super(D, key);
		bgKeyColor();
		setState(Node.UP);
	}
	
	public LeftHeapNode(LeftHeapNode v) {
		this(v.D, v.key, v.x, v.y);
	}
	
	//TOTO MOZE NEFUNGOVAT!
	/**
	 * v.prec(w) iff v precedes w in the heap order, i.e., should be higher in the heap
	 * v precedes w if v.key < w.key when we have a min heap, but
	 * v precedes w if v.key > w.key when we have a max heap
	 */
	public boolean prec(Node v) {
		if (((MeldablePQ) D).minHeap) {
			return this.key < v.key;
		} else {
			return this.key > v.key;
		}
	}

	/**
	 * Precedes or equals (see prec).
	 */
	public boolean preceq(Node v) {
		if (((MeldablePQ) D).minHeap) {
			return this.key <= v.key;
		} else {
			return this.key >= v.key;
		}
	}
	
	//pripojit this ako noveho rodica v a v dat ako praveho syna 
	public void linkup(LeftHeapNode v) {
		if((this.parent != null) && (v != null) ){
			BSTNode tmp = this.parent;
			//this.parent = v;
			v.right = this;
			this.parent = v;		
			v.parent = tmp;
			v.parent.right = v;
		}
	}	
	
	// vyhodi sa zo zoznamu
	public void unlink() {
		/* if (parent != null) {
			if (parent.child == this) {
				if (right == this)
					parent.child = null;
				else
					parent.child = right;
			}
			parent.rank--;
			parent = null;
		}
		left.right = right;
		right.left = left;
		left = right = this;
		*/
	}
	
	
	//docasne a mozno aj navzdy skopirovane z AAtree koli vykreslovaniu ranku
	@Override
	public void draw(View v) {
	
	if (state == Node.INVISIBLE || state == Node.UP || key == NULL) {
		return;
	}
	drawBg(v);
	drawKey(v);
	drawArrow(v);
	drawArc(v);
	String str = new String("" + rank);
	v.drawString(str, x + D.radius, y - D.radius, 7);
	}
	
	
	public void repos(int rrightw, LeftHeapNode w) {
		//this.goTo(w.x + rrightw, w.y);
		this.tox += rrightw;				
		
		if (this.right != null) {
			((LeftHeapNode)this.right).repos(rrightw, (LeftHeapNode)this.right);
		}
		if (this.left != null) {
			((LeftHeapNode)this.left).repos(rrightw, (LeftHeapNode)this.left);
		}
	} 
		
}
