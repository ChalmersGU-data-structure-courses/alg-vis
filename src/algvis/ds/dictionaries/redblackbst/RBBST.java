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

import algvis.core.Node;
import algvis.core.visual.ZDepth;
import algvis.ds.dictionaries.bst.BST;
import algvis.ui.VisPanel;
import algvis.ui.view.Layout;
import algvis.ui.view.View;
import javax.swing.JOptionPane;

public class RBBST extends BST {
    public static String dsName = "redblackbst";
    final RBBSTNode NULL;
    public boolean mode24 = false;
    public boolean slides = false;

    @Override
    public String getName() {
        return "redblackbst";
    }

    public RBBST(VisPanel M) {
        super(M);
        NULL = new RBBSTNode(this, Node.NULL, ZDepth.NODE);
        NULL.setParent(NULL);
        NULL.setRight(NULL);
        NULL.setLeft(NULL);
        NULL.setRed(false);
        NULL.size = NULL.height = NULL.sumh = 0;
        NULL.state = Node.INVISIBLE;
    }

    @Override
    public void insert(int x) {
        start(new RBBSTInsert(this, x));
    }

    @Override
    public void delete(int x) {
        JOptionPane.showMessageDialog(null, "Sorry, deletion is not implemented.");
    }

    @Override
    public void draw(View V) {
        if (getRoot() != null) {
            ((RBBSTNode) getRoot()).drawRBBSTTree(V);
        }
    }

    @Override
    public Layout getLayout() {
        return Layout.COMPACT;
    }
}
