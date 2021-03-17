final class Main {
	final private static void log(String msg) {
		System.out.println(msg);
	}

	final private static void log(String msg, boolean heading) {
		if (!heading) {
			log(msg);
			return;
		}
		var size = 120;
		var padding = "=".repeat(size);
		var margin = "-".repeat((int) ((size - 2 - msg.length()) / 2));
		var spacing = margin.length() > 0 ? " " : "";
		log(padding);
		log(margin + spacing + msg + spacing + margin);
		log(padding);
	}

	final public static void main(String[] args) {
		log("Binary Tree", true);
		var tree = new BinaryTree<Integer>((i1, i2) -> i1 > i2, 15, 5, 16, 3, 12, 20, 10, 13, 18, 23, 6, 7);
		log("Traversal (Preorder): " + tree.toPreOrder().toString());
		log("Traversal (Inorder): " + tree.toInOrder().toString());
		log("Traversal (Postorder): " + tree.toPostOrder().toString());
		log("Find (12): " + (tree.find(12) != null ? "Found it!" : "Didn't find it"));
		log("Find (3): " + (tree.find(3) != null ? "Found it!" : "Didn't find it"));
		log("Find (24): " + (tree.find(24) != null ? "Found it!" : "Didn't find it"));
		log("Find (23): " + (tree.find(23) != null ? "Found it!" : "Didn't find it"));
		log("Done :)", true);
	}
}
