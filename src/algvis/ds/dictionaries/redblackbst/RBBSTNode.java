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

import java.util.Hashtable;

import algvis.core.DataStructure;
import algvis.core.Node;
import algvis.core.NodeColor;
import algvis.core.history.HashtableStoreSupport;
import algvis.ds.dictionaries.bst.BSTNode;
import algvis.ui.view.View;

public class RBBSTNode extends BSTNode {
    private boolean red = true;

    public RBBSTNode(DataStructure D, int key, int x, int y) {
        super(D, key, x, y);
    }

    public RBBSTNode(DataStructure D, int key, int zDepth) {
        super(D, key, zDepth);
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    @Override
    public RBBSTNode getLeft() {
        return (RBBSTNode) super.getLeft();
    }

    public RBBSTNode getLeft2() {
        final RBBSTNode r = getLeft();
        if (r == null) {
            return ((RBBST) D).NULL;
        } else {
            return r;
        }
    }

    @Override
    public RBBSTNode getRight() {
        return (RBBSTNode) super.getRight();
    }

    public RBBSTNode getRight2() {
        final RBBSTNode r = getRight();
        if (r == null) {
            return ((RBBST) D).NULL;
        } else {
            return r;
        }
    }

    @Override
    public RBBSTNode getParent() {
        return (RBBSTNode) super.getParent();
    }

    public RBBSTNode getParent2() {
        final RBBSTNode p = getParent();
        if (p == null) {
            return ((RBBST) D).NULL;
        } else {
            return p;
        }
    }

    @Override
    public void draw(View v) {
        if (state == Node.INVISIBLE || getKey() == NULL) {
            return;
        }
        // TODO check
        setColor(isRed() ? NodeColor.RED : NodeColor.BLACK);
        super.draw(v);
    }

    void drawBigNodes(View v) {
        if (getKey() == NULL) {
            return;
        }
        if (getLeft() != null) {
            getLeft().drawBigNodes(v);
        }
        if (getRight() != null) {
            getRight().drawBigNodes(v);
        }
        if (isRed() && getParent() != null) {
            v.drawWideLine(x, y, getParent().x, getParent().y);
        } else {
            v.drawWideLine(x - 1, y, x + 1, y);
        }
    }

    public void drawRBBSTTree(View v) {
        if (((RBBST) D).mode24) {
            drawBigNodes(v);
        }
        drawTree(v);
    }

    @Override
    public void storeState(Hashtable<Object, Object> state) {
        super.storeState(state);
        HashtableStoreSupport.store(state, hash + "red", red);
    }

    @Override
    public void restoreState(Hashtable<?, ?> state) {
        super.restoreState(state);
        final Object red = state.get(hash + "red");
        if (red != null) {
            this.red = (Boolean) HashtableStoreSupport.restore(red);
        }
    }

    public boolean testRedBlack() {
        for (BSTNode v : postorder()) {
            RBBSTNode w = (RBBSTNode) v;
            if (w.isRed() && (w.isRoot() || w.getParent().isRed())) {
                return false;
            }
        }
        return true;
    }
}
