/*******************************************************************************
 * Copyright (c) 2012-present Jakub Kováč, Jozef Brandýs, Katarína Kotrlová,
 * Pavol Lukča, Ladislav Pápay, Viktor Tomkovič, Tatiana Tóthová
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
package algvis.ds.dictionaries.redblackbst;

import algvis.core.Algorithm;
import algvis.core.visual.ZDepth;
import algvis.ds.dictionaries.bst.BSTInsert;
import algvis.ui.view.REL;

public class RBBSTInsert extends Algorithm {
    private final RBBST T;
    private final int K;

    public RBBSTInsert(RBBST T, int x) {
        super(T.panel);
        this.T = T;
        K = x;
    }

    @Override
    public void runAlgorithm() {
        setHeader("insert", K);
        RBBSTNode w = (RBBSTNode) new BSTInsert(T, K)
            .insert(new RBBSTNode(T, K, ZDepth.ACTIONNODE)).orElse(null);

        // TODO komentar "ideme bublat" (nieco ako pri BSTDelete:
        // "first we have to find a node")
        // bubleme nahor
        while (w != null) {
            RBBSTNode left = w.getLeft();
            RBBSTNode right = w.getRight();
            if (T.slides) {
                if (right != null && right.isRed()) {
                    // skew
                    addStep(w, REL.TOP, "rbbstskew");
                    w.mark();
                    pause();
                    boolean wRed = w.isRed();
                    boolean rRed = right.isRed();
                    w.setRed(rRed);
                    right.setRed(wRed);
                    T.rotate(right);
                    pause();
                    w.unmark();
                    w = right;
                } else if (left != null && left.isRed() && left.getLeft() != null && left.getLeft().isRed()) {
                    // split
                    addStep(w, REL.TOP, "rbbstsplit");
                    w.mark();
                    pause();
                    T.rotate(left);
                    left.setRed(true);
                    left.getLeft().setRed(false);
                    left.getRight().setRed(false);
                    pause();
                    w.unmark();
                    w = left;
                } else w = w.getParent();
            } else {
                if (right != null && right.isRed() && (left == null || !left.isRed())) {
                    // left rotation
                    addStep(w, REL.TOP, "rbbstrotateleft");
                    w.mark();
                    pause();
                    boolean wRed = w.isRed();
                    boolean rRed = right.isRed();
                    w.setRed(rRed);
                    right.setRed(wRed);
                    T.rotate(right);
                    pause();
                    w.unmark();
                    w = right;
                } else if (left != null && left.isRed() && left.getLeft() != null && left.getLeft().isRed()) {
                    // right rotation
                    addStep(w, REL.TOP, "rbbstrotateright");
                    w.mark();
                    pause();
                    boolean wRed = w.isRed();
                    boolean lRed = left.isRed();
                    w.setRed(lRed);
                    left.setRed(wRed);
                    T.rotate(left);
                    pause();
                    w.unmark();
                    w = left;
                } else if (left != null && left.isRed() && right != null && right.isRed()) {
                    // colour flip
                    addStep(w, REL.TOP, "rbbstflipcolours");
                    w.mark();
                    pause();
                    w.setRed(true);
                    w.getLeft().setRed(false);
                    w.getRight().setRed(false);
                    pause();
                    w.unmark();
                } else w = w.getParent();
            }
        }

        RBBSTNode root = (RBBSTNode) T.getRoot();
        if (root.isRed()) {
            addStep(root, REL.TOP, "rbbstroot");
            pause();
            root.setRed(false);
        }
        T.reposition();
        addNote("done");

        assert (((RBBSTNode) T.getRoot()).testStructure()
            && ((RBBSTNode) T.getRoot()).testStructure()
            && ((RBBSTNode) T.getRoot()).testRedBlack());
    }
}
