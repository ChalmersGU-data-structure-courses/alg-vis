package algvis;

public class LazyBinHeapInsert extends Algorithm {
	LazyBinomialHeap H;
	BinHeapNode v;
	int i;

	public LazyBinHeapInsert(LazyBinomialHeap H, int i, int x) {
		super(H.M);
		this.H = H;
		this.i = i;
		v = new BinHeapNode(H, x);
	}

	@Override
	public void run() {
		if (H.root[i] == null) {
			H.root[i] = H.min[i] = v;
		} else {
			H.root[i].linkLeft(v);
			if (v.less(H.min[i])) {
				H.min[i] = v;
			}
		}
		H.reposition();
		mysuspend();
	}
}
