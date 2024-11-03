package application;

public class Tree<T extends Comparable<T>> {
	private TNode<T> root;

	public void traverseInOrder() {
		traverseInOrder(root);
		System.out.println();
	}

	private void traverseInOrder(TNode<T> node) {
		if (node != null) {
			if (node.getLeft() != null)
				traverseInOrder(node.getLeft());
			System.out.print(node.toString() + " ");
			if (node.getRight() != null)
				traverseInOrder(node.getRight());
		}

	}

	public TNode<T> find(T data) {
		return find(data, root);
	}

	private TNode<T> find(T data, TNode<T> node) {
		if (node != null) {
			int comp = node.getData().compareTo(data);
			if (comp == 0)
				return node;
			else if (comp > 0 && node.hasLeft())
				return find(data, node.getLeft());
			else // if (comp < 0 && node.hasRight())
				return find(data, node.getRight());
		}
		return null;
	}

	public TNode<T> largest() {
		return largest(root);
	}

	private TNode<T> largest(TNode<T> node) {
		if (node != null) {
			if (!node.hasRight())
				return (node);
			return largest(node.getRight());
		}
		return null;
	}

	public TNode<T> smallest() {
		return smallest(root);
	}

	private TNode<T> smallest(TNode<T> node) {
		if (node != null) {
			if (!node.hasLeft())
				return (node);
			return smallest(node.getLeft());
		}
		return null;
	}

	public int height() {
		return height(root);
	}
	

	private int height(TNode<T> node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.getLeft());
		if (node.hasRight())
			right = height(node.getRight());
		return 1 + ((left > right) ? left : right);
	}
	

	public void insert(T data) {
		if (root == null)
			root = new TNode<T>(data);
		else
			insert(data, root);

	}

	private void insert(T data, TNode<T> node) {
		if (data.compareTo(node.getData()) >= 0) { // insert into right subtree
			if (!node.hasRight())
				node.setRight(new TNode<T>(data));
			else
				insert(data, node.getRight());
		} else { // insert into left subtree
			if (!node.hasLeft())
				node.setLeft(new TNode<T>(data));
			else
				insert(data, node.getLeft());
		}
	}

	public T delete(T data) {
		TNode<T> current = root;
		TNode<T> parent = root;
		boolean isLeftChild = false;
		if (root == null)
			return null;// tree is empty
		while (current != null && !(current.getData().compareTo(data) == 0)) {
			parent = current;
			if (data.compareTo(current.getData()) < 0) {
				current = current.getLeft();
				isLeftChild = true;
			} else {
				current = current.getRight();
				isLeftChild = false;
			}
		}
		if (current == null)
			return null; // node to be deleted not found
		// case 1: node is a leaf
		if (!current.hasLeft() && !current.hasRight()) {
			if (current == root) // tree has one node
				root = null;
			else {
				if (isLeftChild)
					parent.setLeft(null);
				else
					parent.setRight(null);
			}
		} else if (current.hasLeft() && !current.hasRight()) { // current has left child only
			if (current == root) {
				root = current.getLeft();
			} else if (isLeftChild) {
				parent.setLeft(current.getLeft());
			} else {
				parent.setRight(current.getLeft());
			}
		} else if (current.hasRight() && !current.hasLeft()) { // current has right child only
			if (current == root) {
				root = current.getRight();
			} else if (isLeftChild) {
				parent.setLeft(current.getRight());
			} else {
				parent.setRight(current.getRight());
			}
		} else {
			TNode<T> successor = getSuccessor(current);
			if (current == root)
				root = successor;
			else if (isLeftChild) {
				parent.setLeft(successor);
			} else {
				parent.setRight(successor);
			}
			successor.setLeft(current.getLeft());
		}

		// other cases
		return current.getData();
	}

	private TNode<T> getSuccessor(TNode<T> node) {
		TNode<T> parentOfSuccessor = node;
		TNode<T> successor = node;
		TNode<T> current = node.getRight();
		while (current != null) {
			parentOfSuccessor = successor;
			successor = current;
			current = current.getLeft();
		}
		if (successor != node.getRight()) { // fix successor connections
			parentOfSuccessor.setLeft(successor.getRight());
			successor.setRight(node.getRight());
		}
		return successor;
	}

	public int countLeaves() {

		return countLeaves(root);
	}

	private int countLeaves(TNode<T> node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		return countLeaves(node.getRight()) + countLeaves(node.getLeft());
	}

	public int countParents() {
		return countParents(root);
	}

	private int countParents(TNode<T> node) {
		if (node == null)
			return 0;
		if (node.hasLeft() || node.hasRight()) {
			return 1 + countParents(node.getRight()) + countParents(node.getLeft());

		}
		return 0;
	}

	public TNode<T> getRoot() {
		return root;
	}

	public int size() {
		return size(root);
	}

	private int size(TNode<T> node) {
		if (node == null)
			return 0;
		return 1 + size(node.getLeft()) + size(node.getRight());
	}
	
	public int height(T data) {
	    return height(root, data, 1);
	}

	private int height(TNode<T> node, T data, int level) {     
	    if (node == null)
	        return 0;

	    if (node.getData().compareTo(data) == 0)
	        return level;

	    int leftLevel = height(node.getLeft(), data, level + 1);
	    if (leftLevel != 0)
	        return leftLevel;

	    int rightLevel = height(node.getRight(), data, level + 1);
	    return rightLevel;
	}
    
}