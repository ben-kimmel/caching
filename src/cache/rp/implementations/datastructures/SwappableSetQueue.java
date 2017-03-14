package cache.rp.implementations.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

public class SwappableSetQueue<E> implements Queue<E>, Set<E> {

	private Node head;
	private Node tail;
	private Map<E, Node> nodeIndex;

	public SwappableSetQueue(int startingCapacity) {
		this.nodeIndex = new HashMap<E, Node>(startingCapacity);
	}

	public SwappableSetQueue() {
		this.nodeIndex = new HashMap<E, Node>();
	}

	public void moveToHead(E value) {
		Node toMove = this.nodeIndex.get(value);
		if (toMove != null) {
			Node previous = toMove.getPreviousNode();
			Node next = toMove.getNextNode();
			if (next != null) {
				next.setPreviousNode(previous);
			}
			if (previous != null) {
				previous.setNextNode(next);
			}
			this.nodeIndex.remove(value);
			this.add(value);
		}
	}

	public ArrayList<E> toArrayList() {
		Node cur = this.head;
		ArrayList<E> ret = new ArrayList<E>();
		if (cur == null) {
			return ret;
		}
		do {
			ret.add(cur.getValue());
		} while ((cur = cur.getNextNode()) != null);

		return ret;
	}

	// queue methods
	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		for (E arg : arg0) {
			this.add(arg);
		}
		return true;
	}

	@Override
	public void clear() {
		while (!this.isEmpty()) {
			this.poll();
		}

	}

	@Override
	public boolean contains(Object arg0) {
		boolean contains;
		try {
			contains = this.nodeIndex.containsKey(arg0);
		} catch (ClassCastException e) {
			return false;
		}
		return contains;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		boolean allContained = true;
		for (Object arg : arg0) {
			allContained &= this.contains(arg);
		}
		return allContained;
	}

	@Override
	public boolean isEmpty() {
		return this.nodeIndex.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iter = new Iterator<E>() {

			Node tail = SwappableSetQueue.this.tail;

			@Override
			public boolean hasNext() {
				return tail == null;
			}

			@Override
			public E next() {
				E val = tail.getValue();
				tail = tail.getPreviousNode();
				return val;
			}

		};
		return iter;
	}

	@Override
	public boolean remove(Object arg0) {
		Node toRemove;
		try {
			toRemove = this.nodeIndex.remove(arg0);
		} catch (ClassCastException e) {
			return false;
		}
		Node before = toRemove.getPreviousNode();
		Node after = toRemove.getNextNode();
		before.setNextNode(after);
		after.setPreviousNode(before);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean allRemoved = true;
		for (Object arg : arg0) {
			allRemoved &= this.remove(arg);
		}
		return allRemoved;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		for (Node n : this.nodeIndex.values()) {
			if (!arg0.contains(n)) {
				this.remove(n);
			}
		}
		return true;
	}

	@Override
	public int size() {
		return this.nodeIndex.size();
	}

	@Override
	public Object[] toArray() {
		return this.toArrayList().toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.toArrayList().toArray(arg0);
	}

	@Override
	public boolean add(E value) {
		if (this.contains(value)) {
			return false;
		}
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
		return true;
	}

	@Override
	public E element() {
		E ret = this.peek();
		if (ret == null) {
			throw new NoSuchElementException();
		}
		return ret;
	}

	@Override
	public boolean offer(E arg0) {
		this.add(arg0);
		return true;
	}

	@Override
	public E peek() {
		return this.tail.getValue();
	}

	@Override
	public E poll() {
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
			return null;
		}
	}

	@Override
	public E remove() {
		E ret = poll();
		if (ret == null) {
			throw new NoSuchElementException();
		}
		return ret;
	}

	// node implementation
	private class Node {
		private E value;
		private Node previousNode;
		private Node nextNode;

		public Node() {
			return;
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
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
