package algvis.core;

abstract public class MeldablePQ extends DataStructure {
	public static final int numHeaps = 10;
	public boolean minHeap = false;
	public int active = 1;

	public MeldablePQ(VisPanel M) {
		super(M);
	}

	@Override
	abstract public void insert(int x);

	abstract public void delete();

	abstract public void meld(int i, int j);
	
	abstract public void increaseKey(Node v, int delta);
}
