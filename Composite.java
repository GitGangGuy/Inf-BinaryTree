import java.util.Comparator;

public final class Composite<T> {
	private Item first;

	private Node createNode(T content) {
		var node = new Node();
		node.setContent(content);
		return node;
	}

	public Composite() {
		this.first = new End();
	}

	@SafeVarargs
	public Composite(T... nodes) {
		this();
		this.add(nodes);
	}

	@SafeVarargs
	public final void add(T... contents) {
		for (T content : contents) {
			this.add(content);
		}
	}

	public final void add(T content) {
		var first = this.first.add(createNode(content));
		if (this.first != first)
			this.first = first;
	}

	public final T find(T query) {
		return this.first.find(query).getContent();
	}

	public final void insertSorted(T content, Comparator<? super T> comparator) {
		this.first.insertSorted(createNode(content), comparator);
	}

	public final void remove(T content) {
		this.first.remove(createNode(content));
	}

	public final String toString() {
		var stringified = this.first.print();
		return "[" + stringified.substring(0, stringified.length() - 2) + "]";
	}

	private abstract class Item {
		public abstract boolean isNode();

		public abstract T getContent();

		public abstract Item getNext();

		public abstract void setContent(T content);

		public abstract void setNext(Item next);

		public abstract Node add(Node node);

		public abstract Node find(T query);

		public abstract void insertSorted(Node node, Comparator<? super T> comparator);

		public abstract Item remove(Node node);

		public abstract String print();
	}

	private final class Node extends Item {
		private T content = null;
		private Item next = null;

		public boolean isNode() {
			return true;
		}

		public T getContent() {
			return this.content;
		}

		public Item getNext() {
			return this.next;
		}

		public void setContent(T content) {
			this.content = content;
		}

		public void setNext(Item next) {
			this.next = next;
		}

		public Node add(Node item) {
			this.next = this.next.add(item);
			return this;
		}

		public Node find(T query) {
			return this.content.equals(query) ? this : this.next.find(query);
		}

		public void insertSorted(Node node, Comparator<? super T> comparator) {
			if (comparator.compare(node.getContent(), this.next.getContent()) < 0) {
				node.setNext(this.next);
				this.next = node;
			} else {
				this.next.insertSorted(node, comparator);
			}
		}

		public Item remove(Node node) {
			if (this.content.equals(node.getContent()))
				return this.next;
			this.next = this.next.remove(node);
			return this;
		}

		public String print() {
			return this.content.toString() + ", " + this.next.print();
		}
	}

	private final class End extends Item {
		public boolean isNode() {
			return false;
		}

		public T getContent() {
			throw new NullPointerException("Tried to access contents of end node");
		}

		public Item getNext() {
			throw new IndexOutOfBoundsException("Tried to access index > size");
		}

		public void setContent(T content) {
			throw new NullPointerException("Tried to write contents of end node");
		}

		public void setNext(Item next) {
			throw new IllegalAccessError("Illegal function call");
		}

		public Node add(Node item) {
			item.setNext(this);
			return item;
		}

		public Node find(T query) {
			return null;
		}

		public void insertSorted(Node node, Comparator<? super T> comparator) {
			this.add(node);
		}

		public Item remove(Node node) {
			return this;
		}

		public String print() {
			return "";
		}
	}
}
