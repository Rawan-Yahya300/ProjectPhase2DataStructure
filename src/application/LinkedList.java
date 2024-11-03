package application;

public class LinkedList<T extends Comparable<T>> {

	private Node<T> head;
	private Node<T> tail;

	public void insert(T data) {
		Node<T> newNode = new Node<>(data);
		Node<T> prev = null;
		Node<T> curr = head;

		for (; curr != null && newNode.getData().compareTo(curr.getData()) > 0; prev = curr, curr = curr.getNext())
			;

		if (prev == null && curr == null) { // empty
			head = newNode;
			tail = newNode;
		} else if (prev == null) { // first
			newNode.setNext(curr);
			head = newNode;
		} else if (curr == null) { // last
			prev.setNext(newNode);
			tail = newNode;
		} else { // between
			newNode.setNext(curr);
			prev.setNext(newNode);
		}

	}

	public void insertAtTail(T data) {
		Node<T> newNode = new Node<>(data);
		if (head == null && tail == null) {
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}
	}

	public Node<T> deleteHead() {
		if (head == null && tail == null) {
			return null;
		} else if (head == tail) {
			Node<T> deletedNode = head;
			head = null;
			tail = null;
			return deletedNode;
		} else {
			Node<T> deletedNode = head;
			head = head.getNext();
			return deletedNode;
		}

	}

	public Node<T> delete(T data) {

		Node<T> newNode = new Node<>(data);
		Node<T> prev = null, curr = head;
		for (; curr != null && newNode.getData().compareTo(curr.getData()) > 0; prev = curr, curr = curr.getNext())
			;
		if (curr == null) {
			return null;
		}
		if (curr.getData().compareTo(newNode.getData()) == 0) {
			Node<T> deleted = curr;
			if (prev == null && curr.getNext() == null) {
				head = null;
				tail = null;
				return deleted;
			} else if (prev == null) {
				prev = curr.getNext();
				head = prev;
				return deleted;

			} else if (curr == tail) {
				prev.setNext(curr.getNext());
				curr = curr.getNext();
				tail = prev;
				return deleted;
			} else {
				prev.setNext(curr.getNext());
				curr = curr.getNext();
				return deleted;
			}
		}
		return null;
	}

	public String toString() {
		String s = "";

		Node<T> curr = head;
		while (curr != null) {
			s += curr + " \n";
			curr = curr.getNext();
		}
		return s;

	}

	public Node<T> find(T data) {

		Node<T> newNode = new Node<>(data);
		Node<T> prev = null, curr = head;
		for (; curr != null && newNode.getData().compareTo(curr.getData()) > 0; prev = curr, curr = curr.getNext())
			;
		if (prev == null && curr == null) {
			return null;
		} else if (curr == null) {
			return null;
		} else if (curr.getData().compareTo(newNode.getData()) == 0) {
			return curr;
		}

		return null;

	}

	public void clear() {
		head = null;
		tail = null;
	}

	public int length() {
		Node<T> curr = head;
		int count = 0;
		while (curr != null) {
			count++;
			curr = curr.getNext();
		}
		return count;
	}

	public Node<T> getHead() {
		return head;
	}

	public Node<T> getTail() {
		return tail;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	public void setTail(Node<T> tail) {
		this.tail = tail;
	}

}
