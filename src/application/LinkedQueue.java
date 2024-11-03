package application;

public class LinkedQueue<T extends Comparable<T>> {

	private LinkedList<T> list = new LinkedList<>();

	public boolean isEmpty() {
		return (list.getHead() == null);
	}

	public void clear() {
		list.clear();
	}

	public void enqueue(T data) {
		list.insertAtTail(data);
	}

	public Node<T> dequeue() {
		return list.deleteHead();
	}

	public Node<T> getFront() {
		return list.getHead();
	}

	public void traverse() {
		System.out.println(list.toString());
	}

	public LinkedList<T> getList() {
		return list;
	}

	public int length() {
		return list.length();
	}

}