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
package algvis.ds.priorityqueues.meldableheap;

import algvis.core.visual.ZDepth;
import algvis.ui.view.REL;

public class MeldableHeapInsert extends MeldableHeapAlg {
    private final int x;

    public MeldableHeapInsert(MeldableHeap H, int x) {
        super(H);
        this.x = x;
    }

    @Override
    public void runAlgorithm() {
        setHeader("insert", x);
        final int i = H.active;
        H.root[0] = new MeldableHeapNode(H, x, ZDepth.ACTIONNODE);

        H.reposition();

        if (H.root[i] == null) {
            H.root[i] = H.root[0];
            H.root[0] = null;
            if (H.root[i] != null) {
                addStep(H.root[i], REL.TOP, "newroot");
                H.root[i].highlightTree();
            }
            H.reposition();
            // heap #1 is empty; done;
            return;
        }

        if (H.root[0] == null) {
            // heap #2 is empty; done;
            return;
        }

        H.active = i;
        H.root[0].highlightTree();
        H.reposition();

        pause();
        meld(i);
    }
}
