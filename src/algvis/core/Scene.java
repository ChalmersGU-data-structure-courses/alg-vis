package algvis.core;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Scene implements VisualElement {
	public static final int MAXZ = 10, MIDZ = 5;
	ArrayList<ArrayList<VisualElement>> E;
	Rectangle2D.Float B;

	public Scene() {
		E = new ArrayList<ArrayList<VisualElement>>();
		for (int i = 0; i < MAXZ; ++i) {
			E.add(new ArrayList<VisualElement>());
		}
	}

	public int add(VisualElement e, int z) {
		if (z < 0)
			z = 0;
		if (z >= MAXZ)
			z = MAXZ - 1;
		E.get(z).add(e);
		return E.get(z).size() - 1;
	}

	public void draw(View V) {
		for (ArrayList<VisualElement> l : E) {
			for (VisualElement e : l) {
				e.draw(V);
			}
		}
	}

	public void move() {
		VisualElement e;
		for (ArrayList<VisualElement> l : E) {
			for (int i = l.size() - 1; i >= 0; --i) {
				e = l.get(i);
				if (e.toRemove()) {
					l.set(i, l.get(l.size() - 1));
					l.remove(l.size() - 1);
				} else {
					e.move();
				}
			}
		}
	}

	public Rectangle2D getBoundingBox() {
		return B;
	}

	public boolean toRemove() {
		return false;
	}
}
