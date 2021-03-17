import java.util.ArrayList;

public final class BinaryTree<T> {
	public interface ComparatorLambda<I1, I2, O> {
		public O run(I1 i1, I2 i2);
	}

	private Item first;

	// true for left, false for right
	private ComparatorLambda<T, T, Boolean> comparator;

	private Node createNode(T content) {
		var node = new Node();
		node.setContent(content);
		return node;
	}

	public BinaryTree(ComparatorLambda<T, T, Boolean> comparator) {
		this.first = new End();
		this.comparator = comparator;
	}

	@SafeVarargs
	public BinaryTree(ComparatorLambda<T, T, Boolean> comparator, T... nodes) {
		this(comparator);
		this.add(nodes);
	}

	@SafeVarargs
	public final void add(T... contents) {
		for (T content : contents)
			this.add(content);
	}

	public final void add(T content) {
		this.first = this.first.add(createNode(content));
	}

	public final ArrayList<T> toPreOrder() {
		return this.first.toPreOrder();
	}

	public final ArrayList<T> toInOrder() {
		return this.first.toInOrder();
	}

	public final ArrayList<T> toPostOrder() {
		return this.first.toPostOrder();
	}

	public final T find(T query) {
		return this.first.find(query);
	}

	private abstract class Item {
		public abstract T getContent();

		public abstract void setContent(T content);

		public abstract Item getNextLeft();

		public abstract Item getNextRight();

		public abstract void setNextLeft(Item next);

		public abstract void setNextRight(Item next);

		public abstract Node add(Node node);

		public abstract ArrayList<T> toPreOrder();

		public abstract ArrayList<T> toInOrder();

		public abstract ArrayList<T> toPostOrder();

		public abstract T find(T query);
	}

	private final class Node extends Item {
		private T content = null;
		private Item nextLeft = null;
		private Item nextRight = null;

		@Override
		public T getContent() {
			return this.content;
		}

		@Override
		public void setContent(T content) {
			this.content = content;
		}

		@Override
		public Item getNextLeft() {
			return this.nextLeft;
		}

		@Override
		public Item getNextRight() {
			return this.nextRight;
		}

		@Override
		public void setNextLeft(Item next) {
			this.nextLeft = next;
		}

		@Override
		public void setNextRight(Item next) {
			this.nextRight = next;
		}

		@Override
		public Node add(Node node) {
			if (comparator.run(this.getContent(), node.getContent())) {
				this.setNextLeft(this.getNextLeft().add(node));
			} else {
				this.setNextRight(this.getNextRight().add(node));
			}
			return this;
		}

		@Override
		public ArrayList<T> toPreOrder() {
			var list = new ArrayList<T>();
			list.add(this.getContent());
			list.addAll(this.getNextLeft().toPreOrder());
			list.addAll(this.getNextRight().toPreOrder());
			return list;
		}

		@Override
		public ArrayList<T> toInOrder() {
			var list = this.getNextLeft().toInOrder();
			list.add(this.getContent());
			list.addAll(this.getNextRight().toInOrder());
			return list;
		}

		@Override
		public ArrayList<T> toPostOrder() {
			var list = this.getNextLeft().toPostOrder();
			list.addAll(this.getNextRight().toPostOrder());
			list.add(this.getContent());
			return list;
		}

		@Override
		public T find(T query) {
			if (this.getContent() == query) {
				return this.getContent();
			} else if (comparator.run(this.getContent(), query)) {
				return this.getNextLeft().find(query);
			} else {
				return this.getNextRight().find(query);
			}
		}
	}

	private final class End extends Item {
		@Override
		public T getContent() {
			throw new IndexOutOfBoundsException("Tried to get out-of-bounds content");
		}

		@Override
		public void setContent(T content) {
			throw new IndexOutOfBoundsException("Tried to set out-of-bounds content");
		}

		@Override
		public Item getNextLeft() {
			throw new IndexOutOfBoundsException("Tried to get out-of-bounds left item");
		}

		@Override
		public Item getNextRight() {
			throw new IndexOutOfBoundsException("Tried to get out-of-bounds right item");
		}

		@Override
		public void setNextLeft(Item next) {
			throw new IndexOutOfBoundsException("Tried to set out-of-bounds left item");
		}

		@Override
		public void setNextRight(Item next) {
			throw new IndexOutOfBoundsException("Tried to set out-of-bounds right item");
		}

		@Override
		public Node add(Node node) {
			node.setNextLeft(this);
			node.setNextRight(this);
			return node;
		}

		@Override
		public ArrayList<T> toPreOrder() {
			return new ArrayList<T>();
		}

		@Override
		public ArrayList<T> toInOrder() {
			return new ArrayList<T>();
		}

		@Override
		public ArrayList<T> toPostOrder() {
			return new ArrayList<T>();
		}

		@Override
		public T find(T query) {
			return null;
		}
	}
}
