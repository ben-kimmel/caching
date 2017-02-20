package cache.implementations.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SwappableQueue {

	private Node head;
	private Node tail;
	private Map<Integer, Node> nodeIndex;

	public SwappableQueue(int maxSize) {
		this.nodeIndex = new HashMap<Integer, Node>(maxSize);
		for (int i = 0; i < maxSize; i++) {
			this.pushNode(i);
		}
	}

	public boolean contains(int value) {
		return this.nodeIndex.containsKey(value);
	}

	public void moveToHead(int value) {
		Node toMove = this.nodeIndex.get(value);
		if (toMove != null) {
			Node previous = toMove.getPreviousNode();
			Node next = toMove.getNextNode();
			next.setPreviousNode(previous);
			previous.setNextNode(next);
			this.nodeIndex.remove(value);
			this.pushNode(value);
		}
	}

	public void pushNode(int value) {
		Node newNode = new Node();
		newNode.setValue(value);
		newNode.setNextNode(this.head);
		if (this.head != null) {
			this.head.setPreviousNode(newNode);
		}
		if (this.tail == null) {
			this.tail = newNode;
		}
		this.head = newNode;
		this.nodeIndex.put(this.head.getValue(), this.head);
	}

	public int popTail() {
		Node oldTail = this.tail;
		if (oldTail != null) {
			this.tail = oldTail.getPreviousNode();
			if (this.tail != null) {
				this.tail.setNextNode(null);
			}
			if (oldTail.equals(this.head)) {
				this.head = null;
			}
			this.nodeIndex.remove(oldTail.getValue());
			return oldTail.getValue();
		} else {
			return 0;
		}
	}

	public ArrayList<Integer> toArrayList() {
		Node cur = this.head;
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (cur == null) {
			return ret;
		}
		do {
			ret.add(cur.getValue());
		} while ((cur = cur.getNextNode()) != null);

		return ret;
	}

	// node implementation
	private class Node {
		private int value;
		private Node previousNode;
		private Node nextNode;

		public Node() {
			return;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Node getPreviousNode() {
			return previousNode;
		}

		public void setPreviousNode(Node previousNode) {
			this.previousNode = previousNode;
		}

		public Node getNextNode() {
			return nextNode;
		}

		public void setNextNode(Node nextNode) {
			this.nextNode = nextNode;
		}

	}

}
