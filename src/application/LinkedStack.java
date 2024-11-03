package application;

public class LinkedStack<T extends Comparable<T>> {
	private LinkedQueue<T> queue = new LinkedQueue<>();
	private LinkedQueue<T> temp = new LinkedQueue<>();

	public void push(T data) {

		queue.enqueue(data);
	}

	public Node<T> pop() {
		Node<T> node = null;
		while (queue.getFront() != null) {
			node = queue.dequeue();
			if (queue.getFront() == null)
				break;
			temp.enqueue(node.getData());
		}
		while (!temp.isEmpty()) {
			queue.enqueue(temp.dequeue().getData());
		}
		return node;
	}

	public Node<T> peek() {
		return queue.getList().getTail();
	}

	public int length() {
		return queue.length();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}

	public void clear() {
		queue.clear();
		;
	}

	public void print() {
		LinkedStack<T> temp = new LinkedStack<>();
		while (peek() != null) {
			System.out.print(peek() + "--> ");
			temp.push(pop().getData());
		}
		while (temp.peek() != null) {
			push(temp.pop().getData());
		}
		System.out.println();
	}
}