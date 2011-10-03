package algvis;

abstract public class Dictionary extends DataStructure {
	public Dictionary(VisPanel M) {
		super(M);
	}

	@Override
	abstract public void insert(int x);

	abstract public void find(int x);

	abstract public void delete(int x);
}
