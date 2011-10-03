package algvis.avltree;

import java.awt.Color;
import java.awt.Graphics;

import algvis.bst.BSTNode;
import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.View;

//import static java.lang.Math.random;
//import static java.lang.Math.round;

public class AVLNode extends BSTNode {
	int bal = 0;

	public AVLNode(DataStructure D, int key, int x, int y) {
		super(D, key, x, y);
	}

	public AVLNode(DataStructure D, int key) {
		this(D, key, -10, -10);
	}

	public AVLNode(BSTNode v) {
		this(v.D, v.key, v.x, v.y);
	}

	public int balance() {
		int l = (left == null) ? 0 : left.height, r = (right == null) ? 0
				: right.height;
		return bal = r - l;
	}

	@Override
	public void calc() {
		super.calc();
		balance();
	}

	@Override
	public void draw(Graphics G, View V) {
		if (state == Node.INVISIBLE || state == Node.UP || key == NULL) {
			return;
		}
		drawBg(G, V);
		drawArrow(G, V);
		drawArc(G, V);

		int xx = x - D.radius, yy = y - D.radius, dx = 2 * D.radius, dy = 2 * D.radius;
		String b = "";
		if (bgcolor == Node.NORMAL) {
			G.setColor(Color.ORANGE);
			switch (bal) {
			case +2:
				b = "++";
				V.fillArc(G, xx, yy, dx, dy, 270, 180);
				break;
			case +1:
				b = "+";
				V.fillArc(G, xx, yy, dx, dy, 210, 180);
				break;
			case 0:
				b = "\u00b7";
				V.fillArc(G, xx, yy, dx, dy, 180, 180);
				break;
			case -1:
				b = "\u2013";
				V.fillArc(G, xx, yy, dx, dy, 150, 180);
				break;
			case -2:
				b = "\u2013\u2013";
				V.fillArc(G, xx, yy, dx, dy, 90, 180);
				break;
			}
			G.setColor(fgcolor);
			V.drawOval(G, x - D.radius, y - D.radius, 2 * D.radius,
					2 * D.radius);
		}

		drawKey(G, V);
		if (parent != null && parent.left == this) {
			V.drawString(G, b, x - D.radius - 1, y - D.radius - 1, 10);
		} else {
			V.drawString(G, b, x + D.radius + 1, y - D.radius - 1, 10);
		}
	}
}
